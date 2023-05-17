package arg.com.delikiosco.services.interfaces;

import arg.com.delikiosco.entities.User;
import java.util.Optional;


/**
 * Interfaz con los metodos a implementar en el Userservice.
 */
public interface UserServiceInterface {

  void save(User.UserAuthentication userDto);

  Optional<User> findByEmail(String email);

  User.UserDto findById(Long userId);
}
