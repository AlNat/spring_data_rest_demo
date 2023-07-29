package dev.alnat.sdt.demo.model;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Simplified view of Post entity
 *
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
@Projection(name = "fullPost", types = Post.class)
public interface FullPostDTO extends BasicPostDTO {
    String getText();
    List<PostComment> getCommentList();
}
