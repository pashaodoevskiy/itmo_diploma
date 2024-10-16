package itmo_diploma.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo_diploma.exceptions.ValidationException;
import itmo_diploma.models.Course;
import itmo_diploma.models.User;
import itmo_diploma.requests.CourseRequest;
import itmo_diploma.requests.UserCourseRequest;
import itmo_diploma.requests.UserRequest;
import itmo_diploma.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CourseService courseService;
    private final ObjectMapper mapper;
    private final AuthService authService;

    public void update(UserRequest userRequest) throws EntityNotFoundException {
        User user = authService.getCurrentUser();

        user.setName(userRequest.getName() == null ? user.getName() : userRequest.getName());
        user.setSurname(userRequest.getSurname() == null ? user.getSurname() : userRequest.getSurname());
        user.setPhone(userRequest.getPhone() == null ? user.getPhone() : userRequest.getPhone());
        user.setEmail(userRequest.getEmail() == null ? user.getEmail() : userRequest.getEmail());

        userRepository.save(user);
    }

    public void delete() {
        userRepository.deleteById(authService.getCurrentUser().getId());
    }

    public UserRequest get() {
        return mapper.convertValue(authService.getCurrentUser(), UserRequest.class);
    }

    public void addCourse(UserCourseRequest userCourseRequest) throws ValidationException {
        User user = authService.getCurrentUser();
        Course course = courseService.getCourseFromDb(userCourseRequest.getCourseId());

        user.addCourse(course);

        userRepository.save(user);
    }

    public void removeCourse(UserCourseRequest userCourseRequest) throws ValidationException {
        User user = authService.getCurrentUser();
        Course course = courseService.getCourseFromDb(userCourseRequest.getCourseId());

        user.removeCourse(course);

        userRepository.save(user);
    }

    public List<UserRequest> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> mapper.convertValue(user, UserRequest.class))
                .toList();
    }
}
