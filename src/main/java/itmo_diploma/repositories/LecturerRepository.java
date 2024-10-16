package itmo_diploma.repositories;

import itmo_diploma.models.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    boolean existsByEmailOrPhone(String email, String phone);
}
