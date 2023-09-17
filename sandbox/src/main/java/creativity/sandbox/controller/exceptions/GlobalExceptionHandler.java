package creativity.sandbox.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.TransientObjectException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransientObjectException.class)
    public ResponseEntity<StandardError> transientObjectException(TransientObjectException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .message(e.getClass().getName())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .title("Transient Exception")
                        .details(e.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BadRequestExceptionDetails> resourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(e.getClass().getName())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Resource Not Found")
                        .details(e.getMessage())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BadRequestExceptionDetails> resourceNotFoundException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(e.getClass().getName())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Resource Not Found")
                        .details(e.getMessage())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> resourceNotFoundException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String errors = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(e.getClass().getName())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request. Invalid fields")
                        .details(e.getMessage())
                        .fields(fields)
                        .fieldMessage(errors)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}
