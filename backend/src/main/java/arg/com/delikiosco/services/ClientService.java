package arg.com.delikiosco.services;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Client;
import arg.com.delikiosco.entities.Sale;
import arg.com.delikiosco.exceptions.GeneralException;
import arg.com.delikiosco.repositories.ClientRepository;
import arg.com.delikiosco.services.interfaces.ClientServiceInterface;
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
 * Servicio que posee toda la logica CRUD y de nogocio de los Clientes.
 */
@Service
@RequiredArgsConstructor
public class ClientService implements ClientServiceInterface {

  private final ClientRepository clientRepository;

  ModelMapper mapper = new ModelMapper();

  @Override
  public Set<Client.ClientDto> getClientsActive() {
    Set<Client> clients = clientRepository.findActives();
    return clients.stream()
        .map(client -> mapper.map(client, Client.ClientDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Client.ClientDto::getId))));
  }

  @Override
  public Set<Client.ClientDto> getAllClients() {
    List<Client> clients = clientRepository.findAll();
    return clients.stream()
        .map(client -> mapper.map(client, Client.ClientDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Client.ClientDto::getId))));
  }

  @Override
  @Transactional
  public MessageDto createClient(Client.ClientRequest clientDto) {
    if (clientRepository.findByDni(clientDto.getDni()).isPresent()) {
      throw new GeneralException("El Cliente ya existe",
          HttpStatus.BAD_REQUEST);
    }
    Client client = mapper.map(clientDto, Client.class);
    clientRepository.save(client);
    return new MessageDto("Cliente agregado con exito.");
  }

  @Override
  public MessageDto updateClient(Long clientId, Client.ClientRequest clientDto) {
    Client existingClient = clientRepository.findById(clientId)
        .orElseThrow(() -> new GeneralException("El cliente no existe",
            HttpStatus.NOT_FOUND));

    Optional<Client> client = clientRepository
        .findByDni(clientDto.getDni());
    client.ifPresent(clientDni -> {
      if (!existingClient.getDni().equals(clientDni.getDni())) {
        throw new GeneralException("El dni ya esta asociado a un cliente",
            HttpStatus.BAD_REQUEST);
      }
    });
    mapper.map(clientDto, existingClient);
    existingClient.setId(clientId);
    clientRepository.save(existingClient);
    return new MessageDto("Cliente actualizado.");
  }

  @Override
  public MessageDto toggleActiveClient(Long clientId) {
    Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new GeneralException("No existe el cliente",
            HttpStatus.NOT_FOUND));
    client.setActive(!client.getActive());
    clientRepository.save(client);
    String status = (client.getActive()) ? "Activado" : "Desactivado";
    return new MessageDto("Cliente " + status + " con éxito.");
  }

  @Override
  public Set<Sale.SaleDto> getBuys() {
    Set<Sale> sales = clientRepository.findBuys();
    return sales.stream()
        .map(sale -> mapper.map(sale, Sale.SaleDto.class))
        .collect(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(Sale.SaleDto::getId))));
  }
}
