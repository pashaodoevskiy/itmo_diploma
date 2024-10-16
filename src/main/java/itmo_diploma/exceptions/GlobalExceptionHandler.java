package itmo_diploma.exceptions;

import itmo_diploma.responses.errors.ErrorResponse;
import itmo_diploma.responses.errors.ValidationErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                ));

        return ResponseEntity.badRequest().body(new ValidationErrorResponse("ValidationException", errors));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(ValidationException ex) {
        return ResponseEntity.badRequest().body(new ValidationErrorResponse("ValidationException", ex.getErrors()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(EntityNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("EntityNotFoundException", ex.getMessage()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(AuthorizationDeniedException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("AuthorizationDeniedException", ex.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(CustomException ex) {
        return ResponseEntity.status(ex.getCode()).body(new ErrorResponse("InternalError", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("InternalError", ex.getMessage()));
    }
}

