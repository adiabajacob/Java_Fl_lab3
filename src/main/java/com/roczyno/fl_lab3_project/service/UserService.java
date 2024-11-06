package com.roczyno.fl_lab3_project.service;

import com.roczyno.fl_lab3_project.model.User;

import java.util.List;

public interface UserService {
	User createUser(User request);
	User updateUser(Long userId,User request);
	User getUser(Long userId);
	List<User> getAllUsers();
	String deleteUser(Long userId);
}
