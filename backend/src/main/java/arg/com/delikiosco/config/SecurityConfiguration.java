package arg.com.delikiosco.config;

import arg.com.delikiosco.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Esta clase configura la seguridad de las rutas y la autenticacion de los usuarios.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;

  private final AuthenticationProvider authenticationProvider;

  private final EndpointsConfiguration endpointsConfiguration;

  /**
   * Permite acceso a rutas sin autenticación y despues configura la autenticación
   * para las demas rutas a traves de los tokens JWT.
   *
   * @param http .
   * @return {@link SecurityFilterChain}
   * @throws Exception .
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    endpointsConfiguration.configureApiEndpoints(http);
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
          .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
          .authenticated()
          .anyRequest()
          .permitAll()
        .and()
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


}
