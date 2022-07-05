package com.esoa.demo.entity;

import com.esoa.demo.enumeration.Category;
import java.time.LocalDate;
import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;


@Entity
@Table(name = "animal", indexes = {@Index(name = "idx_name", columnList = "animal_name")})
@SQLDelete(sql = "UPDATE animal SET animal_deleted = true WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "animal_name", nullable = false)
    private String name;
    @Column(name = "animal_scientific_name", nullable = false)
    private String scientificName;
    @Column(name = "animal_description", nullable = false, columnDefinition = "longtext")
    private String description;
    @Column(name = "animal_discharge_date", nullable = false)
    private LocalDate dischargeDate;
    @Enumerated(STRING)
    @Column(name = "animal_category", nullable = false)
    private Category category;
    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "specie", referencedColumnName = "id", nullable = false)
    private Specie specie;
    @Column(name = "animal_image")
    private String image;
    @Column(name = "animal_deleted", nullable = false)
    private boolean deleted;
    
    
}
