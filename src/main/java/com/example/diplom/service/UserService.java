package com.example.diplom.service;

import com.example.diplom.dto.FavouriteRequest;
import com.example.diplom.dto.UserDto;
import com.example.diplom.model.User;
import com.example.diplom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User addFavorite(FavouriteRequest favourite) {
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

    public User removeFavorite(FavouriteRequest favorite) {
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
