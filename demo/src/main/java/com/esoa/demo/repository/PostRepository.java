package com.esoa.demo.repository;

import com.esoa.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Query("UPDATE Post s SET s.deleted = false WHERE s.id = ?1")
    void enableById(Integer id);

    @Query(value = "SELECT * FROM post p WHERE post_deleted=1", nativeQuery = true)
    List<Post> findAllDeleted();

    @Query(value = "SELECT * FROM post p WHERE post_deleted=0", nativeQuery = true)
    List<Post> findAllNotDeleted();
}
