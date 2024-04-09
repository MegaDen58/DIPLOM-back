package com.example.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String email;

    public UserDto(String login, String password){
        this.login = login;
        this.password = password;
    }
    public UserDto(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
