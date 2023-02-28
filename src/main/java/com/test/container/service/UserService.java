package com.test.container.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.container.domain.User;
import com.test.container.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(User user) {
		User userEntity = userRepository.findById(user.getId()).get();

		if (userEntity != null) {
			userEntity = userRepository.save(userEntity);
		}
		return userEntity;
	}

	public void deleteUser(int Id) {
		userRepository.deleteById(Id);

	}

	public Optional<User> findByUserId(int Id) {
		return userRepository.findById(Id);
	}

	public List<User> findByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
}
