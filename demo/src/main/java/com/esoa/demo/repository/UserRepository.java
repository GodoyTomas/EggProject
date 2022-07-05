package com.esoa.demo.repository;

import com.esoa.demo.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.deleted = false WHERE u.id = ?1")
    void enableById(Integer id);
}
