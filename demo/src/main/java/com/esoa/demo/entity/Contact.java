package com.esoa.demo.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "contact")
@SQLDelete(sql = "UPDATE contact SET deleted = true WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "discharge_date")
    private LocalDate dischargeDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(name = "contact_description", nullable = false, columnDefinition = "longtext")
    private String description;
    @Column(name = "contact_deleted", nullable = false)
    private boolean deleted;
    @Column(name = "post_title", nullable = false)
    private String title;
}
