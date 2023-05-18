package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Product;
import arg.com.delikiosco.services.ProductService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller con los endpoints para los metodos del service de Product.
 */
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<Set<Product.ProductDto>> getProductsActive() {
    Set<Product.ProductDto> response = productService.getProductsActive();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<Set<Product.ProductDto>> getAllProducts() {
    Set<Product.ProductDto> response = productService.getAllProducts();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/create/{providerId}")
    public ResponseEntity<MessageDto> createProduct(
        @PathVariable Long providerId,
        @Valid @RequestBody Product.ProductRequest productRequest) {
    MessageDto response = productService.createProduct(providerId, productRequest);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{productId}")
  public ResponseEntity<MessageDto> updateProduct(@PathVariable Long productId,
          @Valid @RequestBody Product.ProductRequest productDto) {
    MessageDto response = productService.updateProduct(productId, productDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/toggleActive/{productId}")
  public ResponseEntity<MessageDto> deleteById(@PathVariable Long productId) {
    MessageDto response = productService.toggleActiveProduct(productId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/lowStock/{lowStockQuantity}")
  public ResponseEntity<Set<Product.ProductDto>> getProductsByLowStock(
      @PathVariable Integer lowStockQuantity) {
    Set<Product.ProductDto> response = productService.getProductsByLowStock(lowStockQuantity);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
