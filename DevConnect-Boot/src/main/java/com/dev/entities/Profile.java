package com.dev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	public Profile(Long profileId, String profileUserName, String profileBio, String gitHubLink, String publicImage,
			String cloudinaryImage, User user) {
		super();
		this.profileId = profileId;
		this.profileUserName = profileUserName;
		this.profileBio = profileBio;
		this.gitHubLink = gitHubLink;
		this.publicImage = publicImage;
		CloudinaryImage = cloudinaryImage;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Profile [profileId=" + profileId + ", profileUserName=" + profileUserName + ", profileBio=" + profileBio
				+ ", gitHubLink=" + gitHubLink + ", publicImage=" + publicImage + ", CloudinaryImage=" + CloudinaryImage
				+ ", user=" + user + "]";
	}
	
	

}
