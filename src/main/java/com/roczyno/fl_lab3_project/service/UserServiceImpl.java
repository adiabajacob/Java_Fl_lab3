package com.roczyno.fl_lab3_project.service;

import com.roczyno.fl_lab3_project.model.User;
import com.roczyno.fl_lab3_project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(User request) {
		request.setCreatedAt(LocalDateTime.now());
		return userRepository.save(request);
	}

	@Override
	public User updateUser(Long userId, User request) {
		User user=getUser(userId);
		if(request.getUsername()!=null){
			user.setUsername(request.getUsername());
		}
		if(request.getEmail()!=null){
			user.setEmail(request.getEmail());
		}
		if(request.getPhone()!=null){
			user.setPhone(request.getPhone());
		}

		return userRepository.save(user);
	}

	@Override
	public User getUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(()-> new IllegalStateException("user not found"));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public String deleteUser(Long userId) {
		User user=getUser(userId);
		userRepository.delete(user);
		return "user deleted successfully";
	}
}
