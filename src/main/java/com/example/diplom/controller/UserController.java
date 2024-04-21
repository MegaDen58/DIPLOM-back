package com.example.diplom.controller;

import com.example.diplom.dto.UserDto;
import com.example.diplom.model.User;
import com.example.diplom.repository.UserRepository;
import com.example.diplom.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.diplom.dto.FavouriteRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        User newUser = userService.registerUser(userDto);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserDto userDto) {
        User user = userService.loginUser(userDto);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/addFavorite")
    public User addFavorite(@RequestBody FavouriteRequest request) {
        return userService.addFavorite(request);
    }
    @PostMapping("/removeFavorite")
    public User removeFavorite(@RequestBody FavouriteRequest request) {
        return userService.removeFavorite(request);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}