package arg.com.delikiosco.services.interfaces;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Product;
import java.util.Set;

/**
 * Interfaz con los metodos a implementar en el ProductService.
 */
public interface ProductServiceInterface {

  Set<Product.ProductDto> getProductsActive();

  Set<Product.ProductDto> getAllProducts();

  MessageDto createProduct(Long providerId, Product.ProductRequest productDto);

  MessageDto updateProduct(Long productId, Product.ProductRequest productDto);

  MessageDto toggleActiveProduct(Long productId);

  Set<Product.ProductDto> getProductsByLowStock(int lowStockQuantity);

}
