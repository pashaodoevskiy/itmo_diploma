package itmo_diploma.repositories;

import itmo_diploma.models.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    public Lecturer findByEmailOrPhone(String email, String phone);
}
