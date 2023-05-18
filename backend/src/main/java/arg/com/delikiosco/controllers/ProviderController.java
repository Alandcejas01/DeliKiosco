package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.MessageDto;
import arg.com.delikiosco.entities.Provider;
import arg.com.delikiosco.services.ProviderService;
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
 * Controller con los endpoints para los metodos del service de Provider.
 */
@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

  private final ProviderService providerService;

  @GetMapping
  public ResponseEntity<Set<Provider.ProviderDto>> getProvidersActive() {
    Set<Provider.ProviderDto> response = providerService.getProvidersActive();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<Set<Provider.ProviderDto>> getAllProviders() {
    Set<Provider.ProviderDto> response = providerService.getAllProviders();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<MessageDto> createProvider(
          @Valid @RequestBody Provider.ProviderDto providerDto) {
    MessageDto response = providerService.createProvider(providerDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{providerId}")
  public ResponseEntity<MessageDto> updateProvider(@PathVariable Long providerId,
      @Valid @RequestBody Provider.ProviderInfoDto providerDto) {
    MessageDto response = providerService.updateProvider(providerId, providerDto);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/toggleActive/{providerId}")
  public ResponseEntity<MessageDto> toggleActiveProvider(@PathVariable Long providerId) {
    MessageDto response = providerService.toggleActiveProvider(providerId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
