package lescano.forohub.controller;

import lescano.forohub.dto.response.ResponseDTO;
import lescano.forohub.dto.topic.*;
import lescano.forohub.dto.topic.*;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.service.topic.TopicService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    //Create topic
    @PostMapping
    public ResponseEntity<ResponseMessageTopicDTO> createTopic(@RequestBody @Valid CreateTopicDTO data,
                                                               UriComponentsBuilder uriComponentsBuilder) {

        ResponseMessageTopicDTO response = topicService.createTopic(data);
        URI url = uriComponentsBuilder.path("/api/topic/{id}")
                .buildAndExpand(response.topic().id())
                .toUri();
        return ResponseEntity.created(url).body(response);
    }

    //Get topic by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTopicDTO> getById(@Valid @PathVariable Long id) {
        ResponseTopicDTO topicDTO = topicService.getById(id);
        return ResponseEntity.ok(topicDTO);
    }

    //Put topic
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMessageTopicDTO> updateTopic(
            @Valid @PathVariable Long id, @Valid @RequestBody UpdateTopicDTO data) throws AccessDeniedException {
        ResponseMessageTopicDTO response = topicService.updateTopic(id, data);
        return ResponseEntity.ok(response);
    }

    //Delete topic
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageTopicDTO> deleteTopic(
            @Valid @PathVariable Long id) {
        ResponseMessageTopicDTO response = topicService.deleteTopic(id);
        return ResponseEntity.ok(response);
    }

    //Get all topics
    @GetMapping
    //@PreAuthorize("hasAuthority('VIEW_TOPIC')")
    public ResponseEntity<PagedModel<EntityModel<TopicDetailsDTO>>> getAllTopics(
            @PageableDefault(sort = "creationDate")
            Pageable pageable,
            PagedResourcesAssembler<TopicDetailsDTO> assembler,
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "status", required = false) TopicStatus status,
            @RequestParam(value = "type", required = false) TopicType type) {
        Page<TopicDetailsDTO> page = topicService.getTopics(courseId, status, type, pageable);
        PagedModel<EntityModel<TopicDetailsDTO>> pagedModel = assembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    //Get responses by topic id
    @GetMapping("/{id}/responses")
    public ResponseEntity<PagedModel<EntityModel<ResponseDTO>>> getResponsesByTopicId(
            @Valid @PathVariable Long id, @PageableDefault(sort = "creationDate") Pageable pageable,
            PagedResourcesAssembler<ResponseDTO> assembler,
            @RequestParam(value = "status", required = false) TopicStatus status) {
        Page<ResponseDTO> responseDTOS = topicService.getResponsesByTopicId(id, status, pageable);
        PagedModel<EntityModel<ResponseDTO>> pagedModel = assembler.toModel(responseDTOS);
        return ResponseEntity.ok(pagedModel);
    }


}
