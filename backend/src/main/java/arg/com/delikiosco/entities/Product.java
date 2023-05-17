package arg.com.delikiosco.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Esta clase hace referencia a la entidad Product de la bases de datos.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Provider provider;

  private String name;

  private String description;

  private BigDecimal price;

  private Integer stock;

  private Boolean active = true;

  /**
   * Dto para la creación de productos y para la información que requiere el proveedor.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProductRequest {
    private Long id;
    @NotBlank(message = "El nombre del producto es requerido")
    private String name;
    @NotBlank(message = "La descripcion del producto es requerido")
    private String description;
    @NotNull(message = "El precio del producto es requerido")
    private BigDecimal price;
    @NotNull(message = "El stock del producto es requerido")
    private Integer stock;
  }

  /**
   * Dto para mostrar los datos.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProductDto {
    private Long id;
    private Provider.ProviderInfoDto provider;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(name, product.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}


