package arg.com.delikiosco.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Esta clase hace referencia a la entidad Provider de la bases de datos.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Provider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Long cuit;

  private String phone;

  private String address;

  @OneToMany(mappedBy = "provider")
  private Set<Product> products;

  private Boolean active = true;

  /**
   * Dto para la manipulación de datos del Proveedor.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProviderDto  {
    private Long id;
    @NotNull(message = "El nombre es requerido")
    private String name;
    @NotNull(message = "El cuit es requerido")
    private Long cuit;
    @NotBlank(message = "El telefono es requerido")
    private String phone;
    @NotBlank(message = "La direccion es requerida")
    private String address;
    @NotEmpty(message = "Los productos son requeridos")
    private Set<Product.ProductRequest> products;
  }

  /**
   * Dto solo con la info del Proveedor.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProviderInfoDto  {
    private Long id;
    @NotNull(message = "El nombre es requerido")
    private String name;
    @NotNull(message = "El cuit es requerido")
    private Long cuit;
    @NotBlank(message = "El telefono es requerido")
    private String phone;
    @NotBlank(message = "La direccion es requerida")
    private String address;
  }

  /**
   * Dto con la información necesaria del proveedor para las ventas.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProviderSaleDto  {
    private Long id;
    private String name;
    private Long cuit;
    private String phone;
    private String address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Provider provider = (Provider) o;
    return Objects.equals(name, provider.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
