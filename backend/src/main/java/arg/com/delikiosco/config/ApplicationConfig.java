package arg.com.delikiosco.config;

import arg.com.delikiosco.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Configuracion de beans para la autenticación JWT.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
  private final UserService userService;

  /**
   * Busca el usuario a traves del correo electronico y lanza una excepción si no lo encuentra.
   *
   * @return {@link arg.com.delikiosco.entities.User} o {@link UsernameNotFoundException}.
   */
  @Bean
  public UserDetailsService userDetailsService() {

    return username -> userService.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Define una instancia de AuthenticationProvider que se utiliza para autenticar a los usuarios.
   *
   * @return {@link AuthenticationProvider}.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
          throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
