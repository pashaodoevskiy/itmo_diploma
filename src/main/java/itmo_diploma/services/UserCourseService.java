package itmo_diploma.services;

import itmo_diploma.enums.PaymentType;
import itmo_diploma.exceptions.CustomException;
import itmo_diploma.models.Course;
import itmo_diploma.models.User;
import itmo_diploma.models.UserCourse;
import itmo_diploma.repositories.UserCourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Formatter;

@Service
@RequiredArgsConstructor
public class UserCourseService {

    private final UserCourseRepository userCourseRepository;

    public UserCourse getFromDb(Long userId, Long courseId) {
        return userCourseRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
    }

    public void create(User user, Course course, PaymentType paymentType) {
        if (userCourseRepository.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new CustomException(new Formatter().format("Вы уже записаны на курс %s", course.getName()).toString());
        }

        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        userCourse.setPaymentType(paymentType);

        userCourseRepository.save(userCourse);
    }

    public void remove(User user, Course course) {
        if (!userCourseRepository.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new CustomException(new Formatter().format("Вы не записаны на курс %s", course.getName()).toString());
        }

        UserCourse userCourse = getFromDb(user.getId(), course.getId());

        userCourseRepository.delete(userCourse);
    }

    public void pay(Long userId, Long courseId) {
        UserCourse userCourse = getFromDb(userId, courseId);

        if (userCourse.getIsPaid().equals(true)) {
            throw new CustomException(new Formatter().format("Курс %s уже оплачен", userCourse.getCourse().getName()).toString());
        }

        userCourse.setIsPaid(true);
        userCourse.setPaymentDate(LocalDateTime.now());

        userCourseRepository.save(userCourse);
    }
}
