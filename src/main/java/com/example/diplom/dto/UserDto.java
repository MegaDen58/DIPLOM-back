package com.example.diplom.dto;

import com.example.diplom.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String email;
    private List<Integer> favourites;
    private Integer balance;
    private Set<Role> roles;


    public UserDto(String login, String password){
        this.login = login;
        this.password = password;
    }
    public UserDto(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
    }
    public UserDto(Long id, String login, String password, String email, List<Integer> favourites, Integer balance, Set<Role> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.favourites = favourites;
        this.balance = balance;
        this.roles = roles;
    }
}
