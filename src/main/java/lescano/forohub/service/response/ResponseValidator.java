package lescano.forohub.service.response;

import lescano.forohub.entities.Response;
import lescano.forohub.repository.ResponseRepository;
import lescano.forohub.service.validations.AbstractEntityValidator;
import org.springframework.stereotype.Component;

@Component
public class ResponseValidator extends AbstractEntityValidator<Response> {

    private final ResponseRepository repository;

    public ResponseValidator (ResponseRepository repository){
        super(repository);
        this.repository=repository;
    }
}
