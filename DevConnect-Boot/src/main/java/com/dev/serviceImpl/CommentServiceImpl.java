package com.dev.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.constant.Role;
import com.dev.entities.Comment;
import com.dev.entities.Post;
import com.dev.entities.User;
import com.dev.repository.CommentRepository;
import com.dev.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	private final UserServiceImpl userServiceImpl;

	private final PostServiceImpl postServiceImpl;

	public CommentServiceImpl(CommentRepository commentRepo, UserServiceImpl userServ, PostServiceImpl postServ) {
		this.commentRepository = commentRepo;
		this.userServiceImpl = userServ;
		this.postServiceImpl = postServ;
	}

	@Override
	public Comment addCommet(Comment comment) {
		User user = userServiceImpl.getUserByUserName(comment.getUser().getUsername());
		if (user == null) {
			return null;
		}
		Post post = postServiceImpl.getPostById(comment.getPost().getPostId());
		if (post == null) {
			return null;
		}
		comment.setUser(user);
		comment.setPost(post);
		comment.setCommentTime(LocalDateTime.now());
		return commentRepository.save(comment);
	}

	@Override
	public Comment updateComment(long commentId, Comment comment) {
		Comment exsitingComment = getCommentById(commentId);
		if (exsitingComment == null) {
			return null;
		}
		User user = userServiceImpl.getUserByUserName(comment.getUser().getUsername());
		if (user == null) {
			return null;
		}
		Post post = postServiceImpl.getPostById(comment.getPost().getPostId());
		if (post == null) {
			return null;
		}
		exsitingComment.setCommentTime(LocalDateTime.now());
		exsitingComment.setContent(comment.getContent());
		return commentRepository.save(exsitingComment);
	}

	@Override
	public Comment getCommentById(long commentId) {
		Optional<Comment> optionalComment = commentRepository.findById(commentId);
		if (optionalComment.isPresent()) {
			return optionalComment.get();
		}
		return null;
	}

	@Transactional
	@Override
	public boolean deleteComment(long commentId) {
		Comment comment = getCommentById(commentId);
		if (comment != null) {
			Post post = comment.getPost();
			if (post != null) {
				post.getComments().remove(comment);
			}
			commentRepository.delete(comment);
			return true;
		}
		return false;
	}

	@Override
	public List<Comment> getAllCommentFromPostId(long postId) {
		List<Comment> allComment = commentRepository.findAllCommentByPost(postId);
		return allComment;
	}

}
