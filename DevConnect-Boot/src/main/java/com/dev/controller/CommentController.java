package com.dev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.Comment;
import com.dev.serviceImpl.CommentServiceImpl;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private final CommentServiceImpl commentServiceImpl;

	public CommentController(CommentServiceImpl commentServ) {
		this.commentServiceImpl = commentServ;
	}

	@PostMapping("/add")
	public ResponseEntity<?> addComment(@RequestBody Comment comment) {
		try {
			System.out.println("Cooment " + comment);
			Comment savedComment = commentServiceImpl.addCommet(comment);
			if (savedComment != null)
				return new ResponseEntity<>(savedComment, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Not saved", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/update/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable long commentId, @RequestBody Comment comment) {
		try {
			Comment updatedComment = commentServiceImpl.updateComment(commentId, comment);
			if (updatedComment != null)
				return new ResponseEntity<>(updatedComment, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Not Updated ", HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable long commentId) {
		try {
			System.out.println("Comment id for delting " + commentId);
			boolean updatedComment = commentServiceImpl.deleteComment(commentId);
			if (updatedComment)
				return new ResponseEntity<>(updatedComment, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Not Deleted ", HttpStatus.NO_CONTENT);
	}

	@GetMapping("post/{postId}")
	public ResponseEntity<?> getAllCommentOfPost(@PathVariable long postId) {
		try {
			System.out.println("1 All comment for post " + postId);
			List<Comment> allComment = commentServiceImpl.getAllCommentFromPostId(postId);
			System.out.println("2 All comment for post " + postId);
			System.out.println(3 + " " + allComment);
			if (allComment != null)
				return new ResponseEntity<>(allComment, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

}
