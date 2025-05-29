package com.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(value = "select * from post where user_user_id = :userId", nativeQuery = true)
	List<Post> findAllPostByUserId(@Param("userId") long userId);

}
