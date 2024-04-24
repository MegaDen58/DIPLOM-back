package com.example.diplom.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Column(name="balance")
    private int balance;

    @ElementCollection
    @Column(name="favourites")
    private List<Integer> favourites;
}