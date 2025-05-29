package com.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "select * from comment where post_post_id = :postId", nativeQuery = true)
	List<Comment> findAllCommentByPost(@Param("postId") long postId);

}
