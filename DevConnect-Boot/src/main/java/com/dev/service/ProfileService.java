package com.dev.service;

import com.dev.entities.Profile;
import com.dev.request.UserProfileForm;

public interface ProfileService {

	public Profile savedProfile(UserProfileForm profileForm);

	public Profile updateProfile(long profileId, UserProfileForm profileForm);

	public Profile getProfileByUserId(long userId);

	public boolean deleteProfile(long id);

	public Profile getProfileByProfileId(long profileId);

	public Profile getProfileByUserName(String userName);

}
