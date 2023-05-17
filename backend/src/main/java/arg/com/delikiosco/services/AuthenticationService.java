package arg.com.delikiosco.services;

import arg.com.delikiosco.dtos.auth.AuthenticationRequestDto;
import arg.com.delikiosco.dtos.auth.AuthenticationResponseDto;
import arg.com.delikiosco.dtos.auth.RegisterRequestDto;
import arg.com.delikiosco.entities.User;
import arg.com.delikiosco.enums.Role;
import arg.com.delikiosco.exceptions.GeneralException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Servicio para el registro y autenticación de la aplicación.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  ModelMapper mapper = new ModelMapper();

  /**
   * Este método se encarga de registrar al usuario en la bases de datos
   * y returnar el token jwt.
   *
   * @param request con datos del registro del usuario.
   * @return JWT token.
   */
  public AuthenticationResponseDto register(RegisterRequestDto request) {

    Optional<User> userExist = userService.findByEmail(request.getEmail());

    if (userExist.isPresent()) {
      throw new GeneralException("El usuario ya existe", HttpStatus.BAD_REQUEST);
    }
    User user = User.builder()
        .fullName(request.getFullName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();

    userService.save(mapper.map(user, User.UserAuthentication.class));

    String jwtToken = jwtService.generateToken(user);

    return AuthenticationResponseDto.builder()
        .token(jwtToken)
        .build();
  }

  /**
   * Este método se encarga de autenticar el usuario obtenido
   * y devolver el jwt si la autenticacion es correcta.
   *
   * @param request contiene datos del login del Usuario.
   * @return JWT token.
   */
  public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        ));

    User user = userService.findByEmail(request.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);

    return AuthenticationResponseDto.builder()
        .token(jwtToken)
        .build();
  }
}
