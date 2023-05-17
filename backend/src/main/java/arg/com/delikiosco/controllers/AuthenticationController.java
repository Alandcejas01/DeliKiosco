package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.auth.AuthenticationRequestDto;
import arg.com.delikiosco.dtos.auth.AuthenticationResponseDto;
import arg.com.delikiosco.dtos.auth.RegisterRequestDto;
import arg.com.delikiosco.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller con los endpoints para el registro y login del Usuario.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponseDto> register(
      @Valid @RequestBody RegisterRequestDto request
  ) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> authenticate(
      @Valid @RequestBody AuthenticationRequestDto request
  ) {
    return ResponseEntity.ok(authenticationService.authenticate(request));
  }

}
