package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.dtos.ProductCarpinchoDto;
import arg.com.delikiosco.services.CarpinchoService;
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
 * Controller con los endpoints para los metodos que consumen la api externa.
 */
@RestController
@RequestMapping("/api/v1/carpincho")
@RequiredArgsConstructor
public class CarpinchoController {

  private final CarpinchoService carpinchoService;

  @GetMapping
  public ResponseEntity<Set<ProductCarpinchoDto.TransformedProductDto>> getProducts() {
    Set<ProductCarpinchoDto.TransformedProductDto> response = carpinchoService.getProducts();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<MessageDto> createProduct(
      @Valid @RequestBody ProductCarpinchoDto productDto) {
    MessageDto response = carpinchoService.createProduct(productDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{productId}")
  public ResponseEntity<MessageDto> updateProduct(
      @PathVariable Integer productId,
      @Valid @RequestBody ProductCarpinchoDto productDto) {
    MessageDto response = carpinchoService.updateProduct(productId, productDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{productId}")
  public ResponseEntity<MessageDto> deleteById(@PathVariable Integer productId) {
    MessageDto response = carpinchoService.deleteProduct(productId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
