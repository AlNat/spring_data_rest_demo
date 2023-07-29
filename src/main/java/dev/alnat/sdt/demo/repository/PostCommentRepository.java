package dev.alnat.sdt.demo.repository;

import dev.alnat.sdt.demo.model.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {

    @RestResource(path = "find-by-author")
    Page<PostComment> findByAuthor_Id(@Param("author_id") Integer authorId, Pageable pageable);

    @RestResource(path = "find-by-post")
    Page<PostComment> findByPost_Id(@Param("post_id") Integer postId, Pageable pageable);

}
