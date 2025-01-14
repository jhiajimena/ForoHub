package lescano.forohub.repository;

import lescano.forohub.entities.Permission;
import lescano.forohub.entities.Profile;
import lescano.forohub.entities.ProfilePermission;
import lescano.forohub.entities.ProfilePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePermissionRepository extends JpaRepository<ProfilePermission, ProfilePermissionId> {


    boolean existsByProfileAndPermission(Profile profile, Permission permission);
}
