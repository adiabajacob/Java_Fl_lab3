package com.roczyno.fl_lab3_project.service;

import com.roczyno.fl_lab3_project.model.User;
import com.roczyno.fl_lab3_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createUser_ShouldSaveUserWithTimestamp() {
		// Arrange
		User user = new User();
		user.setUsername("testuser");
		when(userRepository.save(user)).thenReturn(user);

		// Act
		User createdUser = userService.createUser(user);

		// Assert
		assertNotNull(createdUser.getCreatedAt(), "Created timestamp should be set");
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void updateUser_ShouldUpdateFields_WhenFieldsArePresent() {
		// Arrange
		Long userId = 1L;
		User existingUser = new User();
		existingUser.setId(userId);
		existingUser.setUsername("oldUsername");
		existingUser.setEmail("oldEmail@example.com");
		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		User updateRequest = new User();
		updateRequest.setUsername("newUsername");
		updateRequest.setEmail("newEmail@example.com");

		// Mock the save method to return the updated user
		when(userRepository.save(existingUser)).thenReturn(existingUser);

		// Act
		User updatedUser = userService.updateUser(userId, updateRequest);

		// Assert
		assertNotNull(updatedUser, "Updated user should not be null");
		assertEquals("newUsername", updatedUser.getUsername());
		assertEquals("newEmail@example.com", updatedUser.getEmail());
		verify(userRepository, times(1)).save(existingUser);
	}


	@Test
	void getUser_ShouldReturnUser_WhenUserExists() {
		// Arrange
		Long userId = 1L;
		User user = new User();
		user.setId(userId);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// Act
		User foundUser = userService.getUser(userId);

		// Assert
		assertNotNull(foundUser);
		assertEquals(userId, foundUser.getId());
	}

	@Test
	void getUser_ShouldThrowException_WhenUserNotFound() {
		// Arrange
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Act & Assert
		Exception exception = assertThrows(IllegalStateException.class, () -> userService.getUser(userId));
		assertEquals("user not found", exception.getMessage());
	}

	@Test
	void getAllUsers_ShouldReturnListOfUsers() {
		// Arrange
		User user1 = new User();
		User user2 = new User();
		when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

		// Act
		List<User> users = userService.getAllUsers();

		// Assert
		assertEquals(2, users.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void deleteUser_ShouldDeleteUser_WhenUserExists() {
		// Arrange
		Long userId = 1L;
		User user = new User();
		user.setId(userId);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// Act
		String result = userService.deleteUser(userId);

		// Assert
		assertEquals("user deleted successfully", result);
		verify(userRepository, times(1)).delete(user);
	}

	@Test
	void deleteUser_ShouldThrowException_WhenUserNotFound() {
		// Arrange
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Act & Assert
		Exception exception = assertThrows(IllegalStateException.class, () -> userService.deleteUser(userId));
		assertEquals("user not found", exception.getMessage());
		verify(userRepository, never()).delete(any(User.class));
	}
}
