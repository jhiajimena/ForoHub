package lescano.forohub.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int statusCode;
    private List<DataErrorValidation> errors;

    public ErrorResponse(String message, int statusCode){
        this.message = message;
        this.statusCode=statusCode;
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(String message, int statusCode, List<DataErrorValidation> errors){
        this.message = message;
        this.statusCode=statusCode;
        this.errors = errors != null ? errors : new ArrayList<>();
    }

}
