package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
