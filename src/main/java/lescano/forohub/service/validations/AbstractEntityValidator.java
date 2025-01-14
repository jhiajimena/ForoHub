package lescano.forohub.service.validations;

import lescano.forohub.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractEntityValidator<T> implements EntityValidator<T> {

    private final JpaRepository<T, Long> repository;

    protected AbstractEntityValidator(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public void validateExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource with ID " + id + " was not found.");
        }
    }

}
