package lescano.forohub.entities;

import lescano.forohub.dto.profile.CreateProfileDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="profile")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER)
    private Set<ProfilePermission> profilePermissions = new HashSet<>();

    public Set<Permission> getPermissions() {
        Set<Permission> permissions = new HashSet<>();
        for (ProfilePermission profilePermission : profilePermissions) {
            permissions.add(profilePermission.getPermission());
        }
        return permissions;
    }

    public void updateProfile (CreateProfileDTO profileDTO){
        if(profileDTO.name() != null){
            this.name=profileDTO.name();
        }
        if(profileDTO.description() != null){
            this.description=profileDTO.description();
        }

    }
}
