package com.esoa.demo.entity;

import com.esoa.demo.enumeration.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "user", indexes = {@Index(name = "idx_user_mail", columnList = "user_email")})
@SQLDelete(sql = "UPDATE user SET user_deleted = true WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_email",unique = true, nullable = false)
    private String email;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(name = "user_lastname", nullable = false)
    private String lastName;
    @Column(name = "user_password", nullable = false)
    private String password;
    @Column(name = "user_role", nullable = false)
    private Role role;
    @Column(name = "user_deleted", nullable = false)
    private boolean deleted;
}
