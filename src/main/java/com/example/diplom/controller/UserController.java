package com.example.diplom.controller;

import com.example.diplom.dto.BalanceUpdateRequest;
import com.example.diplom.dto.UserBalance;
import com.example.diplom.dto.UserDto;
import com.example.diplom.model.User;
import com.example.diplom.model.UserRole;
import com.example.diplom.repository.UserRepository;
import com.example.diplom.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.diplom.dto.FavouriteRequest;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/addFavourite")
    public User addFavorite(@RequestBody FavouriteRequest request) {
        return userService.addFavourite(request);
    }
    @PostMapping("/removeFavourite")
    public User removeFavorite(@RequestBody FavouriteRequest request) {
        return userService.removeFavourite(request);
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getFavourites(),
                        user.getBalance(),
                        userRepository.findRoleNameByUserId(user.getId())
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFavourites(),
                    user.getBalance(),
                    userRepository.findRoleNameByUserId(userId)
            );
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/updateBalance")
    public ResponseEntity<User> updateUserBalance(@PathVariable Long userId, @RequestBody BalanceUpdateRequest request) {
        userService.updateUserBalance(userId, request.getAmountToAdd());
        return ResponseEntity.ok(userRepository.findById(userId).orElse(null));
    }

    @PostMapping("/setBalance")
    public ResponseEntity<User> setUserBalance(@RequestBody UserBalance request) {
        userService.setUserBalance(request.getUserId(), request.getBalance());
        return ResponseEntity.ok(userRepository.findById(request.getUserId()).orElse(null));
    }
    @GetMapping("/{userId}/role")
    public List<String> getUserRole(@PathVariable Long userId) {
        return userRepository.findRoleNameByUserId(userId);
    }
}
