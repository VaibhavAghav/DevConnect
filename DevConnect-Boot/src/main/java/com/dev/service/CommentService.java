package com.dev.service;

import java.util.List;

import com.dev.entities.Comment;

public interface CommentService {

	public Comment addCommet(Comment comment);

	public Comment updateComment(long commentId, Comment comment);

	public Comment getCommentById(long commentId);

	public boolean deleteComment(long commentId);
	
	public List<Comment> getAllCommentFromPostId(long postId);

}
