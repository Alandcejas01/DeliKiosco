package arg.com.delikiosco.config;

import arg.com.delikiosco.utils.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    http
        .csrf()
        .disable()
        .cors()
        .and()
        .authorizeHttpRequests()
          .requestMatchers("/api/v1/auth/**")
          .permitAll()
          .anyRequest()
          .authenticated()
        .and()
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint((request, response, e) -> {
          response.setContentType("application/json;charset=UTF-8");
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          response.getWriter().write(new JSONObject()
              .put("message", "Acceso denegado")
              .toString());
        });

    return http.build();
  }


}
