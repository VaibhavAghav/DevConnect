package com.dev.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	private LocalDateTime postCreation;
	private LocalDateTime postUpdation;

	@ManyToOne
	@JsonIgnoreProperties({"posts", "password", "email"})
	private User user;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"))
	@ToStringExclude
	private Set<Profile> likedByProfiles = new HashSet<>();

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

	public Set<Profile> getLikedByProfiles() {
		return likedByProfiles;
	}

	public void setLikedByProfiles(Set<Profile> likedByProfiles) {
		this.likedByProfiles = likedByProfiles;
	}

	public void removeLike(Profile profile) {
		this.likedByProfiles.remove(profile);
		profile.getLikedPosts().remove(this);
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", postTitle=" + postTitle + ", postContent=" + postContent
				+ ", publicPostUrl=" + publicPostUrl + ", cloudinaryPostUrl=" + cloudinaryPostUrl + ", postCreation="
				+ postCreation + ", postUpdation=" + postUpdation + "]";
	}

	public Post(long postId, String postTitle, String postContent, String publicPostUrl, String cloudinaryPostUrl,
			LocalDateTime postCreation, LocalDateTime postUpdation, User user, Set<Profile> likedByProfiles) {
		super();
		this.postId = postId;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.publicPostUrl = publicPostUrl;
		this.cloudinaryPostUrl = cloudinaryPostUrl;
		this.postCreation = postCreation;
		this.postUpdation = postUpdation;
		this.user = user;
		this.likedByProfiles = likedByProfiles;
	}

}
