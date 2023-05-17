package arg.com.delikiosco.services.interfaces;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Provider;
import java.util.Set;

/**
 * Interfaz con los metodos a implementar en el ProviderService.
 */
public interface ProviderServiceInterface {

  Set<Provider.ProviderDto> getProvidersActive();

  Set<Provider.ProviderDto> getAllProviders();

  MessageDto createProvider(Provider.ProviderDto providerDto);

  MessageDto updateProvider(Long providerId, Provider.ProviderInfoDto providerDto);

  MessageDto toggleActiveProvider(Long providerId);

}
