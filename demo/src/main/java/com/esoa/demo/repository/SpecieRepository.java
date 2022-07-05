
package com.esoa.demo.repository;


import com.esoa.demo.entity.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecieRepository extends JpaRepository<Specie,Integer> {

    boolean existsByName(String name);
    boolean existsByOrder(String order);
    boolean existsByGenus(String genus);

    @Modifying
    @Query("UPDATE Specie s SET s.deleted = false WHERE s.id = ?1")
    void enableById(Integer id);

    @Query(value = "SELECT * FROM specie s ORDER BY specie_kingdom ASC", nativeQuery = true)
    List<Specie> findAllSortedByKingdomAsc();

    @Query(value = "SELECT * FROM specie s ORDER BY specie_kingdom DESC", nativeQuery = true)
    List<Specie> findAllSortedByKingdomDesc();

    @Query(value = "SELECT * FROM specie s WHERE specie_deleted=1", nativeQuery = true)
    List<Specie> findAllDeleted();

    @Query(value = "SELECT * FROM specie s WHERE specie_deleted=1", nativeQuery = true)
    List<Specie> findAllNotDeleted();
}
