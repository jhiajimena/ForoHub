package lescano.forohub.repository;

import lescano.forohub.entities.Response;
import lescano.forohub.entities.TopicStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    boolean existsById(Long id);
    Page<Response> findAll(Pageable pageable);

    @Query("""
            SELECT COUNT(r.topic.id) FROM Response r WHERE topic.id= :id
            """)
    int countResponsesInATopicById(Long id);

    @Query("""
            SELECT r FROM Response r WHERE r.topic.id=:id AND topic.status= :status
            """)
    Page<Response> findResponsesByTopicId(@Valid Long id, TopicStatus status, Pageable pageable);
}
