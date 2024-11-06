package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Nouvelle méthode pour la connexion
    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@RequestParam String username, @RequestParam String password) {
        User authenticatedUser = userService.authenticateUser(username, password);

        if (authenticatedUser != null) {
            // Créez un objet de réponse avec l'ID de l'utilisateur et/ou un token
            LoginResponse loginResponse = new LoginResponse(authenticatedUser.getId(), authenticatedUser.getUsername());
            return ResponseEntity.ok(loginResponse);  // Vous pouvez également ajouter un token ici
        } else {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

    // Classe de réponse de connexion
    public static class LoginResponse {
        private Integer userId;
        private String username;

        public LoginResponse(Integer userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
