package com.example.AuctionSite.configuration;

import com.example.AuctionSite.entity.Permission;
import com.example.AuctionSite.entity.Role;
import com.example.AuctionSite.entity.User;
import com.example.AuctionSite.repository.PermissionRepository;
import com.example.AuctionSite.repository.RoleRepository;
import com.example.AuctionSite.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            createPermissionIfNotExists("GET_ALL_USERS","Get all users permission");
            createPermissionIfNotExists("GET_USER_BY_ID","Get user by id permission");
            createPermissionIfNotExists("GET_USER_BY_USERNAME","Get user by username permission");
            createPermissionIfNotExists("UPDATE_USER","Update user permission");
            createPermissionIfNotExists("DELETE_USER","Delete user permission");
            
            
            
            createRoleIfNotExists("ADMIN", "ADMIN role");
            createRoleIfNotExists("USER", "USER role");
            if (userRepository.findByUsername("admin").isEmpty()) {
                Set<Role> role = new HashSet<>();
                role.add(roleRepository.findById("ADMIN").get());
                User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(role)
                    .build();
                userRepository.save(user);
                log.warn("admin user has been created with default username & password: admin");
            }
        };
    }
    
    private void createRoleIfNotExists(String roleName, String description) {
        roleRepository.findById(roleName).ifPresentOrElse(
            role -> {},
            () -> {
                Role role = Role.builder()
                    .name(roleName)
                    .description(description)
                    .build();
                if ("ADMIN".equals(roleName)) {
                    HashSet<Permission> permissions = new HashSet<>(permissionRepository.findAll());
                    role.setPermissions(permissions);
                }
                roleRepository.save(role);
            }
        );
    }
    
    private void createPermissionIfNotExists(String permissionName, String description) {
        permissionRepository.findById(permissionName).ifPresentOrElse(
            permission -> {},
            () -> {
                Permission permission=Permission.builder()
                    .name(permissionName)
                    .description(description)
                    .build();
                permissionRepository.save(permission);
            }
        );
    }
}
