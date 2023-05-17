package arg.com.delikiosco.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto para las respuestas en las llamadas a la api.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
  private String message;
}
