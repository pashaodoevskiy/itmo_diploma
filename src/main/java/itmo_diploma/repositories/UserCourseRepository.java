package itmo_diploma.repositories;

import itmo_diploma.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    Optional<UserCourse> findByUserIdAndCourseId(Long userId, Long courseId);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
