package info.sasinski.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerBase {

    protected <T> ResponseEntity<T> notFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    protected <T> ResponseEntity<T> ok(T clazz) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clazz);
    }

    protected <T> ResponseEntity<T> conflict(T clazz) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(clazz);
    }

    protected ResponseEntity<Void> noContent() {
        return ResponseEntity
                .noContent()
                .build();
    }

    protected ResponseEntity<Void> created() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
