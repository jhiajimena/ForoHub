package lescano.forohub.repository;

import lescano.forohub.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    boolean existsByName(String name);
    Page<SubCategory> findAll(Pageable pageable);
    @Query(value = """
            SELECT s FROM SubCategory s WHERE s.category.id =:id
            """)
    Page<SubCategory> findSubCategoriesByCategoryId(Long id, Pageable pageable);

}
