package lav.valentine.accountserver.exception.ext;

import lav.valentine.accountserver.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends ServiceException {
    public InsufficientFundsException(String message) {
        super(message);
        super.setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
