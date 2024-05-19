package com.example.diplom.repository;

import com.example.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query("SELECT r.name FROM Role r JOIN UserRole ur ON r.id = ur.role.id JOIN User u ON u.id = ur.user.id WHERE u.id = :userId")
    List<String> findRoleNameByUserId(@Param("userId") Long userId);
}