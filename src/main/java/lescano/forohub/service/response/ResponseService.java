package lescano.forohub.service.response;

import lescano.forohub.dto.response.*;
import lescano.forohub.dto.response.*;
import lescano.forohub.entities.ForumUser;
import lescano.forohub.entities.Response;
import lescano.forohub.entities.Topic;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.repository.ResponseRepository;
import lescano.forohub.repository.TopicRepository;
import lescano.forohub.repository.UserRepository;
import lescano.forohub.service.topic.TopicValidator;
import lescano.forohub.service.user.CustomUserDetails;
import lescano.forohub.utils.ConverterData;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final ResponseValidator responseValidator;
    private final TopicRepository topicRepository;
    private final TopicValidator topicValidator;
    private final UserRepository userRepository;

    public ResponseService(
            ResponseRepository responseRepository, ResponseValidator responseValidator, TopicRepository topicRepository,
            TopicValidator topicValidator, UserRepository userRepository) {
        this.responseRepository = responseRepository;
        this.responseValidator = responseValidator;
        this.topicRepository = topicRepository;
        this.topicValidator = topicValidator;
        this.userRepository = userRepository;
    }

    //Create response
    public DetailsResponseDTO createResponse(CreateResponseDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        topicValidator.validateExistsById(data.topicId());
        Topic topic = topicRepository.getReferenceById(data.topicId());
        topic.answeredStatus();
        ForumUser user = userRepository.getReferenceById(userId);
        Response response = new Response(null, data.message(), LocalDateTime.now(), false, topic, user);
        responseRepository.save(response);
        return new DetailsResponseDTO(response);
    }

    //Get response by id
    public ResponseDTO getById(@Valid Long id) {
        responseValidator.validateExistsById(id);
        Response response = responseRepository.getReferenceById(id);
        return new ResponseDTO(response);
    }

    //Update response
    public MessageResponseDTO updateResponse(@Valid Long id, @Valid UpdateResponse data) throws AccessDeniedException {
        responseValidator.validateExistsById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long authenticatedUserId  = userDetails.getId();
        boolean hasEditPermission = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("EDIT_ANY_COMMENT"));

        Response response = responseRepository.getReferenceById(id);

        if (!response.getAuthor().getId().equals(authenticatedUserId) && !hasEditPermission){
            throw new AccessDeniedException("You do not have permission to edit this response.");
        }

        response.updateResponse(data);
        DetailsResponseDTO responseDTO = new DetailsResponseDTO(response);
        return new MessageResponseDTO("Response updated successfully", responseDTO);
    }

    //Delete response
    public MessageResponseDTO deleteResponse(@Valid Long id) {
        responseValidator.validateExistsById(id);
        Response response = responseRepository.getReferenceById(id);
        Topic topic = topicRepository.getReferenceById(response.getTopic().getId());
        int responses = responseRepository.countResponsesInATopicById(response.getId());
        if(responses < 1 ){
            topic.unansweredStatus();
        }
        responseRepository.delete(response);
        DetailsResponseDTO responseDTO = new DetailsResponseDTO(response);
        return new MessageResponseDTO("Response deleted successfully", responseDTO);
    }

    //Get all responses
    public Page<ResponseDTO> geAllResponses(Pageable pageable) {
        Page<Response> responses = responseRepository.findAll(pageable);
        return responses.map(ConverterData::convertToResponseDTO);
    }

    //Get responses by topic id
    public Page<ResponseDTO> getResponsesByTopicId(@Valid Long id, TopicStatus status, Pageable pageable) {
        Page<Response> responses = responseRepository.findResponsesByTopicId(id,status,pageable);
        return responses.map(ConverterData::convertToResponseDTO);
    }
}
