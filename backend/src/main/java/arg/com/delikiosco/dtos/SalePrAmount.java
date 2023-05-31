package arg.com.delikiosco.dtos;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Dto para persistir la cantidad de cada producto en una venta.
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalePrAmount {
  private Long productId;
  private Integer amount;
}
