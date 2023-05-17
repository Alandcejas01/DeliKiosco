package arg.com.delikiosco.services;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Product;
import arg.com.delikiosco.entities.Provider;
import arg.com.delikiosco.exceptions.GeneralException;
import arg.com.delikiosco.repositories.ProviderRepository;
import arg.com.delikiosco.services.interfaces.ProviderServiceInterface;
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
 * Servicio que posee toda la logica CRUD y de nogocio de los Proveedores.
 */
@Service
@RequiredArgsConstructor
public class ProviderService implements ProviderServiceInterface {

  private final ProviderRepository providerRepository;

  private final ProductService productService;

  ModelMapper mapper = new ModelMapper();

  @Override
  public Set<Provider.ProviderDto> getProvidersActive() {
    Set<Provider> providers = providerRepository.findActives();
    return providers.stream()
        .map(provider -> mapper.map(provider, Provider.ProviderDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Provider.ProviderDto::getId))));
  }

  @Override
  public Set<Provider.ProviderDto> getAllProviders() {
    List<Provider> providers = providerRepository.findAll();
    return providers.stream()
        .map(provider -> mapper.map(provider, Provider.ProviderDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Provider.ProviderDto::getId))));
  }

  @Override
  @Transactional
  public MessageDto createProvider(Provider.ProviderDto providerDto) {
    if (providerRepository.findByCuit(providerDto.getCuit()).isPresent()) {
      throw new GeneralException("El Proveedor ya existe",
          HttpStatus.BAD_REQUEST);
    }
    Provider provider = mapper.map(providerDto, Provider.class);
    providerRepository.save(provider);
    Set<Product> products = provider.getProducts();
    if (products.size() < providerDto.getProducts().size()) {
      throw new GeneralException("Hay productos repetidos por favor compruebelos",
          HttpStatus.BAD_REQUEST);
    }
    for (Product product : products) {
      Product.ProductRequest productRequest = mapper.map(product, Product.ProductRequest.class);
      productService.createProduct(provider.getId(), productRequest);
    }
    return new MessageDto("Proveedor agregado con exito.");
  }

  @Override
  public MessageDto updateProvider(Long providerId, Provider.ProviderInfoDto providerDto) {
    Provider existingProvider = providerRepository.findById(providerId)
        .orElseThrow(() -> new GeneralException("El proveedor no existe",
            HttpStatus.NOT_FOUND));

    Optional<Provider> provider = providerRepository
        .findByCuit(providerDto.getCuit());
    provider.ifPresent(providerName -> {
      if (!existingProvider.getCuit().equals(providerName.getCuit())) {
        throw new GeneralException("El cuit ya esta asociado a un proveedor",
            HttpStatus.BAD_REQUEST);
      }
    });
    mapper.map(providerDto, existingProvider);
    existingProvider.setId(providerId);
    providerRepository.save(existingProvider);
    return new MessageDto("Proveedor actualizado.");
  }

  @Override
  public MessageDto toggleActiveProvider(Long productId) {
    Provider provider = providerRepository.findById(productId)
        .orElseThrow(() -> new GeneralException("No existe el proveedor",
            HttpStatus.NOT_FOUND));
    provider.setActive(!provider.getActive());
    providerRepository.save(provider);
    String status = (provider.getActive()) ? "Activado" : "Desactivado";
    return new MessageDto("Proveedor " + status + " con éxito.");
  }

}
