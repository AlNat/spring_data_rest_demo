package dev.alnat.sdt.demo.repository;

import dev.alnat.sdt.demo.model.BasicPostDTO;
import dev.alnat.sdt.demo.model.Post;
import dev.alnat.sdt.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
@RepositoryRestResource(excerptProjection = BasicPostDTO.class)
public interface PostRepository extends CrudRepository<Post, Integer> {

    @RestResource(path = "find-by-author")
    Page<Post> findByAuthor_Id(@Param("author_id") Integer authorId, Pageable pageable);

}
