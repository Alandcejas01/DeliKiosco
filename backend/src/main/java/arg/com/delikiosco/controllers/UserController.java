package arg.com.delikiosco.controllers;

import arg.com.delikiosco.entities.User;
import arg.com.delikiosco.services.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller con los endpoints para los metodos del service del Usuario.
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{userEmail}")
  public ResponseEntity<Optional<User>> user(@PathVariable String userEmail) {
    Optional<User> response = userService.findByEmail(userEmail);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/find/{userId}")
  public ResponseEntity<User.UserDto> findById(@PathVariable Long userId) {
    User.UserDto response = userService.findById(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
