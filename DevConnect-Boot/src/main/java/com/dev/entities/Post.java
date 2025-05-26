package com.dev.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long postId;
	private String postTitle;
	private String postContent;
	private String publicPostUrl;
	private String cloudinaryPostUrl;
	private int postLikes;
	private LocalDateTime postCreation;
	private LocalDateTime postUpdation;

	@ManyToOne
	@JsonIgnore
	private User user;

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

	public String getPublicPostUrl() {
		return publicPostUrl;
	}

	public void setPublicPostUrl(String publicPostUrl) {
		this.publicPostUrl = publicPostUrl;
	}

	public String getCloudinaryPostUrl() {
		return cloudinaryPostUrl;
	}

	public void setCloudinaryPostUrl(String cloudinaryPostUrl) {
		this.cloudinaryPostUrl = cloudinaryPostUrl;
	}

	public int getPostLikes() {
		return postLikes;
	}

	public void setPostLikes(int postLikes) {
		this.postLikes = postLikes;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(long postId, String postTitle, String postContent, String publicPostUrl, String cloudinaryPostUrl,
			int postLikes, LocalDateTime postCreation, LocalDateTime postUpdation, User user) {
		super();
		this.postId = postId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.publicPostUrl = publicPostUrl;
		this.cloudinaryPostUrl = cloudinaryPostUrl;
		this.postLikes = postLikes;
		this.postCreation = postCreation;
		this.postUpdation = postUpdation;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", postTitle=" + postTitle + ", postContent=" + postContent
				+ ", publicPostUrl=" + publicPostUrl + ", cloudinaryPostUrl=" + cloudinaryPostUrl + ", postLikes="
				+ postLikes + ", postCreation=" + postCreation + ", postUpdation=" + postUpdation + ", user=" + user
				+ "]";
	}

}
