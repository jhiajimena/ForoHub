package lescano.forohub.repository;

import lescano.forohub.entities.ForumUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ForumUser,Long> {
    boolean existsByEmail(String email);
    Page<ForumUser> findAll(Pageable pageable);

    @Query("""
            SELECT u FROM ForumUser u
            LEFT JOIN FETCH Profile p ON u.profile.id = p.id
            LEFT JOIN FETCH ProfilePermission pp ON pp.profile.id = p.id
            WHERE u.id = :id
            """)
    ForumUser findUserProfileAndPermissions(Long id);

    Optional<ForumUser> findByEmail(String email);
}
