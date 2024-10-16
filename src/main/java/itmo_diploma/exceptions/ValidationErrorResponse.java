package itmo_diploma.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse {
    private Boolean success = false;
    private String type;
    private Map<String, List<String>> errors;

    public ValidationErrorResponse(String type, Map<String, List<String>> errors) {
        this.type = type;
        this.errors = errors;
    }
}
