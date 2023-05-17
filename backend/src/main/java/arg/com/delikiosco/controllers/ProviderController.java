package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Provider;
import arg.com.delikiosco.services.ProviderService;
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
 * Controller con los endpoints para los metodos del service de Provider.
 */
@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

  private final ProviderService providerService;

  @GetMapping
  public ResponseEntity<Set<Provider.ProviderDto>> getProvidersActive() {
    return ResponseEntity.ok(providerService.getProvidersActive());
  }

  @GetMapping("/all")
  public ResponseEntity<Set<Provider.ProviderDto>> getAllProviders() {
    return ResponseEntity.ok(providerService.getAllProviders());
  }

  @PostMapping("/create")
  public ResponseEntity<MessageDto> createProvider(
          @Valid @RequestBody Provider.ProviderDto providerDto) {
    return ResponseEntity.ok(providerService.createProvider(providerDto));
  }

  @PutMapping("/update/{providerId}")
  public ResponseEntity<MessageDto> updateProvider(@PathVariable Long providerId,
      @Valid @RequestBody Provider.ProviderInfoDto providerDto) {
    return ResponseEntity.ok(providerService.updateProvider(providerId, providerDto));
  }

  @DeleteMapping("/toggleActive/{providerId}")
  public ResponseEntity<MessageDto> toggleActiveProvider(@PathVariable Long providerId) {
    return ResponseEntity.ok(providerService.toggleActiveProvider(providerId));
  }
}
