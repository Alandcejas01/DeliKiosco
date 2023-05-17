package arg.com.delikiosco.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de los Productos del mockApi CarpinchoStore.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCarpinchoDto {

  private Long id;
  @NotBlank(message = "El nombre del producto es requerido")
  private String productName;
  @NotNull(message = "El codigo del producto es requerido")
  private Integer productCode;
  @NotNull(message = "La cantidad del producto es requerido")
  private Integer productQuantity;
  @NotBlank(message = "La url del producto es requerida")
  private String productImg;

  /**
   * Dto con los campos transformados del ProductCarpincho.
   */
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class TransformedProductDto {
    private Long id;
    private String name;
    private Integer sku;
    private Integer stock;
    private String imgUrl;
  }
}
