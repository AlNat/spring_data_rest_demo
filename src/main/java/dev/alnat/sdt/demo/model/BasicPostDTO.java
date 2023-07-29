package dev.alnat.sdt.demo.model;

import dev.alnat.sdt.demo.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

/**
 * Simplified view of Post entity
 *
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
@Projection(name = "basicPost", types = Post.class)
public interface BasicPostDTO {
    String getTitle();
    LocalDateTime getCreated();
    @Value("#{target.author.username}")
    String authorName();
}
