package com.esoa.demo.repository;

import com.esoa.demo.entity.Valoration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorationRepository extends JpaRepository<Valoration,Integer> {

}
