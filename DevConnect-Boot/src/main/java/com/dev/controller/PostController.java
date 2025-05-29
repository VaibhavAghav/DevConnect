package com.dev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.Post;
import com.dev.exception.PostImageUploadException;
import com.dev.exception.UserNotFoundException;
import com.dev.repository.PostRepository;
import com.dev.request.UserPostForm;
import com.dev.serviceImpl.PostServiceImpl;

@RestController
@RequestMapping("/post")
public class PostController {

	private final PostServiceImpl postServiceImpl;

	public PostController(PostServiceImpl postServ) {
		this.postServiceImpl = postServ;
	}

	@GetMapping
	public List<Post> getAllPost() {
		return postServiceImpl.getAllPost();
	}

	@GetMapping("/id/{postId}")
	public ResponseEntity<?> getPostByPostId(@PathVariable long postId) {
		Post post;
		try {
			post = postServiceImpl.getPostById(postId);
			if (post != null) {
				return new ResponseEntity<>(post, HttpStatus.OK);
			}
		} catch (PostImageUploadException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Post is not Found with id " + postId, HttpStatus.NO_CONTENT);

	}

	@PostMapping("/add")
	public ResponseEntity<?> savedPost(@ModelAttribute UserPostForm postForm) {
		Post post;
		try {
			post = postServiceImpl.savePost(postForm);
			if (post != null) {
				return new ResponseEntity<>(post, HttpStatus.OK);
			}
		} catch (PostImageUploadException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Post is not created", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/update/{postId}")
	public ResponseEntity<?> updatePost(@PathVariable long postId, @ModelAttribute UserPostForm postForm) {
		try {

			Post post = postServiceImpl.updatePost(postId, postForm);
			if (post != null) {
				return new ResponseEntity<>(post, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Post was not updated", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/user/{userName}")
	public ResponseEntity<?> getAllPostByUserName(@PathVariable String userName) {
		System.out.println("UserNAme for post of user " + userName);
		try {
			List<Post> postList = postServiceImpl.getAllPostByUserName(userName);
			System.out.println("PostList " + postList);
			if (postList != null) {
				return new ResponseEntity<>(postList, HttpStatus.OK);
			}
		} catch (UserNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity<>("Not Posted any post", HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable long postId) {
		try {
			boolean isDeleted = postServiceImpl.deletePost(postId);
			if (isDeleted) {
				return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Not Posted any post", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/like/{postId}/{userName}")
	public ResponseEntity<?> likePostByUser(@PathVariable long postId, @PathVariable String userName) {
		try {
			System.out.println("Post like postId " + postId + " and UserName " + userName);
			Post likedPost = postServiceImpl.likePost(postId, userName);
			System.out.println("Liked post details in controller " + likedPost);
			if (likedPost != null)
				return new ResponseEntity<>(likedPost, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("No Like to post", HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/unlike/{postId}/{userName}")
	public ResponseEntity<?> unLikePostByUser(@PathVariable long postId, @PathVariable String userName) {
		try {
			Post unlikedPost = postServiceImpl.unLikePost(postId, userName);
			if (unlikedPost != null) {
				return new ResponseEntity<>(unlikedPost, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("No Like to post", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/isLiked/{postId}/{userName}")
	public ResponseEntity<Boolean> isPostLikeByUser(@PathVariable long postId, @PathVariable String userName) {
		try {
			boolean islikedPost = postServiceImpl.isPostLiked(postId, userName);
			System.out.println(postId + " Isliked or not " + islikedPost);
			return ResponseEntity.ok(islikedPost);
		} catch (Exception ex) {
			return ResponseEntity.ok(false);
		}
	}

}
