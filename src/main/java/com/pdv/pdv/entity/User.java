package com.pdv.pdv.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 30, nullable = false, unique = true)
    private String username;
    @Column(length = 60, nullable = false)
    private String password;
    private boolean isEnabled;

    @OneToMany(mappedBy = "user")
    private List<Sale> sales;

}
