package arg.com.delikiosco.repositories;

import arg.com.delikiosco.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Interfaz para User que extiende de JpaRepository para heredar las querys de JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  @Query(value = "select u from User u WHERE u.id = ?1")
  User findOne(Long userId);
}
