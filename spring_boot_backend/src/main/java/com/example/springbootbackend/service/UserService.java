package com.example.springbootbackend.service;

import com.example.springbootbackend.domain.Role;
import com.example.springbootbackend.domain.User;
import com.example.springbootbackend.repo.RoleRepository;
import com.example.springbootbackend.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * User/role management service.
 */
@Service
public class UserService {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_USER = "ROLE_USER";

    private final UserRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository users, RoleRepository roles, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.roles = roles;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    void ensureDefaults() {
        Role admin = roles.findByName(ROLE_ADMIN).orElseGet(() -> roles.save(new Role(ROLE_ADMIN)));
        Role manager = roles.findByName(ROLE_MANAGER).orElseGet(() -> roles.save(new Role(ROLE_MANAGER)));
        Role user = roles.findByName(ROLE_USER).orElseGet(() -> roles.save(new Role(ROLE_USER)));

        users.findByUsername("admin").orElseGet(() -> {
            User u = new User("admin", passwordEncoder.encode("admin123"));
            u.getRoles().addAll(Set.of(admin, manager, user));
            return users.save(u);
        });
        users.findByUsername("manager").orElseGet(() -> {
            User u = new User("manager", passwordEncoder.encode("manager123"));
            u.getRoles().addAll(Set.of(manager, user));
            return users.save(u);
        });
        users.findByUsername("user").orElseGet(() -> {
            User u = new User("user", passwordEncoder.encode("user123"));
            u.getRoles().add(user);
            return users.save(u);
        });
    }

    /**
     * PUBLIC_INTERFACE
     * Returns the User or throws if missing.
     */
    @Transactional(readOnly = true)
    public User getByUsernameOrThrow(String username) {
        return users.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User not found: " + username));
    }

    /**
     * PUBLIC_INTERFACE
     * Verifies password and returns roles if successful.
     */
    @Transactional(readOnly = true)
    public Set<String> authenticateAndGetRoles(String username, String rawPassword) {
        User user = getByUsernameOrThrow(username);
        if (!user.isEnabled()) {
            throw new IllegalArgumentException("User is disabled");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }
}
