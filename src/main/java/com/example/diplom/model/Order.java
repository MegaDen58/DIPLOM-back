package com.example.diplom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date start_date;
    private Date end_date;
    private Integer price;

    @Column(name = "user_id")
    private Integer user_id;

    private String type;

    @ElementCollection
    @Column(name="items")
    private List<Integer> items;
}
