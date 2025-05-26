package com.dev.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.Profile;
import com.dev.request.UserProfileForm;
import com.dev.service.ProfileService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

	private final ProfileService profileService;

	public ProfileController(ProfileService profileSer) {
		this.profileService = profileSer;
	}

	@PostMapping("/add")
	public ResponseEntity<?> saveProfile(@ModelAttribute UserProfileForm profileForm) {
		try {
			System.out.println(profileForm);
			Profile profile = profileService.savedProfile(profileForm);
			if (profile != null) {
				return new ResponseEntity<>(profile, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@PutMapping("/update/{profileId}")
	public ResponseEntity<?> updateProfile(@PathVariable long profileId, @ModelAttribute UserProfileForm profileForm) {
		Profile profile = profileService.updateProfile(profileId, profileForm);
		if (profile == null) {
			return new ResponseEntity<>("user not found to update ", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(profile, HttpStatus.OK);

	}

	@GetMapping("/get/{profileId}")
	public ResponseEntity<?> getProfileById(@PathVariable long profileId) {
		Profile profile = profileService.getProfileByProfileId(profileId);
		if (profile == null) {
			return new ResponseEntity<>("User profile not found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}

	@GetMapping("/user/get/{userId}")
	public ResponseEntity<?> getProfileByUserId(@PathVariable long userId) {
		try {

			Profile profile = profileService.getProfileByUserId(userId);
			if (profile != null) {
				return new ResponseEntity<>(profile, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("No Profile Found with this userdId " + userId, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/user/{userName}")
	public ResponseEntity<?> getProfileByUserName(@PathVariable String userName) {
		Profile profile = profileService.getProfileByUserName(userName);
		if (profile == null) {
			return new ResponseEntity<>("No Profile Found with this userName " + userName, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}

}
