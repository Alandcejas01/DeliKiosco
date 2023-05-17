package arg.com.delikiosco.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Esta clase hace referencia a la entidad Sale de la bases de datos.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime date;

  @ManyToOne(fetch = FetchType.LAZY)
  private Client client;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "sale_providers",
      joinColumns = @JoinColumn(name = "saleId"),
      inverseJoinColumns = @JoinColumn(name = "providerId")
  )
  private Set<Provider> providers = new HashSet<>();


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "sale_products",
      joinColumns = @JoinColumn(name = "saleId"),
      inverseJoinColumns = @JoinColumn(name = "productId")
  )
  private Set<Product> products = new HashSet<>();

  private Integer amounts;

  private BigDecimal totalPrice;

  /**
   * Dto para la creación de una venta.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SaleRequest {
    @NotEmpty(message = "Los productos son requeridos")
    private Set<Product.ProductSaleDto> products;
  }

  /**
   * Dto para traer los datos de las ventas.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SaleDto {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Client.ClientSaleDto client;
    private Set<Provider.ProviderSaleDto> providers = new HashSet<>();
    private Set<Product.ProductSaleDto> products = new HashSet<>();
    private Integer amounts;
    private BigDecimal totalPrice;
  }

  /**
   * Dto con la información necesaria de las ventas para el proveedor.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SaleProviderDto {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Client.ClientDto client;
    private Set<Product.ProductSaleDto> products = new HashSet<>();
    private Integer amounts;
    private BigDecimal totalPrice;
  }

  /**
   * Dto con la información necesaria de las ventas para el cliente.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SaleClientDto {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private BigDecimal totalPrice;
  }
}
