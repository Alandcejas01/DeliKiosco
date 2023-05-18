package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Client;
import arg.com.delikiosco.entities.Sale;
import arg.com.delikiosco.services.ClientService;
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
 * Controller con los endpoints para los metodos del service de Client.
 */
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;

  @GetMapping
  public ResponseEntity<Set<Client.ClientDto>> getClientsActive() {
    Set<Client.ClientDto> response = clientService.getClientsActive();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<Set<Client.ClientDto>> getAllClients() {
    Set<Client.ClientDto> response = clientService.getAllClients();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<MessageDto> createProvider(
          @Valid @RequestBody Client.ClientRequest clientDto) {
    MessageDto response = clientService.createClient(clientDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{clientId}")
  public ResponseEntity<MessageDto> updateProvider(@PathVariable Long clientId,
      @Valid @RequestBody Client.ClientRequest clientDto) {
    MessageDto response = clientService.updateClient(clientId, clientDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/toggleActive/{clientId}")
  public ResponseEntity<MessageDto> toggleActiveProvider(@PathVariable Long clientId) {
    MessageDto response = clientService.toggleActiveClient(clientId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/buys")
  public ResponseEntity<Set<Sale.SaleDto>> getBuys() {
    Set<Sale.SaleDto> response = clientService.getBuys();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
