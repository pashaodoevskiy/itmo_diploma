package itmo_diploma.exceptions;

import lombok.Getter;

@Getter
public class RecordAlreadyExistsException extends RuntimeException {

    private final String message;

    public RecordAlreadyExistsException(String message) {
        this.message = message;
    }
}