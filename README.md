# spring-data-rest-demo

Tiny project as demo of spring data rest usage for test spring data rest to prototyping microservices

As domain model is the simplest social network:
user, post, post comment

## ER


+---------------+                +---------------+
|               |                |               |
|               |                |     Post      |
|     User      +--------------->|               |
|               |                |               |
+-----+---------+                +-------+-------+
        |                           |
        |                           |
        |                           |
        |                           |
        |                           |
        |                           v
        |                +----------------+
        |                |                |
        +--------------->|  PostComments  |
                         |                |
                         +----------------+

## Usage

For usage see `api.http` file in repo

Usage of projection as DTO is limitation of Spring Data Rest, but exposing entities bth isn't a good choice due coupling and data leak.
To avoid password leak it available only by specific projection


## Stack:

* Spring + Spring Data JPA
* Spring Data Rest
* Postgres, Hibernate, Flyway
