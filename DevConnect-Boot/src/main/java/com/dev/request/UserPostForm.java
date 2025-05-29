package com.dev.request;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class UserPostForm {

	private long postId;
	private String postTitle;
	private String postContent;
	private LocalDateTime postCreation;
	private LocalDateTime postUpdation;
	private MultipartFile postphoto;
	private String userName;

	public UserPostForm() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserPostForm [postId=" + postId + ", postTitle=" + postTitle + ", postContent=" + postContent
				+ ", postCreation=" + postCreation + ", postUpdation=" + postUpdation + ", postphoto=" + postphoto
				+ ", userName=" + userName + "]";
	}

	public UserPostForm(long postId, String postTitle, String postContent, LocalDateTime postCreation,
			LocalDateTime postUpdation, MultipartFile postphoto, String userName) {
		super();
		this.postId = postId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postCreation = postCreation;
		this.postUpdation = postUpdation;
		this.postphoto = postphoto;
		this.userName = userName;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public LocalDateTime getPostCreation() {
		return postCreation;
	}

	public void setPostCreation(LocalDateTime postCreation) {
		this.postCreation = postCreation;
	}

	public LocalDateTime getPostUpdation() {
		return postUpdation;
	}

	public void setPostUpdation(LocalDateTime postUpdation) {
		this.postUpdation = postUpdation;
	}

	public MultipartFile getPostphoto() {
		return postphoto;
	}

	public void setPostphoto(MultipartFile postphoto) {
		this.postphoto = postphoto;
	}

}
