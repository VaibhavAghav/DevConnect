package com.dev.serviceImpl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.entities.Profile;
import com.dev.entities.User;
import com.dev.repository.ProfileRepository;
import com.dev.repository.UserRepository;
import com.dev.request.ProfiledUser;
import com.dev.request.UserProfileForm;
import com.dev.service.ProfileService;
import com.dev.service.cloud.ImageService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileServiceImpl implements ProfileService {

	private final ProfileRepository profileRepository;

	private final UserServiceImpl userServiceImpl;

	private final ImageService imageService;

	public ProfileServiceImpl(ProfileRepository profileRepo, ImageService imageSer, UserServiceImpl userServiceImpl) {
		this.profileRepository = profileRepo;
		this.userServiceImpl = userServiceImpl;
		this.imageService = imageSer;
	}

	// profile_id cloudinary_image git_hub_link profile_bio profile_user_name
	// public_image user_id

	@Override
	public Profile savedProfile(UserProfileForm profileForm) {

		User user = userServiceImpl.getUserById(profileForm.getUserId());

		if (user == null) {
			System.out.println("User not found with id " + profileForm.getUserId());
			return null;
		}

		Profile profile = new Profile();
		profile.setGitHubLink(profileForm.getGitHubLink());
		profile.setProfileBio(profileForm.getProfileBio());
		profile.setProfileUserName(profileForm.getProfileUserName());
		try {
			if (profileForm.getProfilePhoto().isEmpty()) {

			} else {
				Map map = imageService.uploaFile(profileForm.getProfilePhoto());
				profile.setPublicImage(map.get("secure_url").toString());
				profile.setCloudinaryImage(map.get("public_id").toString());
			}
			profile.setUser(user);
			return profileRepository.save(profile);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in Image Saved Profile " + e.getMessage());
		}
		return null;
	}

	@Override
	public Profile updateProfile(long profileId, UserProfileForm profileForm) {
		Optional<Profile> optionalProfile = profileRepository.findById(profileId);

		if (optionalProfile.isEmpty()) {
			System.out.println("Profile not found with Id " + profileForm.getProfileId());
			return null;
		}

		Profile profile = optionalProfile.get();
		profile.setGitHubLink(profileForm.getGitHubLink());
		profile.setProfileBio(profileForm.getProfileBio());
		profile.setProfileUserName(profileForm.getProfileUserName());

		try {
			if (profileForm.getProfilePhoto() != null && !profileForm.getProfilePhoto().isEmpty()) {

				if (profile.getCloudinaryImage() != null && !profile.getCloudinaryImage().isEmpty()) {
					Map deleteResult = imageService.deleteFile(profile.getCloudinaryImage());
					System.out.println("Old image delete result: " + deleteResult);
				}

				Map map = imageService.uploaFile(profileForm.getProfilePhoto());
				profile.setCloudinaryImage(map.get("public_id").toString());
				profile.setPublicImage(map.get("secure_url").toString());
			}
			return profileRepository.save(profile);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error uploading image: " + e.getMessage());
		}
		return null;
	}

	@Override
	public Profile getProfileByUserId(long userId) {
		Optional<Profile> profile = profileRepository.findByUserId(userId);
		if (profile.isPresent()) {
			return profile.get();
		}
		throw new EntityNotFoundException("User not found with id " + userId);
	}

	@Override
	public boolean deleteProfile(long profileId) {
		Optional<Profile> profile = profileRepository.findById(profileId);
		if (profile.isPresent()) {
			profileRepository.delete(profile.get());
			return true;
		}
		return false;
	}

	@Override
	public Profile getProfileByProfileId(long profileId) {

		Optional<Profile> profile = profileRepository.findById(profileId);
		if (profile.isPresent()) {
			return profile.get();
		}

		return null;
	}

	@Override
	public Profile getProfileByUserName(String userName) {
		Optional<ProfiledUser> profiledUser = profileRepository.findProfileByUserName(userName);
		System.out.println("Profile by userNAme " + userName);
		System.out.println("Profile by ProfiledUser " + profiledUser);
		if (profiledUser.isPresent()) {
			Profile profile = new Profile();
			profile.setProfileId(profiledUser.get().getProfileId());
			profile.setProfileBio(profiledUser.get().getProfileBio());
			profile.setProfileUserName(profiledUser.get().getProfileUserName());
			profile.setCloudinaryImage(profiledUser.get().getCloudinaryImage());
			profile.setGitHubLink(profiledUser.get().getGitHubLink());
			profile.setPublicImage(profiledUser.get().getPublicImage());
			return profile;
		}
		return null;
	}

}
