package com.dev.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.User;
import com.dev.serviceImpl.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	private final UserServiceImpl userServiceImpl;

	public UserController(UserServiceImpl serviceImpl) {
		this.userServiceImpl = serviceImpl;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id, Principal principal) {
		User user = userServiceImpl.getUserById(id);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		if (!user.getUsername().equals(principal.getName())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping("/add")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		User savedUser = null;
		try {
			savedUser = userServiceImpl.savedUser(user);
			if (savedUser != null) {
				return new ResponseEntity<>(savedUser, HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable long userId, @RequestBody User user) {
		try {
			User updatedUser = userServiceImpl.updateUser(userId, user);
			if (updatedUser != null) {
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			}
		} catch (EntityNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Dont write Anything", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable long userId) {
		boolean isDeleted = userServiceImpl.deleteUser(userId);
		if (isDeleted) {
			return new ResponseEntity<>("User deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("User not Found", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/getuser/{userName}")
	public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
		System.out.println("hello there its'/getuser ");
		try {
			User user = userServiceImpl.getUserByUserName(userName);
			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
		} catch (EntityNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("User not Found", HttpStatus.BAD_REQUEST);
	}

}
