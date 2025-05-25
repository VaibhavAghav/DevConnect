package com.dev.controller.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.dev.constant.Role;
import com.dev.entities.User;
import com.dev.repository.UserRepository;
import com.dev.request.UserLogin;
import com.dev.request.UserRegister;
import com.dev.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
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

		userRepository.save(user);

		return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
	}
}
