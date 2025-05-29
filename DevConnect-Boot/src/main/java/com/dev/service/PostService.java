package com.dev.service;

import java.util.List;

import com.dev.entities.Post;
import com.dev.exception.PostImageUploadException;
import com.dev.exception.UserNotFoundException;
import com.dev.request.UserPostForm;

public interface PostService {

	public Post savePost(UserPostForm postForm);

	public Post updatePost(long postId, UserPostForm postForm);

	public Post getPostById(long postId);

	public boolean deletePost(long postId);

	public List<Post> getAllPost();

	public List<Post> getAllPostByUserName(String profileName);

	public Post likePost(long postId, String userName);// liking userName //post postId

	public Post unLikePost(long postId, String userName);

}
