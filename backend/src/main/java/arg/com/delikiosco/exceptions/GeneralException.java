package arg.com.delikiosco.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Clase para el manejo de errores personalizados.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralException extends RuntimeException {
  private HttpStatus status;

  public GeneralException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
