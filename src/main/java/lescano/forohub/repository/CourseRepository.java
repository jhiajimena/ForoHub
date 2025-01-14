package lescano.forohub.repository;

import lescano.forohub.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course,Long> {
    boolean existsByName(String name);
    Page<Course> findAll( Pageable pageable);
    @Query(value = """
            SELECT c from Course c WHERE c.subCategory.id = :id
            """)
    Page<Course> findCoursesBySubCategoryId(Long id, Pageable pageable);

}
