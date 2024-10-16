package itmo_diploma.responses.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private Boolean success = false;
    private String type;
    private String message;

    public ErrorResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }

}
