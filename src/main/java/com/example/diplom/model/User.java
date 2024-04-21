package com.example.diplom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(unique = true, name="email")
    private String email;

    @ElementCollection
    @Column(name="favourites")
    private List<Integer> favourites;
}