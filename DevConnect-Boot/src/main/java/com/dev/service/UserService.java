package com.dev.service;

import java.util.List;

import com.dev.entities.User;

public interface UserService {

	public User savedUser(User user);

	public User updateUser(long id, User user);

	public User getUserById(long id);

	public boolean deleteUser(long id);

	public List<User> getAllUser();
	
	public User getUserByUserName(String userName);

}
