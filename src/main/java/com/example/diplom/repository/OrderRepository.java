package com.example.diplom.repository;

import com.example.diplom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o.items FROM Order o WHERE o.user_id = :userId")
    List<Integer> findItemsByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE o.user_id = :userId")
    List<Order> findByUser_id(Long userId);
}
