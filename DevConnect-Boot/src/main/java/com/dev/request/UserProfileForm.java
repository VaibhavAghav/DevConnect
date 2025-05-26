package com.dev.request;

import org.springframework.web.multipart.MultipartFile;

public class UserProfileForm {

	private Long profileId;
	private String profileUserName;
	private String profileBio;
	private String gitHubLink;
	private MultipartFile profilePhoto;
	private Long userId;

	@Override
	public String toString() {
		return "UserProfileForm [profileId=" + profileId + ", profileUserName=" + profileUserName + ", profileBio="
				+ profileBio + ", gitHubLink=" + gitHubLink + ", profilePhoto=" + profilePhoto + ", userId=" + userId
				+ "]";
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

	public MultipartFile getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(MultipartFile profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public UserProfileForm(long profileId, String profileUserName, String profileBio, String gitHubLink,
			MultipartFile profilePhoto, Long userId) {
		super();
		this.profileId = profileId;
		this.profileUserName = profileUserName;
		this.profileBio = profileBio;
		this.gitHubLink = gitHubLink;
		this.profilePhoto = profilePhoto;
		this.userId = userId;
	}

	public UserProfileForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
