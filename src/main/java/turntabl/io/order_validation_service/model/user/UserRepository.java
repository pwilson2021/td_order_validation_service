package turntabl.io.order_validation_service.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //@Query("SELECT s FROM users s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);
}
