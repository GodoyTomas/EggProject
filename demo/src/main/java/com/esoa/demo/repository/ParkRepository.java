package com.esoa.demo.repository;

import com.esoa.demo.entity.Park;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkRepository extends JpaRepository<Park,Integer> {


    boolean existsByNameAndDescription(String name, String description);

    @Modifying
    @Query("UPDATE Park s SET s.deleted = false WHERE s.id = ?1")
    void enableById(Integer id);

    @Query(value = "SELECT * FROM park s ORDER BY park_name ASC", nativeQuery = true)
    List<Park> findAllSortedByParkNameAsc();

    @Query(value = "SELECT * FROM park s ORDER BY park_name DESC", nativeQuery = true)
    List<Park> findAllSortedByParkNameDesc();

    @Query(value = "SELECT * FROM park s WHERE park_deleted=1", nativeQuery = true)
    List<Park> findAllDeleted();

    @Query(value = "SELECT * FROM park s WHERE park_deleted=1", nativeQuery = true)
    List<Park> findAllNotDeleted();

}
