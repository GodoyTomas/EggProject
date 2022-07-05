package com.esoa.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "valoration")
@Getter
@Setter
@NoArgsConstructor
public class Valoration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private User user;
    @Column(name = "valoration_score", nullable = true)
    private Integer score;
    @Column(name = "valoration_date", nullable = true)
    private LocalDateTime valorationDate;
}
