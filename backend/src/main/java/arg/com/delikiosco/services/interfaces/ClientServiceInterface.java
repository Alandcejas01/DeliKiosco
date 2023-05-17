package arg.com.delikiosco.services.interfaces;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Client;
import arg.com.delikiosco.entities.Sale;
import java.util.Set;

/**
 * Interfaz con los metodos a implementar en el ProviderService.
 */
public interface ClientServiceInterface {

  Set<Client.ClientDto> getClientsActive();

  Set<Client.ClientDto> getAllClients();

  MessageDto createClient(Client.ClientRequest clientDto);

  MessageDto updateClient(Long clientId, Client.ClientRequest clientDto);

  MessageDto toggleActiveClient(Long clientId);

  Set<Sale.SaleDto> getBuys();

}
