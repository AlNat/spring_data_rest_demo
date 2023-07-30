package dev.alnat.sdt.demo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Use-case test of create some users, single post and comments on it
 * Created by @author AlNat on 29.07.2023
 * Licensed by Apache License, Version 2.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Order for tests
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UseCaseTest {

    @Autowired
    private MockMvc mvc;

    @SuppressWarnings("unused")
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3-alpine3.18");

    @Test
    @Order(10)
    @SneakyThrows
    @DisplayName("1) Create users")
    void step1() {
        mvc.perform(post("/api/data/users")
                        .content("""
                                {
                                  "id": 0,
                                  "username": "test-user",
                                  "password": "somepass"
                                }
                                """)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        mvc.perform(post("/api/data/users")
                        .content("""
                                {
                                  "id": 0,
                                  "username": "test-user-2",
                                  "password": "somepass"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(20)
    @SneakyThrows
    @DisplayName("2) Retrieve users")
    void step2() {
        mvc.perform(get("/api/data/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.users[0].username").exists())
                .andExpect(jsonPath("$._embedded.users[0].password").doesNotExist())
                .andExpect(jsonPath("$._embedded.users[0].username").value("test-user"))
                .andExpect(jsonPath("$._embedded.users[1].username").exists())
                .andExpect(jsonPath("$._embedded.users[1].password").doesNotExist());

        // check full dto (with passwords)
        mvc.perform(get("/api/data/users?projection=full_user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.users[0].username").exists())
                .andExpect(jsonPath("$._embedded.users[0].password").exists())
                .andExpect(jsonPath("$._embedded.users[0].username").value("test-user"))
                .andExpect(jsonPath("$._embedded.users[1].username").exists())
                .andExpect(jsonPath("$._embedded.users[1].password").exists());
    }

    @Test
    @Order(30)
    @SneakyThrows
    @DisplayName("3) Save post of first user")
    void step3() {
        mvc.perform(post("/api/data/posts")
                        .content("""
                                {
                                 "title": "somepost",
                                 "text": "some post with text",
                                 "author": "http://localhost/api/data/users/1"
                                 }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(40)
    @SneakyThrows
    @DisplayName("4) Retrieve posts of first user")
    void step4() {
        // get preview
        mvc.perform(get("/api/data/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.posts[0].title").exists())
                .andExpect(jsonPath("$._embedded.posts[0].title").value("somepost"))
                .andExpect(jsonPath("$._embedded.posts[0].text").doesNotExist());

        // Find by author
        mvc.perform(get("/api/data/posts/search/find-by-author?author_id=1&page=0&size=20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.posts[0].title").exists())
                .andExpect(jsonPath("$._embedded.posts[0].title").value("somepost"))
                .andExpect(jsonPath("$._embedded.posts[0].text").doesNotExist());

        // get full first post
        mvc.perform(get("/api/data/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.created").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("somepost"))
                .andExpect(jsonPath("$.text").exists())
                .andExpect(jsonPath("$.text").value("some post with text"));
    }

    @Test
    @Order(50)
    @SneakyThrows
    @DisplayName("5) Save comments to first posts")
    void step5() {
        mvc.perform(post("/api/data/postComments")
                        .content("""
                                {
                                  "text": "this post is bullshit",
                                  "post": "http://localhost/api/data/post/1",
                                  "author": "http://localhost/api/data/users/2"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        mvc.perform(post("/api/data/postComments")
                        .content("""
                                {
                                "text": "yep, it is",
                                "post": "http://localhost/api/data/post/1",
                                "author": "http://localhost/api/data/users/1"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(60)
    @SneakyThrows
    @DisplayName("6) Retrieve post with comments")
    void step6() {
        // get preview
        mvc.perform(get("/api/data/postComments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.postComments[0].created").exists())
                .andExpect(jsonPath("$._embedded.postComments[0].text").exists())
                .andExpect(jsonPath("$._embedded.postComments[0].text").value("this post is bullshit"))
                .andExpect(jsonPath("$._embedded.postComments[1].created").exists())
                .andExpect(jsonPath("$._embedded.postComments[1].text").exists())
                .andExpect(jsonPath("$._embedded.postComments[1].text").value("yep, it is"));


        // Find by post
        mvc.perform(get("/api/data/postComments/search/find-by-post?post_id=1&page=0&size=20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.postComments[0].created").exists())
                .andExpect(jsonPath("$._embedded.postComments[0].text").exists())
                .andExpect(jsonPath("$._embedded.postComments[0].text").value("this post is bullshit"))
                .andExpect(jsonPath("$._embedded.postComments[1].created").exists())
                .andExpect(jsonPath("$._embedded.postComments[1].text").exists())
                .andExpect(jsonPath("$._embedded.postComments[1].text").value("yep, it is"));
    }

}
