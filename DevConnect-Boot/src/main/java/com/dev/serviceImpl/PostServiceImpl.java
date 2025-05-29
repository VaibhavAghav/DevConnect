package com.dev.serviceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.entities.Post;
import com.dev.entities.Profile;
import com.dev.entities.User;
import com.dev.exception.PostImageUploadException;
import com.dev.exception.PostNotFoundException;
import com.dev.exception.UserNotFoundException;
import com.dev.repository.PostRepository;
import com.dev.repository.ProfileRepository;
import com.dev.request.UserPostForm;
import com.dev.service.PostService;
import com.dev.service.cloud.ImageService;

@Service
public class PostServiceImpl implements PostService {

	private final ProfileRepository profileRepository;

	private final PostRepository postRepository;

	private final ImageService imageService;

	private final UserServiceImpl userServiceImpl;

	private final ProfileServiceImpl profileServiceImpl;

	public PostServiceImpl(PostRepository postRepo, UserServiceImpl userServ, ProfileServiceImpl profileServ,
			ImageService imageSer, ProfileRepository profileRepository) {
		this.postRepository = postRepo;
		this.userServiceImpl = userServ;
		this.profileServiceImpl = profileServ;
		this.imageService = imageSer;
		this.profileRepository = profileRepository;
	}

	@Override
	public Post getPostById(long postId) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		if (optionalPost.isPresent()) {
			return optionalPost.get();
		}
		throw new PostNotFoundException("Post notFound with id " + postId);
	}

	@Override
	public Post savePost(UserPostForm postForm) {
		Post post = new Post();
		post.setPostTitle(postForm.getPostTitle());
		post.setPostContent(postForm.getPostContent());
		post.setPostCreation(LocalDateTime.now());
		post.setPostUpdation(LocalDateTime.now());

		User user = userServiceImpl.getUserByUserName(postForm.getUserName());
		if (user == null) {
			throw new UserNotFoundException("User not found: " + postForm.getUserName());
		}
		post.setUser(user);

		try {
			Map<String, Object> map = imageService.uploaFile(postForm.getPostphoto());
			post.setCloudinaryPostUrl(map.get("public_id").toString());
			post.setPublicPostUrl(map.get("secure_url").toString());
			return postRepository.save(post);
		} catch (IOException e) {
			throw new PostImageUploadException("Unable to upload image");
		}
	}

	@Override
	public Post updatePost(long postId, UserPostForm postForm) {
		Optional<Post> optionalPost = postRepository.findById(postId);
		if (optionalPost.isEmpty()) {
			throw new PostNotFoundException("Post not found with ID: " + postId);
		}

		Post post = optionalPost.get();

		post.setPostTitle(postForm.getPostTitle());
		post.setPostContent(postForm.getPostContent());
		post.setPostUpdation(LocalDateTime.now());

		User user = userServiceImpl.getUserByUserName(postForm.getUserName());
		if (user == null) {
			throw new UserNotFoundException("User not found for username: " + postForm.getUserName());
		}
		post.setUser(user);
		return postRepository.save(post);
	}

	@Override
	public boolean deletePost(long postId) {
		Post post = getPostById(postId);
		if (post != null) {
			try {
				imageService.deleteFile(post.getCloudinaryPostUrl());
			} catch (IOException e) {
				e.printStackTrace();
				throw new PostImageUploadException("Unable to Delete image for post Delete ");
			}
			postRepository.delete(post);
			return true;
		}
		return false;
	}

	@Override
	public List<Post> getAllPost() {
		return postRepository.findAll();
	}

	@Override
	public List<Post> getAllPostByUserName(String userName) {
		User user = userServiceImpl.getUserByUserName(userName);
		System.out.println("User for postService " + user);
		if (user == null) {
			throw new UserNotFoundException("User not found for post with userName " + userName);
		}
		List<Post> postList = postRepository.findAllPostByUserId(user.getUserId());
		return postList;
	}

	@Override
	public Post likePost(long postId, String userName) {
		Post post = getPostById(postId);
		System.out.println("Post for Like " + post);
		if (post == null) {
			return null;
		}
		Profile profile = profileServiceImpl.getProfileByUserName(userName);
		System.out.println("Profile for liked " + profile);
		if (profile == null) {
			return null;
		}
		post.getLikedByProfiles().add(profile);
		System.out.println("Poszt after srviecImpl liked " + post);
		return postRepository.save(post);
	}

	@Transactional
	@Override
	public Post unLikePost(long postId, String userName) {
		Post post = getPostById(postId);
		System.out.println("post in unlikepost " + post);

		if (post == null) {
			return null;
		}
		Profile profile = profileServiceImpl.getProfileByUserName(userName);
		System.out.println(userName + " profile in unlikepost " + profile);
		if (profile == null) {
			return null;
		}
		post.removeLike(profile);

		// Save both to persist changes on both sides
		postRepository.save(post);
		// profileRepository.save(profile);

		System.out.println("profile is been removed " + post.getPostId());
		return post;
	}

	public boolean isPostLiked(long postId, String userName) {
		Post post = getPostById(postId);
		System.out.println("post in unlikepost " + post);

		if (post == null) {
			return false;
		}
		Profile profile = profileServiceImpl.getProfileByUserName(userName);
		System.out.println(userName + " profile in unlikepost " + profile);
		if (profile == null) {
			return false;
		}

		return post.getLikedByProfiles().contains(profile);
	}

}
