package arg.com.delikiosco.controllers;

import arg.com.delikiosco.dtos.ErrorDto;
import arg.com.delikiosco.exceptions.GeneralException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controlador de excepciones para los controllers Rest para el manejo de errores en la aplicación.
 */
@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = GeneralException.class)
  public ResponseEntity<ErrorDto> requestExceptionHandler(GeneralException ex) {
    ErrorDto error = ErrorDto.builder().message(ex.getMessage()).build();
    return new ResponseEntity<>(error, ex.getStatus());
  }

  /**
   * Este metodo maneja excepciones de validación y
   * devuelve un mapa de errores que contiene información sobre qué campos
   * fallaron la validación.
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }
}
