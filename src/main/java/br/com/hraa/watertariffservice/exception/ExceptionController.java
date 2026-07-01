package br.com.hraa.watertariffservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = ClientCategoryException.class)
    public ResponseEntity<Object> exception(ClientCategoryException exception) {
        return new ResponseEntity<>("Client category not found.", HttpStatus.NOT_FOUND);
    }

    // Every IllegalArgumentException thrown contains clear but safe messages.
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> exception(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
