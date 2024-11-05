package itmo_diploma.repositories;

import itmo_diploma.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameOrEmailOrPhone(String username, String email, String phone);
    Optional<User> findByUsername(String username);
}
