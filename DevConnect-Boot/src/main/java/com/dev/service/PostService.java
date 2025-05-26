package com.dev.service;

import java.util.List;

import com.dev.entities.Post;

public interface PostService {

	public Post savePost(Post post);

	public Post updatePost(Post post);

	public boolean deletePost(long postId);

	public List<Post> getAllPost();

	public List<Post> getAllPostByUserName(String profileName);

}
