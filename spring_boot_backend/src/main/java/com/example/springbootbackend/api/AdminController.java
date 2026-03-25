package com.example.springbootbackend.api;

import com.example.springbootbackend.domain.User;
import com.example.springbootbackend.repo.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Users")
public class AdminController {

    private final UserRepository users;

    public AdminController(UserRepository users) {
        this.users = users;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/users")
    @Operation(summary = "List users (admin)", description = "Lists all users. Requires ROLE_ADMIN.")
    public List<User> listUsers() {
        return users.findAll();
    }
}
