package lescano.forohub.repository;

import lescano.forohub.entities.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    boolean existsByName(String name);
    Page<Profile> findAll(Pageable pageable);

    Optional<Profile> findByName(String name);
}
