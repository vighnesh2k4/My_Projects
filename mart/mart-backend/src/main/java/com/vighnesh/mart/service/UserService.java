package com.vighnesh.mart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.User;
import com.vighnesh.mart.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
	public User getUserAuth(User user) throws MartException {
		if (user.getUsername()==null) {
			throw new MartException("Invalid username");
		}
		if (user.getPassword()==null) {
			throw new MartException("Invalid password");
		}	
		List<User> userById = userRepository.getUser(user);
		if (userById.size()==0) {
			return null;
		}
		return userById.get(0);
	}
	
	@Transactional
	public void addUser(User user) throws MartException {
		if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
			throw new MartException("Username cannot be null or empty");
		}
		if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
			throw new MartException("Email cannot be null or empty");
		}
		if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
			throw new MartException("Password cannot be null or empty");
		}
		List<User> duplicateuser = userRepository.getUser(user);
		if (duplicateuser.size() > 0) {
			throw new MartException("Username already exists, please enter a new username");
		}
		userRepository.addUser(user);
	}
	
	@Transactional
	public List<User> getAllUsers() {
		return userRepository.getUser(new User());
	}
	
	@Transactional
	public User getUser(User user) throws MartException {
		if (user.getUser_id() <= 0) {
			throw new MartException("Invalid user ID");
		}		
		List<User> userById = userRepository.getUser(user);
		if (userById.size()==0) {
			return null;
		}
		return userById.get(0);
	}
	
	@Transactional
	public void updateUser(User user) throws MartException {
		if (user.getUser_id()<=0) {
			throw new MartException("User ID invalid");
		}
		if (user.getUsername() != null && user.getUsername().trim().isEmpty()) {
			throw new MartException("Username cannot be null or empty");
		}
		if (user.getEmail() != null && user.getEmail().trim().isEmpty()) {
			throw new MartException("Email cannot be null or empty");
		}
		if (user.getPassword() != null && user.getPassword().trim().isEmpty()) {
			throw new MartException("Password cannot be null or empty");
		}
		
		if(user.getUsername()!=null) {
			User temp2 = new User();
			temp2.setUsername(user.getUsername());
			List<User> duplicateUser = userRepository.getUser(temp2);
			if (duplicateUser.size() > 0 && duplicateUser.get(0).getUser_id()!=user.getUser_id()) {
				throw new MartException("Username already exists, please enter a new username");
			}
		}
		userRepository.updateUser(user);
	}
	
	@Transactional
	public void updateUserRole(User user) throws MartException {
		if (user.getUser_id()<=0) {
			throw new MartException("User ID invalid");
		}
		if (user.getRole() == null) {
			throw new MartException("Role cannot be null or empty");
		}
		userRepository.updateUser(user);
	}
	
	@Transactional
	public void deleteUser(User user) throws MartException {
		if (user.getUser_id()<=0) {
			throw new MartException("User ID invalid");
		}
		user.setStatus(User.Status.INACTIVE);
		userRepository.updateUser(user);
	}
}
