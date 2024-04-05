package com.example.diplom.service;

import com.example.diplom.dto.UserDto;
import com.example.diplom.model.User;
import com.example.diplom.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    public User loginUser(UserDto userDto) {
        User user = userRepository.findByLogin(userDto.getLogin());
        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            return user;
        }
        return null;
    }
}
