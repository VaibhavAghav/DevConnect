package com.dev.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long profileId;

	private String profileUserName;

	private String profileBio;

	private String gitHubLink;

	private String publicImage;

	private String CloudinaryImage;

	@OneToOne
	@JoinColumn(name = "user_Id")
	@JsonIgnore
	private User user;

	@ManyToMany(mappedBy = "likedByProfiles", fetch = FetchType.EAGER)
	@ToStringExclude
	@JsonIgnore
	private Set<Post> likedPosts = new HashSet<>();

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getProfileUserName() {
		return profileUserName;
	}

	public void setProfileUserName(String profileUserName) {
		this.profileUserName = profileUserName;
	}

	public String getProfileBio() {
		return profileBio;
	}

	public void setProfileBio(String profileBio) {
		this.profileBio = profileBio;
	}

	public String getGitHubLink() {
		return gitHubLink;
	}

	public void setGitHubLink(String gitHubLink) {
		this.gitHubLink = gitHubLink;
	}

	public String getPublicImage() {
		return publicImage;
	}

	public void setPublicImage(String publicImage) {
		this.publicImage = publicImage;
	}

	public String getCloudinaryImage() {
		return CloudinaryImage;
	}

	public void setCloudinaryImage(String cloudinaryImage) {
		CloudinaryImage = cloudinaryImage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Profile [profileId=" + profileId + ", profileUserName=" + profileUserName + ", profileBio=" + profileBio
				+ ", gitHubLink=" + gitHubLink + ", publicImage=" + publicImage + ", CloudinaryImage=" + CloudinaryImage
				+ "]";
	}

	public Set<Post> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(Set<Post> likedPosts) {
		this.likedPosts = likedPosts;
	}

	public Profile(Long profileId, String profileUserName, String profileBio, String gitHubLink, String publicImage,
			String cloudinaryImage, User user, Set<Post> likedPosts) {
		super();
		this.profileId = profileId;
		this.profileUserName = profileUserName;
		this.profileBio = profileBio;
		this.gitHubLink = gitHubLink;
		this.publicImage = publicImage;
		CloudinaryImage = cloudinaryImage;
		this.user = user;
		this.likedPosts = likedPosts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Profile profile = (Profile) o;
		return Objects.equals(profileId, profile.profileId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(profileId);
	}

}
