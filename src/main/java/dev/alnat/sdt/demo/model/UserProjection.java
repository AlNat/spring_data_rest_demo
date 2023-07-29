package dev.alnat.sdt.demo.model;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

/**
 * Projection for password showing
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
@Projection(name = "preview_user", types = { User.class })
public interface UserProjection {
    Integer getId();
    LocalDateTime getCreated();
    String getUsername();
}
