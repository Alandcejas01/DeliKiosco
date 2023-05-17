package arg.com.delikiosco.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


/**
 * Clase para definir los endpoints con autenticación.
 */
@Configuration
public class EndpointsConfiguration {

  /**
   * Clase que llama a todas las configuraciones de endpoints individuales.
   */
  public void configureApiEndpoints(HttpSecurity http) throws Exception {
    configureUserEndpoints(http);
    configureProductEndpoints(http);
    configureProviderEndpoints(http);
    configureSaleEndpoints(http);
    configureClientEndpoints(http);
    configureCarpinchoEndpoints(http);
  }

  /**
   * Configuración de endpoints para User.
   */
  public void configureUserEndpoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/api/v1/user/**")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/v1/find/**")
        .authenticated();
  }

  /**
   * Configuración de endpoints para Product.
   */
  public void configureProductEndpoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/api/v1/product")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/v1/product/all")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/v1/product/create/**")
        .authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/v1/product/update/**")
        .authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/product/toggleActive/**")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/v1/product/lowStock/**")
        .authenticated();
  }

  /**
   * Configuración de endpoints para Provider.
   */
  public void configureProviderEndpoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/api/v1/provider")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/v1/provider/all")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/v1/provider/create")
        .authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/v1/provider/update/**")
        .authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/provider/toggleActive/**")
        .authenticated();
  }

  /**
   * Configuración de endpoints para Sale.
   */
  public void configureSaleEndpoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/api/v1/sale")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/v1/sale/makeSale/**")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/v1/sale/**")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/v1/sale/provider/**")
        .authenticated();
  }

  /**
   * Configuración de endpoints para Client.
   */
  public void configureClientEndpoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/api/v1/client")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "/api/v1/client/all")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/v1/client/create")
        .authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/v1/client/update/**")
        .authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/client/toggleActive/**")
        .authenticated()
        .requestMatchers(HttpMethod.GET, "api/v1/client/buys")
        .authenticated();
  }

  /**
   * Configuración de endpoints para CarpinchoStore.
   */
  public void configureCarpinchoEndpoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET, "/api/v1/carpincho")
        .authenticated()
        .requestMatchers(HttpMethod.POST, "/api/v1/carpincho/create")
        .authenticated()
        .requestMatchers(HttpMethod.PUT, "/api/v1/carpincho/update/**")
        .authenticated()
        .requestMatchers(HttpMethod.DELETE, "/api/v1/carpincho/delete/**")
        .authenticated();
  }
}
