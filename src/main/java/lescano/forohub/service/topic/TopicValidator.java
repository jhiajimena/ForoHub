package lescano.forohub.service.topic;

import lescano.forohub.exception.ResourceAlreadyExistException;
import lescano.forohub.entities.Topic;
import lescano.forohub.repository.TopicRepository;
import lescano.forohub.service.validations.AbstractEntityValidator;
import org.springframework.stereotype.Component;

@Component
public class TopicValidator extends AbstractEntityValidator<Topic> {

    private final TopicRepository topicRepository;

    public TopicValidator(TopicRepository topicRepository) {
        super(topicRepository);
        this.topicRepository = topicRepository;
    }

    public void validationTopicExistsByTitle(String title) {
        if (topicRepository.existsByTitle(title)) {
            throw new ResourceAlreadyExistException("A Topic with the title " + title + " is already exists.");
        }
    }
    public void validationTopicExistsByMessage(String message) {
        if (topicRepository.existsByMessage(message)) {
            throw new ResourceAlreadyExistException("A Topic with the message '" + message + "' is already exists.");
        }
    }
}
