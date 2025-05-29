package com.dev.entities;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;

	private String content;

	private LocalDateTime commentTime;

	@ManyToOne
	@JsonIgnoreProperties({ "comments", "posts" })
	private User user;

	@ManyToOne
	@JsonIgnoreProperties({ "comments" })
	@ToStringExclude
	private Post post;

	public Comment() {
	}

	public Comment(long commentId, String content, LocalDateTime commentTime, Post post, User user) {
		this.commentId = commentId;
		this.content = content;
		this.commentTime = commentTime;
		this.post = post;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", content=" + content + ", commentTime=" + commentTime + "]";
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(LocalDateTime commentTime) {
		this.commentTime = commentTime;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// Getters, Setters, toString
}
