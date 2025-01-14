package lescano.forohub.repository;

import lescano.forohub.entities.Topic;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic,Long> {
    boolean existsByTitle(String title);
    boolean existsByMessage(String message);
    Page<Topic> findAll(Pageable pageable);


    @Query("""
            SELECT t FROM Topic t
            LEFT JOIN Response r ON r.topic.id =t.id
            WHERE (:courseId IS NULL OR t.course.id = :courseId)
            AND (:status IS NULL OR t.status = :status)
            AND (:type IS NULL OR t.type = :type)
            """)
   Page<Topic> findTopics(Long courseId, TopicStatus status, TopicType type,Pageable pageable);


    @Query("""
            SELECT t FROM Topic t
            LEFT JOIN Response r ON r.topic.id=t.id
            LEFT JOIN Course c ON t.course.id = c.id
            WHERE (:subcategoryId IS NULL OR c.subCategory.id = :subcategoryId)
            AND (:status IS NULL OR t.status = :status)
            AND (:type IS NULL OR t.type = :type)
            """)
   Page<Topic> findTopicsBySubcategory(Long subcategoryId,TopicStatus status, TopicType type, Pageable pageable);

    @Query("""
            SELECT t FROM Topic t
            LEFT JOIN Response r ON r.topic.id = t.id
            LEFT JOIN Course c ON t.course.id = c.id
            LEFT JOIN SubCategory s ON c.subCategory.id = s.id
            WHERE (:id IS NULL OR s.category.id = :id)
            AND (:status IS NULL OR t.status = :status)
            AND (:type IS NULL OR t.type = :type)
            """)
    Page<Topic> findTopicsByCategory(Long id, TopicStatus status, TopicType type, Pageable pageable);

    @Query("""
            SELECT DISTINCT t
            FROM Topic t
            WHERE t.author.id = :id
            """)
    Page<Topic> findTopicsByAuthor(Long id,Pageable pageable);

    @Query("""
            SELECT DISTINCT t
            FROM Topic t
            LEFT JOIN Response r ON r.topic.id= t.id
            WHERE r.author.id = :id
            """)
    Page<Topic> findTopicsByAuthorResponse(Long id,Pageable pageable);
}
