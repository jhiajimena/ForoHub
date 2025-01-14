package lescano.forohub.repository;

import lescano.forohub.entities.Permission;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    boolean existsByName(String name);
    Page<Permission> findAll (Pageable pageable);

    @Query("""
            SELECT p FROM Permission p
            JOIN ProfilePermission pp ON p.id= pp.permission.id
            WHERE pp.profile.id = :id
            """)
    Page<Permission> findPermissionsByProfileId(@Valid Long id, Pageable pageable);

    Optional<Permission> findByName(String name);

}
