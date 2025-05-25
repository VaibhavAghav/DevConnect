package com.dev.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.entities.User;
import com.dev.repository.UserRepository;
import com.dev.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepo) {
		this.userRepository = userRepo;
	}

	@Override
	public User savedUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(long id, User user) {
		User existingUser = getUserById(id);
		if (existingUser == null) {
			throw new EntityNotFoundException("user with id " + id + " is not found");
		}
		return userRepository.save(user);
	}

	@Override
	public User getUserById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public boolean deleteUser(long id) {
		User user = getUserById(id);
		if (user != null) {
			userRepository.delete(user);
			return true;
		}
		return false;
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByUserName(String userName) {
		Optional<User> user = userRepository.findByUserName(userName);
		if (user.isPresent()) {
			return user.get();
		}
		throw new EntityNotFoundException("User with userName " + userName + " is not Found");
	}

}
