package com.example.diplom.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
