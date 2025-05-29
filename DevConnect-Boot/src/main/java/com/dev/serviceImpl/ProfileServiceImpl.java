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
		System.out.println("UserForm profile saved profile first 1 ");
		User user = userServiceImpl.getUserById(profileForm.getUserId());
		System.out.println("UserForm profile saved profile first 2 user " + user);

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
				System.out.println("Profile Image Working before uploading ");
				Map map = imageService.uploaFile(profileForm.getProfilePhoto());
				System.out.println("Profile Image Working after uploading ");

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
		System.out.println("1 upadateProfile 1" + optionalProfile);
		if (optionalProfile.isEmpty()) {
			System.out.println("2 Profile not found with Id " + profileForm.getProfileId());
			return null;
		}
		System.out.println("3 upadateProfile 2 not empty" + optionalProfile);
		Profile profile = optionalProfile.get();
		profile.setGitHubLink(profileForm.getGitHubLink());
		profile.setProfileBio(profileForm.getProfileBio());
		profile.setProfileUserName(profileForm.getProfileUserName());
		System.out.println("4 upadateProfile 2 setting info" + profile);

		try {
			if (profileForm.getProfilePhoto() != null && !profileForm.getProfilePhoto().isEmpty()) {
				System.out.println("5 upadateProfile 3 image not null " + profile.getCloudinaryImage());

				if (profile.getCloudinaryImage() != null && !profile.getCloudinaryImage().isEmpty()) {
					System.out.println("6 Previous image deleting 5");
					Map deleteResult = imageService.deleteFile(profile.getCloudinaryImage());
					System.out.println("7 Old image delete result: " + deleteResult);
				}
				System.out.println("8 image uploading 6 ");
				Map map = imageService.uploaFile(profileForm.getProfilePhoto());
				profile.setCloudinaryImage(map.get("public_id").toString());
				profile.setPublicImage(map.get("secure_url").toString());
				System.out.println("9 upadateProfile 7 image uploaded" + profile);

			}
			System.out.println("last upadateProfile saveding last " + profile);
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

//	@Override
//	public Profile getProfileByUserName(String userName) {
//		Optional<ProfiledUser> profiledUser = profileRepository.findProfileByUserName(userName);
//		System.out.println("Profile by userNAme " + userName);
//		System.out.println("Profile by ProfiledUser " + profiledUser.isPresent());
//		if (profiledUser.isPresent()) {
//			Profile profile = new Profile();
//			profile.setProfileId(profiledUser.get().getProfileId());
//			profile.setProfileBio(profiledUser.get().getProfileBio());
//			profile.setProfileUserName(profiledUser.get().getProfileUserName());
//			profile.setCloudinaryImage(profiledUser.get().getCloudinaryImage());
//			profile.setGitHubLink(profiledUser.get().getGitHubLink());
//			profile.setPublicImage(profiledUser.get().getPublicImage());
//			System.out.println("Soyut Profile from ujserName " + profile);
//			System.out.println("Soyut Profile from ujserName " + userName);
//			return profile;
//		}
//		return null;
//	}
	@Override
	public Profile getProfileByUserName(String userName) {
		Optional<Profile> profile = profileRepository.findByUserName(userName);
		System.out.println(profile);
		return profile.orElse(null);
	}

	@Override
	public Profile getProfileByProfileName(String profileName) {
		Optional<Profile> profile = profileRepository.findProfileByProfileName(profileName);
		if (profile.isPresent()) {
			return profile.get();
		}
		return null;
	}

}
