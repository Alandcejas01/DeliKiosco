package arg.com.delikiosco.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * Dto para el manejo de excepciones.
 */
@Data
@Builder
public class ErrorDto {
  private String message;
}
