package lescano.forohub.repository;

import lescano.forohub.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Category,Long> {

    @Query("SELECT COUNT(c) FROM Category c")
    long countCategories();

    @Query("SELECT COUNT(s) FROM SubCategory s")
    long countSubCategories();

    @Query("SELECT COUNT(co) FROM Course co")
    long countCourses();

    @Query("SELECT COUNT(t) FROM Topic t")
    long countTopics();

    @Query("SELECT COUNT(r) FROM Response r")
    long countResponses();
}
