package com.dev.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dev.constant.Role;
import jakarta.persistence.*;

@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(unique = true)
	private String userName;

	@Column(unique = true)
	private String userEmail;

	private String userPassword;

	@Enumerated(EnumType.STRING)
	private Role role;

	private boolean isEnabled = true;
	private boolean isAccountNonExpired = true;
	private boolean isAccountNonLocked = true;
	private boolean isCredentialsNonExpired = true;

	private String phoneNumber;
	private boolean isPhoneNumberVerified;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Profile profile;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> posts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comment> comments;

	public User() {
	}

	public User(long userId, String userName, String userEmail, String userPassword, Role role, boolean isEnabled,
			boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired,
			String phoneNumber, boolean isPhoneNumberVerified) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.role = role;
		this.isEnabled = isEnabled;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.phoneNumber = phoneNumber;
		this.isPhoneNumberVerified = isPhoneNumberVerified;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

	// Getters & Setters

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isPhoneNumberVerified() {
		return isPhoneNumberVerified;
	}

	public void setPhoneNumberVerified(boolean phoneNumberVerified) {
		isPhoneNumberVerified = phoneNumberVerified;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", role=" + role
				+ ", isEnabled=" + isEnabled + ", phoneNumber=" + phoneNumber + ", isPhoneNumberVerified="
				+ isPhoneNumberVerified + "]";
	}
}
