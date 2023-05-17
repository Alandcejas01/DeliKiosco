package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Client;
import arg.com.delikiosco.entities.Sale;
import arg.com.delikiosco.services.ClientService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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
    return ResponseEntity.ok(clientService.getClientsActive());
  }

  @GetMapping("/all")
  public ResponseEntity<Set<Client.ClientDto>> getAllClients() {
    return ResponseEntity.ok(clientService.getAllClients());
  }

  @PostMapping("/create")
  public ResponseEntity<MessageDto> createProvider(
          @Valid @RequestBody Client.ClientRequest clientDto) {
    return ResponseEntity.ok(clientService.createClient(clientDto));
  }

  @PutMapping("/update/{clientId}")
  public ResponseEntity<MessageDto> updateProvider(@PathVariable Long clientId,
      @Valid @RequestBody Client.ClientRequest clientDto) {
    return ResponseEntity.ok(clientService.updateClient(clientId, clientDto));
  }

  @DeleteMapping("/toggleActive/{clientId}")
  public ResponseEntity<MessageDto> toggleActiveProvider(@PathVariable Long clientId) {
    return ResponseEntity.ok(clientService.toggleActiveClient(clientId));
  }

  @GetMapping("/buys")
  public ResponseEntity<Set<Sale.SaleDto>> getBuys() {
    return ResponseEntity.ok(clientService.getBuys());
  }
}
