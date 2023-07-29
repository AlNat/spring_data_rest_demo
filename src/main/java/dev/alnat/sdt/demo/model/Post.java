package dev.alnat.sdt.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Table(name = "post", schema = "social")
public class Post implements Serializable, Comparable<Post> {

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
    private String title;

    @ToString.Exclude
    @Column(updatable = false, nullable = false)
    private String text;

    @ToString.Include
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private User author;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    @Fetch(FetchMode.SELECT)
    private List<PostComment> commentList;


    @Override
    public int compareTo(Post o) {
        return o.getId().compareTo(this.id);
    }
}
