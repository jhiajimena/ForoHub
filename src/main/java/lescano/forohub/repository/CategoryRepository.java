package lescano.forohub.repository;

import lescano.forohub.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);
    @Query("""
            SELECT c FROM Category c
            LEFT JOIN c.subCategories sc
            LEFT JOIN sc.courses co
            LEFT JOIN co.topics t
            LEFT JOIN t.responses r
            LEFT JOIN r.author a
            WHERE c.id = :id
            """)
    Category findCategoryWithDetails(Long id);

}
