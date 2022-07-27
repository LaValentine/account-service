package lav.valentine.accountserver.exception;

import lav.valentine.accountserver.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<ResponseDto> apiExceptionHandler(ServiceException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ResponseDto(ex.getMessage()));
    }
}