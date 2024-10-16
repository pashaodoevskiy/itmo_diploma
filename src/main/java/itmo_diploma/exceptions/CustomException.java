package itmo_diploma.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final Integer code;
    private final String message;

    public CustomException(String message, Integer code) {
        this.code = code;
        this.message = message;
    }

    public CustomException(String message) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
    }
}