package arg.com.delikiosco.services;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Client;
import arg.com.delikiosco.entities.Product;
import arg.com.delikiosco.entities.Provider;
import arg.com.delikiosco.entities.Sale;
import arg.com.delikiosco.exceptions.GeneralException;
import arg.com.delikiosco.repositories.ClientRepository;
import arg.com.delikiosco.repositories.ProductRepository;
import arg.com.delikiosco.repositories.SaleRepository;
import arg.com.delikiosco.services.interfaces.SaleServiceInterface;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Servicio que posee toda la logica CRUD y de nogocio de las ventas.
 */
@Service
@RequiredArgsConstructor
public class SaleService implements SaleServiceInterface {

  private final SaleRepository saleRepository;
  private final ClientRepository clientRepository;
  private final ProductRepository productRepository;
  ModelMapper mapper = new ModelMapper();

  @Override
  public Set<Sale.SaleDto> getSales() {
    List<Sale> sales = saleRepository.findAll();
    return sales.stream()
        .map(sale -> mapper.map(sale, Sale.SaleDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Sale.SaleDto::getId))));
  }

  @Override
  @Transactional
  public MessageDto makeSale(Long clientId, Sale.SaleRequest saleDto) {
    Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new GeneralException("El cliente no existe",
            HttpStatus.NOT_FOUND));
    if (!client.getActive()) {
      throw new GeneralException("El cliente se encuentra deshabilitado",
          HttpStatus.BAD_REQUEST);
    }
    Set<Provider> providers = new HashSet<>();
    Set<Product> products = new HashSet<>();
    int amounts = 0;
    BigDecimal totalPrice = new BigDecimal("0.0");
    for (Product.ProductSaleDto productSale : saleDto.getProducts()) {
      Product product = productRepository.findById(productSale.getId())
          .orElseThrow(() -> new GeneralException("No existe el producto",
              HttpStatus.BAD_REQUEST));
      if (!product.getActive()) {
        throw new GeneralException("El producto se encuentra deshabilitado",
            HttpStatus.BAD_REQUEST);
      }
      if (!product.getProvider().getActive()) {
        throw new GeneralException("El proveedor se encuentra deshabilitado",
            HttpStatus.BAD_REQUEST);
      }
      if (product.getStock() <= productSale.getAmount()) {
        throw new GeneralException("No hay suficiente stock para la compra de "
           + productSale.getName() + ".",
            HttpStatus.BAD_REQUEST);
      }
      int newStock = product.getStock() - productSale.getAmount();
      product.setStock(newStock);
      amounts += productSale.getAmount();
      totalPrice = totalPrice.add(productSale.getPrice()
          .multiply(new BigDecimal(productSale.getAmount())));
      providers.add(product.getProvider());
      products.add(product);
      productRepository.save(product);
    }
    Sale sale = Sale.builder()
        .date(LocalDateTime.now())
        .client(client)
        .providers(providers)
        .products(products)
        .amounts(amounts)
        .totalPrice(totalPrice)
        .build();
    saleRepository.save(sale);
    return new MessageDto("Compra realizada con exito.");
  }

  @Override
  public Set<Sale.SaleDto> getSalesByDate(String date) {
    Pattern pattern = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$");
    Matcher matcher = pattern.matcher(date);
    if (!matcher.matches()) {
      throw new GeneralException("La fecha introducida es invalida",
          HttpStatus.BAD_REQUEST);
    }
    LocalDateTime saleDateStartOfDay = LocalDateTime.parse(
        date + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime saleDateEndOfDay = LocalDateTime.parse(
        date + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Set<Sale> sales = saleRepository.getByDate(saleDateStartOfDay, saleDateEndOfDay);
    return sales.stream()
        .map(sale -> mapper.map(sale, Sale.SaleDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Sale.SaleDto::getId))));
  }

  @Override
  public Set<Sale.SaleDto> getSalesByProvider(Long cuitProvider) {
    Set<Sale> sales = saleRepository.getByProvider(cuitProvider);
    return sales.stream()
        .map(sale -> mapper.map(sale, Sale.SaleDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Sale.SaleDto::getId))));
  }

}
