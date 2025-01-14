package lescano.forohub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="profile_permission")
@IdClass(ProfilePermissionId.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePermission {
    @Id
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="profile_id")
    private Profile profile;

    @Id
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="permission_id")
    private Permission permission;
}
