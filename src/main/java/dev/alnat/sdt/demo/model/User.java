package dev.alnat.sdt.demo.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "user", schema = "social")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @CreationTimestamp
    @ToString.Include
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;

    @ToString.Include
    @Column(updatable = false, nullable = false)
    private String username;

    @ToString.Exclude
    @Column(updatable = false, nullable = false)
    private String password;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @Fetch(FetchMode.SELECT)
    private List<Post> postList;

}
