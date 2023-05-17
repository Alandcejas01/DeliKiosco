package arg.com.delikiosco.services;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.dtos.ProductCarpinchoDto;
import arg.com.delikiosco.exceptions.GeneralException;
import arg.com.delikiosco.services.interfaces.CarpinchoServiceInterface;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Servicio que consume el api externa e implementa la logica requerida.
 */
@Service
@RequiredArgsConstructor
public class CarpinchoService implements CarpinchoServiceInterface {

  @Value("${spring.external.service.base-url}")
  private String baseUrl;

  private final RestTemplate restTemplate;

  private static ModelMapper carpinchoMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProductCarpinchoDto.class, ProductCarpinchoDto.TransformedProductDto.class)
        .addMappings(mapper -> {
          mapper.map(ProductCarpinchoDto::getProductCode,
              ProductCarpinchoDto.TransformedProductDto::setSku);
          mapper.map(ProductCarpinchoDto::getProductQuantity,
              ProductCarpinchoDto.TransformedProductDto::setStock);
          mapper.map(ProductCarpinchoDto::getProductImg,
              ProductCarpinchoDto.TransformedProductDto::setImgUrl);
        });
    return modelMapper;
  }

  ModelMapper mapper = carpinchoMapper();

  @Override
  public Set<ProductCarpinchoDto.TransformedProductDto> getProducts() {
    ProductCarpinchoDto[] response = restTemplate
        .getForObject(baseUrl + "/products", ProductCarpinchoDto[].class);
    if (response == null) {
      throw new GeneralException("La respuesta es nula", HttpStatus.BAD_REQUEST);
    }
    return Arrays.stream(response)
        .map(product -> mapper.map(product, ProductCarpinchoDto.TransformedProductDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(ProductCarpinchoDto.TransformedProductDto::getId))));
  }

  @Override
  public MessageDto createProduct(ProductCarpinchoDto productDto) {
    try {
      restTemplate.postForObject(baseUrl + "/products", productDto, ProductCarpinchoDto.class);
      return new MessageDto("Producto creado con éxito. ");
    } catch (RuntimeException e) {
      throw new GeneralException("Hubo un error", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public MessageDto updateProduct(Integer id, ProductCarpinchoDto productDto) {
    try {
      restTemplate.put(baseUrl + "/products/" + id, productDto);
      return new MessageDto("Producto actualizado con éxito.");
    } catch (RuntimeException e) {
      throw new GeneralException("Hubo un error", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public MessageDto deleteProduct(Integer id) {
    try {
      restTemplate.delete(baseUrl + "/products/" + id);
      return new MessageDto("Producto eliminado.");
    } catch (RuntimeException e) {
      throw new GeneralException("Hubo un error", HttpStatus.BAD_REQUEST);
    }
  }
}
