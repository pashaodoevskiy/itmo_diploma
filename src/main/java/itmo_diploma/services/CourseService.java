package itmo_diploma.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo_diploma.exceptions.RecordAlreadyExistsException;
import itmo_diploma.exceptions.ValidationException;
import itmo_diploma.models.Course;
import itmo_diploma.models.Lecturer;
import itmo_diploma.requests.CourseRequest;
import itmo_diploma.requests.CourseToLecturerDto;
import itmo_diploma.requests.LecturerRequest;
import itmo_diploma.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LecturerService lecturerService;
    private final ObjectMapper mapper;
    private AuthService authService;

    public CourseRequest create(CourseRequest courseRequest) throws ValidationException {
        Course course = mapper.convertValue(courseRequest, Course.class);

        if (courseRepository.findByName(course.getName()) != null) {
            throw new RecordAlreadyExistsException("Курс с таким названием уже существует");
        }

        Course savedCourse = courseRepository.save(course);

        return mapper.convertValue(savedCourse, CourseRequest.class);
    }

    public void update(Long id, CourseRequest courseRequest) throws EntityNotFoundException {
        Course course = getCourseFromDb(id);

        course.setName(courseRequest.getName() == null ? course.getName() : courseRequest.getName());
        course.setPrice(courseRequest.getPrice() == null ? course.getPrice() : courseRequest.getPrice());
        course.setStartDate(courseRequest.getStartDate() == null ? course.getStartDate() : courseRequest.getStartDate());
        course.setEndDate(courseRequest.getEndDate() == null ? course.getEndDate() : courseRequest.getEndDate());

        courseRepository.save(course);
    }

    public void delete(Long id) throws EntityNotFoundException {
        isCourseExists(id);

        courseRepository.deleteById(id);
    }

    public CourseRequest get(Long id) throws EntityNotFoundException {
        isCourseExists(id);

        Course course = getCourseFromDb(id);
        CourseRequest courseRequest = mapper.convertValue(course, CourseRequest.class);
        courseRequest.setLecturer(mapper.convertValue(course.getLecturer(), LecturerRequest.class));

        return courseRequest;
    }

    public List<CourseRequest> getAll() {
        return prepareCourseRequests(courseRepository.findAll());
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

    public Course getCourseFromDb(Long id) throws EntityNotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Курс не найден"));
    }

    private void isCourseExists(Long courseId) throws EntityNotFoundException {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс не найден");
        }
    }

    public List<CourseRequest> getUserCourses() {
        return prepareCourseRequests(authService.getCurrentUser().getCourses());
    }

    public List<CourseRequest> getLecturerCourses(Long id) {
        Lecturer lecturer = lecturerService.getLecturerFromDb(id);

        return prepareCourseRequests(lecturer.getCourses());
    }

    private List<CourseRequest> prepareCourseRequests(List<Course> courses) {
        return courses.stream()
                .map(course -> {
                    CourseRequest courseRequest = mapper.convertValue(course, CourseRequest.class);
                    LecturerRequest lecturerRequest = mapper.convertValue(course.getLecturer(), LecturerRequest.class);

                    courseRequest.setLecturer(lecturerRequest);

                    return courseRequest;
                }).toList();
    }
}
