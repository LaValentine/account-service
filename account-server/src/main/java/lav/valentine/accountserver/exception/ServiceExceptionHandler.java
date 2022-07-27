package lav.valentine.accountserver.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<String> apiExceptionHandler(ServiceException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.getMessage());
    }
}