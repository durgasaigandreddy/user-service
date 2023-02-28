package com.test.container.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.container.domain.User;
import com.test.container.service.UserService;

@RestController
@RequestMapping("/svc/user/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping("/update")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@DeleteMapping("/delete/{Id}")
	public void deleteUser(@PathVariable int Id) {
		userService.deleteUser(Id);
	}

	@GetMapping("/findById/{Id}")
	public Optional<User> findByUserId(@PathVariable int Id) {
		return userService.findByUserId(Id);
	}

	@GetMapping("/findByFirstName/{firstName}")
	public List<User> findByFirstName(@PathVariable String firstName) {
		return userService.findByFirstName(firstName);
	}

	@GetMapping("/findAllUsers")
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}

}
