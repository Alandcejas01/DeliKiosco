package arg.com.delikiosco.services;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Product;
import arg.com.delikiosco.entities.Provider;
import arg.com.delikiosco.exceptions.GeneralException;
import arg.com.delikiosco.repositories.ProductRepository;
import arg.com.delikiosco.repositories.ProviderRepository;
import arg.com.delikiosco.services.interfaces.ProductServiceInterface;
import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Servicio que posee toda la logica CRUD y de nogocio de los Productos.
 */
@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface {

  private final ProductRepository productRepository;

  private final ProviderRepository providerRepository;
  ModelMapper mapper = new ModelMapper();

  @Override
  public Set<Product.ProductDto> getProductsActive() {
    Set<Product> products = productRepository.findActives();
    return products.stream()
        .map(product -> mapper.map(product, Product.ProductDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Product.ProductDto::getId))));
  }

  @Override
  public Set<Product.ProductDto> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(product -> mapper.map(product, Product.ProductDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Product.ProductDto::getId))));
  }

  @Override
  @Transactional
  public MessageDto createProduct(Long providerId, Product.ProductRequest productDto) {
    Provider provider = providerRepository.findById(providerId)
        .orElseThrow(() -> new GeneralException("El proveedor no existe",
            HttpStatus.BAD_REQUEST));
    if (productRepository.findByName(productDto.getName()).isPresent()) {
      throw new GeneralException("El nombre del producto ya existe",
          HttpStatus.BAD_REQUEST);
    }
    Product product = mapper.map(productDto, Product.class);
    product.setProvider(provider);
    productRepository.save(product);
    return new MessageDto("Producto agregado con exito.");
  }

  @Override
  public MessageDto updateProduct(Long productId, Product.ProductRequest productDto) {
    Product existingProduct = productRepository.findById(productId)
        .orElseThrow(() -> new GeneralException("El producto no existe",
            HttpStatus.BAD_REQUEST));

    Optional<Product> product = productRepository
        .findByName(productDto.getName());
    product.ifPresent(productName -> {
      if (!existingProduct.getName().equals(productName.getName())) {
        throw new GeneralException("El nombre ya esta asociado a un producto",
            HttpStatus.BAD_REQUEST);
      }
    });
    mapper.map(productDto, existingProduct);
    existingProduct.setId(productId);
    productRepository.save(existingProduct);
    return new MessageDto("Producto actualizado.");
  }

  @Override
  public MessageDto toggleActiveProduct(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new GeneralException("No existe el producto",
            HttpStatus.BAD_REQUEST));
    product.setActive(!product.getActive());
    productRepository.save(product);
    return new MessageDto("Producto activado/desactivado con éxito.");
  }

  @Override
  public Set<Product.ProductDto> getProductsByLowStock(int lowStockQuantity) {
    Set<Product> products = productRepository.findByLowStock(lowStockQuantity);
    return products.stream()
        .map(product -> mapper.map(product, Product.ProductDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Product.ProductDto::getId))));
  }

}
