package itmo_diploma.controllers;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Controller {

    public <T> ResponseEntity<Object> response(T data) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", data
        ));
    }

    public ResponseEntity<Object> response() {
        return ResponseEntity.ok(Map.of(
                "success", true
        ));
    }
}
