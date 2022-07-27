package lav.valentine.accountserver.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ServiceException extends RuntimeException {

    private HttpStatus httpStatus;

    public ServiceException(String message) {
        super(message);
    }
}