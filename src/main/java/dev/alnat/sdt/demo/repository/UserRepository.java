package dev.alnat.sdt.demo.repository;

import dev.alnat.sdt.demo.model.User;
import dev.alnat.sdt.demo.model.UserProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface UserRepository extends CrudRepository<User, Integer> {

}
