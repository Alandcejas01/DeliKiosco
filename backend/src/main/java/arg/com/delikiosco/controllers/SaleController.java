package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Sale;
import arg.com.delikiosco.services.SaleService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
    * Controller con los endpoints para los metodos del service de Sale.
 */
@RestController
@RequestMapping("/api/v1/sale")
@RequiredArgsConstructor
public class SaleController {

  private final SaleService saleService;

  @GetMapping
  public ResponseEntity<Set<Sale.SaleDto>> getSales() {
    Set<Sale.SaleDto> response = saleService.getSales();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/makeSale")
  public ResponseEntity<MessageDto> makeSale(
      @Valid @RequestBody Sale.SaleRequest saleDto) {
    MessageDto response = saleService.makeSale(saleDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{date}")
  public ResponseEntity<Set<Sale.SaleDto>> getSalesByDate(@PathVariable String date) {
    Set<Sale.SaleDto> response = saleService.getSalesByDate(date);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/provider/{cuitProvider}")
  public ResponseEntity<Set<Sale.SaleDto>> getSalesByProvider(@PathVariable Long cuitProvider) {
    Set<Sale.SaleDto> response = saleService.getSalesByProvider(cuitProvider);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}