package arg.com.delikiosco.services;

import arg.com.delikiosco.entities.User;
import arg.com.delikiosco.exceptions.GeneralException;
import arg.com.delikiosco.repositories.UserRepository;
import arg.com.delikiosco.services.interfaces.UserServiceInterface;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


/**
 * Servicio que contiene los metodos con la logica del usuario .
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

  private final UserRepository userRepository;

  ModelMapper mapper = new ModelMapper();

  @Override
  public void save(User.UserAuthentication userDto) {
    User userEntity = mapper.map(userDto, User.class);
    userRepository.save(userEntity);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User.UserDto findById(Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException("El usuario no existe",
            HttpStatus.NOT_FOUND));

    return mapper.map(user, User.UserDto.class);
  }

}
