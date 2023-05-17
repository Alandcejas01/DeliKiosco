package arg.com.delikiosco.services.interfaces;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.dtos.ProductCarpinchoDto;
import java.util.Set;

/**
 * Interfaz con los metodos a implementar en el CarpinchoService.
 */
public interface CarpinchoServiceInterface {

  Set<ProductCarpinchoDto.TransformedProductDto> getProducts();

  MessageDto createProduct(ProductCarpinchoDto productDto);

  MessageDto updateProduct(Integer id, ProductCarpinchoDto productDto);

  MessageDto deleteProduct(Integer id);
}
