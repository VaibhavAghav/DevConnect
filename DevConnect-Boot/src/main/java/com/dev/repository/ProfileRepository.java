package com.dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.entities.Profile;
import com.dev.request.ProfiledUser;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	@Query(value = "select * from profile where user_id = :userId", nativeQuery = true)
	public Optional<Profile> findByUserId(@Param("userId") long userId);

//	@Query(value = "SELECT p.profile_id AS profileId, "
//			+ "p.public_image AS publicImage, p.cloudinary_image AS cloudinaryImage, "
//			+ "p.git_hub_link AS gitHubLink, p.profile_bio AS profileBio, p.profile_user_name AS profileUserName "
//			+ "FROM profile p INNER JOIN user u ON u.user_id = p.user_id WHERE u.user_name = :userName", nativeQuery = true)
	@Query("SELECT p FROM Profile p WHERE p.user.userName = :userName")
	Optional<ProfiledUser> findProfileByUserName(@Param("userName") String userName);

}
