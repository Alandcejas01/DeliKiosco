package arg.com.delikiosco.services.interfaces;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Sale;
import java.util.Set;

/**
 * Interfaz con los metodos a implementar en el SaleService.
 */
public interface SaleServiceInterface {

  Set<Sale.SaleDto> getSales();

  MessageDto makeSale(Long clientId, Sale.SaleRequest saleDto);

  Set<Sale.SaleDto> getSalesByDate(String date);

  Set<Sale.SaleDto> getSalesByProvider(Long cuitProvider);

}
