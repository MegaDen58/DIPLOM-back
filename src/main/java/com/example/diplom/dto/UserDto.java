package com.example.diplom.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String email;

    public UserDto(String login, String password){
        this.login = login;
        this.password = password;
    }
}
