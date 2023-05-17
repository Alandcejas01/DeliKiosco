package arg.com.delikiosco.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Esta clase hace referencia a la entidad Client de la bases de datos.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String lastName;

  private Integer dni;

  private String phone;

  private String address;

  @OneToMany(mappedBy = "client")
  private Set<Sale> buys = new HashSet<>();

  private Boolean active = true;


  /**
   * Dto para la creacion y edición del cliente.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ClientRequest {
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "El apellido es requerido")
    private String lastName;
    @NotNull(message = "El dni es requerido.")
    private Integer dni;
    @NotBlank(message = "El telefono es requerido")
    private String phone;
    @NotBlank(message = "La direccion es requerida")
    private String address;
  }

  /**
   * Dto para traer los datos del cliente.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ClientDto {
    private Long id;
    private String name;
    private String lastName;
    private Integer dni;
    private String phone;
    private String address;
    private Set<Sale.SaleClientDto> buys = new HashSet<>();
  }

  /**
   * Dto con la información necesaria del cliente para las ventas.
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ClientSaleDto {
    private Long id;
    private String name;
    private String lastName;
    private Integer dni;
    private String phone;
    private String address;
  }
}
