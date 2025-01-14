package lescano.forohub.exception;

import org.springframework.validation.FieldError;

public record DataErrorValidation(
        String field, String error
) {
    public DataErrorValidation(FieldError error) {
        this(error.getField(),error.getDefaultMessage());
    }
}
