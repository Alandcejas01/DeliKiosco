package arg.com.delikiosco.dtos.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto para el login JWT.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {
  @NotBlank(message = "El email es requerido")
  private String email;
  @NotBlank(message = "La contraseña es requerida")
  String password;
}
