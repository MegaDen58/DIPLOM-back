package com.example.diplom.service;

import com.example.diplom.dto.FavouriteRequest;
import com.example.diplom.dto.UserDto;
import com.example.diplom.model.Role;
import com.example.diplom.model.User;
import com.example.diplom.repository.RoleRepository;
import com.example.diplom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        Role userRole = roleRepository.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        return userRepository.save(user);
    }
    public void setUserBalance(Long userId, Integer balance) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBalance(balance);
        userRepository.save(user);
    }

    public User addFavourite(FavouriteRequest favourite) {
        User user = userRepository.findById(favourite.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> favourites = user.getFavourites();
        if (!favourites.contains(favourite.getProductId())) {
            favourites.add(favourite.getProductId());
            user.setFavourites(favourites);
            return userRepository.save(user);
        } else {
            return user;
        }
    }

    public User removeFavourite(FavouriteRequest favorite) {
        User user = userRepository.findById(favorite.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        List<Integer> favorites = user.getFavourites();
        if (favorites.contains(favorite.getProductId())) {
            favorites.remove(favorite.getProductId());
            user.setFavourites(favorites);
            return userRepository.save(user);
        } else {
            return user;
        }
    }

    public User loginUser(UserDto userDto) {
        User user = userRepository.findByLogin(userDto.getLogin());
        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            return user;
        }
        return null;
    }
}
