
package com.esoa.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "specie")
@SQLDelete(sql = "UPDATE specie SET specie_deleted = true WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "specie_name", nullable = false)
    private String name;
    @Column(name = "specie_kingdom",nullable = false)
    private String kingdom;
    @Column(name = "specie_phylum",nullable = false)
    private String phylum;
    @Column(name = "specie_order", nullable = false)
    private String order;
    @Column(name = "specie_family", nullable = false)
    private String family;
    @Column(name = "specie_genus", nullable = false)
    private String genus;
    @Column(name = "specie_deleted")
    private boolean deleted;
}
