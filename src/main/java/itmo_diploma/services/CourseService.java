package itmo_diploma.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo_diploma.exceptions.CustomException;
import itmo_diploma.exceptions.ValidationException;
import itmo_diploma.models.Course;
import itmo_diploma.models.Lecturer;
import itmo_diploma.requests.CourseRequest;
import itmo_diploma.requests.CourseToLecturerDto;
import itmo_diploma.repositories.CourseRepository;
import itmo_diploma.responses.CourseResponse;
import itmo_diploma.responses.LecturerResponse;
import itmo_diploma.responses.UserCourseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Formatter;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LecturerService lecturerService;
    private final ObjectMapper mapper;
    private AuthService authService;

    public Course getCourseFromDb(Long id) throws EntityNotFoundException {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курс не найден"));
    }

    private void isCourseExists(Long courseId) throws EntityNotFoundException {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс не найден");
        }
    }

    private void isCourseExists(String name) throws CustomException {
        if (courseRepository.existsByName(name)) {
            throw new CustomException(new Formatter().format("Курс с названием %s уже существует", name).toString(), HttpStatus.CONFLICT.value());
        }
    }

    public CourseResponse create(CourseRequest courseRequest) throws ValidationException {
        Course course = mapper.convertValue(courseRequest, Course.class);

        isCourseExists(course.getName());

        Course savedCourse = courseRepository.save(course);

        return mapper.convertValue(savedCourse, CourseResponse.class);
    }

    public void update(Long id, CourseRequest courseRequest) throws EntityNotFoundException {
        Course course = getCourseFromDb(id);

        isCourseExists(course.getName());

        course.setName(courseRequest.getName() == null ? course.getName() : courseRequest.getName());
        course.setPrice(courseRequest.getPrice() == null ? course.getPrice() : courseRequest.getPrice());
        course.setStartDate(courseRequest.getStartDate() == null ? course.getStartDate() : courseRequest.getStartDate());
        course.setEndDate(courseRequest.getEndDate() == null ? course.getEndDate() : courseRequest.getEndDate());

        courseRepository.save(course);
    }

    public void delete(Long id) throws EntityNotFoundException {
        Course course = getCourseFromDb(id);

        if (!course.getUserCourses().isEmpty()) {
            throw new CustomException("Нельзя удалить курс если на него записаны пользователи");
        }

        courseRepository.deleteById(id);
    }

    public CourseResponse get(Long id) throws EntityNotFoundException {
        isCourseExists(id);

        Course course = getCourseFromDb(id);
        CourseResponse courseResponse = mapper.convertValue(course, CourseResponse.class);
        courseResponse.setLecturer(mapper.convertValue(course.getLecturer(), LecturerResponse.class));

        return courseResponse;
    }

    public List<CourseResponse> getAll() {
        return convertToResponse(courseRepository.findAll());
    }

    public void addLecturer(CourseToLecturerDto courseToLecturerDto) throws EntityNotFoundException {
        Course course = getCourseFromDb(courseToLecturerDto.getCourseId());

        Lecturer lecturer = lecturerService.getLecturerFromDb(courseToLecturerDto.getLecturerId());
        lecturer.getCourses().add(course);
        lecturerService.updateData(lecturer);

        course.setLecturer(lecturer);
        courseRepository.save(course);
    }

    public void removeLecturer(CourseToLecturerDto courseToLecturerDto) throws EntityNotFoundException {
        Course course = getCourseFromDb(courseToLecturerDto.getCourseId());

        Lecturer lecturer = lecturerService.getLecturerFromDb(courseToLecturerDto.getLecturerId());
        lecturer.getCourses().remove(course);
        lecturerService.updateData(lecturer);

        course.setLecturer(null);

        courseRepository.save(course);
    }

    public List<UserCourseResponse> getUserCourses() {

        return authService.getCurrentUser().getCourses().stream()
                .map(userCourse -> {
                    Course course = userCourse.getCourse();

                    return UserCourseResponse.builder()
                            .id(course.getId())
                            .name(course.getName())
                            .price(course.getPrice())
                            .isPaid(userCourse.getIsPaid())
                            .startDate(course.getStartDate())
                            .endDate(course.getEndDate())
                            .paymentDate(userCourse.getPaymentDate())
                            .paymentType(userCourse.getPaymentType())
                            .lecturer(mapper.convertValue(course.getLecturer(), LecturerResponse.class))
                            .build();
                })
                .toList();
    }

    public List<CourseResponse> getLecturerCourses(Long id) {
        Lecturer lecturer = lecturerService.getLecturerFromDb(id);

        return convertToResponse(lecturer.getCourses());
    }

    private List<CourseResponse> convertToResponse(List<Course> courses) {
        return courses.stream()
                .map(course -> {
                    CourseResponse courseResponse = mapper.convertValue(course, CourseResponse.class);
                    courseResponse.setLecturer(mapper.convertValue(course.getLecturer(), LecturerResponse.class));

                    return courseResponse;
                })
                .toList();
    }
}
