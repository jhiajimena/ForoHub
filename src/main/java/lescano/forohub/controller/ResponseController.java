package lescano.forohub.controller;


import lescano.forohub.dto.response.*;
import lescano.forohub.dto.response.*;
import lescano.forohub.service.response.ResponseService;
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
@RequestMapping("/api/responses")
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    //Create response
    @PostMapping
    @Transactional
    public ResponseEntity<DetailsResponseDTO> createResponse(
            @Valid @RequestBody CreateResponseDTO data, UriComponentsBuilder uriComponentsBuilder) {
        DetailsResponseDTO response = responseService.createResponse(data);
        URI url = uriComponentsBuilder.path("/api/response/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    //Get response by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getResponseById(@Valid @PathVariable Long id) {
        ResponseDTO responseDto = responseService.getById(id);
        return ResponseEntity.ok(responseDto);
    }

    //Update response
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageResponseDTO> updateResponse(
            @Valid @PathVariable Long id, @Valid @RequestBody UpdateResponse data) throws AccessDeniedException {
        MessageResponseDTO responseDTO = responseService.updateResponse(id, data);
        return ResponseEntity.ok(responseDTO);
    }

    //Delete Response
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteResponse(@Valid @PathVariable Long id) {
        MessageResponseDTO responseDto = responseService.deleteResponse(id);
        return ResponseEntity.ok(responseDto);
    }

    //Get all responses
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ResponseDTO>>> getAllResponses(
            @PageableDefault(sort = "topic_id") Pageable pageable,
            PagedResourcesAssembler<ResponseDTO> assembler){
        Page<ResponseDTO> responseDTOPage = responseService.geAllResponses(pageable);
        PagedModel<EntityModel<ResponseDTO>> pagedModel = assembler.toModel(responseDTOPage);
        return ResponseEntity.ok().body(pagedModel);
    }
}
