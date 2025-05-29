package com.dev.controller.auth;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.dev.constant.Role;
import com.dev.entities.Profile;
import com.dev.entities.User;
import com.dev.repository.ProfileRepository;
import com.dev.repository.UserRepository;
import com.dev.request.UserLogin;
import com.dev.request.UserRegister;
import com.dev.security.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ProfileRepository profileRepository;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepo,
			ProfileRepository profileRepo, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepo;
		this.profileRepository = profileRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLogin userLogin) {
		System.out.println("AuthController.loginUser()");
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword()));
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}

		String token = jwtUtil.generateToken(userLogin.getUserName());
		return ResponseEntity.ok(Collections.singletonMap("token", token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRegister userRegister) {

		System.out.println("AuthController.registerUser()");
		if (userRepository.findByUserName(userRegister.getUserName()).isPresent()) {
			return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUserName(userRegister.getUserName());
		user.setUserEmail(userRegister.getUserEmail());
		user.setUserPassword(passwordEncoder.encode(userRegister.getPassword()));
		user.setRole(Role.USER);

		Profile profile = new Profile();
		profile.setProfileUserName(userRegister.getUserName());
		profile.setProfileBio(userRegister.getUserName());

		User savedUser = userRepository.save(user);
		profile.setUser(savedUser);
		profileRepository.save(profile);
		System.out.println("AuthController Profile saved " + profile);
		System.out.println("AuthController User saved " + user);
		Map<String, String> response = new HashMap<>();
		response.put("message", "User registered successfully!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
