package lescano.forohub.config;


import lescano.forohub.entities.ForumUser;
import lescano.forohub.entities.Permission;
import lescano.forohub.entities.Profile;
import lescano.forohub.entities.ProfilePermission;
import lescano.forohub.repository.PermissionRepository;
import lescano.forohub.repository.ProfilePermissionRepository;
import lescano.forohub.repository.ProfileRepository;
import lescano.forohub.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            ProfileRepository profileRepository,
            PermissionRepository permissionRepository,
            ProfilePermissionRepository profilePermissionRepository,
            UserRepository userRepository) {
        return args -> {
            //  Permission
            // createPermissions
            if (permissionRepository.count() > 0 && profileRepository.count() > 0) {
                log.info("Data already initialized, skipping...");
                return;
            }
            Permission createTopic = createPermissionIfNotExists(permissionRepository, "CREATE_TOPIC",
                    "Allows creating new topics in the forum");

            Permission createResponse = createPermissionIfNotExists(permissionRepository, "CREATE_RESPONSE",
                    "Allows posting comments in topics");

            Permission viewTopic = createPermissionIfNotExists(permissionRepository, "VIEW_TOPIC",
                    "Allows viewing published topics");

            Permission viewResponses = createPermissionIfNotExists(permissionRepository, "VIEW_RESPONSES",
                    "Allows viewing comments in topics");

            Permission editOwnTopic = createPermissionIfNotExists(permissionRepository, "EDIT_OWN_TOPIC",
                    "Allows editing their own topics");

            Permission editOwnResponse = createPermissionIfNotExists(permissionRepository, "EDIT_OWN_COMMENT",
                    "Allows editing their own comments");

            Permission editAnyTopic = createPermissionIfNotExists(permissionRepository, "EDIT_ANY_TOPIC",
                    "Allows editing topics created by any user");

            Permission deleteAnyTopic = createPermissionIfNotExists(permissionRepository, "DELETE_ANY_TOPIC",
                    "Allows deleting topics created by any user");

            Permission editAnyResponse = createPermissionIfNotExists(permissionRepository, "EDIT_ANY_COMMENT",
                    "Allows editing comments created by any user");

            Permission deleteAnyResponse = createPermissionIfNotExists(permissionRepository, "DELETE_ANY_RESPONSE",
                    "Allows deleting comments created by any user");

            Permission viewReports = createPermissionIfNotExists(permissionRepository, "VIEW_REPORTS",
                    "Allows viewing reported content");

            Permission editUser = createPermissionIfNotExists(permissionRepository, "EDIT_USER",
                    "Allows editing user profiles");

            Permission deleteUser = createPermissionIfNotExists(permissionRepository, "DELETE_USER",
                    "Allows deleting users");

            Permission assignRole = createPermissionIfNotExists(permissionRepository, "ASSIGN_ROLE",
                    "Allows assigning roles or permissions to users");

            Permission createCategory = createPermissionIfNotExists(permissionRepository, "CREATE_CATEGORY",
                    "Allows creating new categories or sections in the forum");

            Permission editCategory = createPermissionIfNotExists(permissionRepository, "EDIT_CATEGORY",
                    "Allows editing existing categories");

            Permission deleteCategory = createPermissionIfNotExists(permissionRepository, "DELETE_CATEGORY",
                    "Allows deleting categories");

            Permission createSubCategory = createPermissionIfNotExists(permissionRepository, "CREATE_SUB_CATEGORY",
                    "Allows creating new subCategories or sections in the forum");

            Permission editSubCategory = createPermissionIfNotExists(permissionRepository, "EDIT_SUBCATEGORY",
                    "Allows editing existing subCategories");

            Permission deleteSubCategory = createPermissionIfNotExists(permissionRepository, "DELETE_SUBCATEGORY",
                    "Allows deleting subCategories");

            Permission createCourse = createPermissionIfNotExists(permissionRepository, "CREATE_COURSE",
                    "Allows creating new courses or sections in the forum");

            Permission editCourse = createPermissionIfNotExists(permissionRepository, "EDIT_COURSE",
                    "Allows editing existing courses");

            Permission deleteCourse = createPermissionIfNotExists(permissionRepository, "DELETE_COURSE",
                    "Allows deleting courses");


            // ROLES
            // Profiles
            Profile developerProfile = createProfileIfNotExists(profileRepository, "DEVELOPER",
                    "Full access to the system");


            Profile adminProfile = createProfileIfNotExists(profileRepository, "ADMIN",
                    "Full administrative access to manage users, roles, content, and forum settings");

            Profile moderatorProfile = createProfileIfNotExists(profileRepository, "MODERATOR",
                    "Access to moderate content");

            Profile userProfile = createProfileIfNotExists(profileRepository, "USER",
                    "Basic access to interact with the platform");


            // Create admin user if not exists
            if (!userRepository.existsByEmail("root@gmail.com")) {
                ForumUser createDevelop = ForumUser.builder()
                        .name("DEVELOPER")
                        .email("root@gmail.com")
                        .password("$2y$10$G1wRm0G9vK0sD5rbLr9K4X7h5uK0jT7gzX0k5K1PkeOB6Wq8H7K7G")//root1234
                        .profile(developerProfile)
                        .build();
                userRepository.save(createDevelop);
            }

            if (!userRepository.existsByEmail("admin@gmail.com")) {
                ForumUser createAdmin = ForumUser.builder()
                        .name("ADMIN")
                        .email("admin@gmail.com")
                        .password("$2y$10$yqNVNV68A/uDr/TdORs1C.ZsjX6gMg/AKfJ0pDi7ppVtXd8T.kMX.")//admin1234
                        .profile(adminProfile)
                        .build();
                userRepository.save(createAdmin);
            }


            // Assign permissions only if they don't exist
            if (profilePermissionRepository.count() == 0) {

                //developer permission
                profilePermissionRepository.saveAll(assignPermissions(developerProfile,
                        createTopic, editOwnTopic, createResponse, viewTopic, viewResponses, editOwnResponse, editAnyTopic,
                        deleteAnyTopic, deleteAnyResponse, editAnyResponse, viewReports, editUser, deleteUser,
                        assignRole, createCategory, editCategory, deleteCategory,
                        createSubCategory, editSubCategory, deleteSubCategory, createCourse, editCourse, deleteCourse));

                // Admin permissions
                profilePermissionRepository.saveAll(assignPermissions(adminProfile,
                        createTopic, editOwnTopic, createResponse, viewTopic, viewResponses, editOwnResponse, editAnyTopic,
                        deleteAnyTopic, deleteAnyResponse, editAnyResponse, viewReports,
                        createCategory, editCategory, deleteCategory,
                        createSubCategory, editSubCategory, deleteSubCategory, createCourse, editCourse, deleteCourse));

                // Moderator permissions
                profilePermissionRepository.saveAll(assignPermissions(moderatorProfile, createTopic, createResponse,
                        viewTopic, viewResponses, editOwnTopic, editOwnResponse, editAnyTopic, deleteAnyTopic,
                        deleteAnyResponse, editAnyResponse));

                // User permissions
                profilePermissionRepository.saveAll(assignPermissions(userProfile, createTopic, createResponse,
                        viewTopic, viewResponses, editOwnTopic, editOwnResponse));
            }
        };

    }

    private Permission createPermissionIfNotExists(PermissionRepository repository, String name, String description) {
        return repository.findByName(name)
                .orElseGet(() -> repository.save(Permission.builder()
                        .name(name)
                        .description(description)
                        .build()));
    }

    private Profile createProfileIfNotExists(ProfileRepository repository, String name, String description) {
        return repository.findByName(name)
                .orElseGet(() -> repository.save(Profile.builder()
                        .name(name)
                        .description(description)
                        .build()));
    }

    private List<ProfilePermission> assignPermissions(Profile profile, Permission... permissions) {
        return Arrays.stream(permissions)
                .map(permission -> new ProfilePermission(profile, permission))
                .toList();
    }

}
