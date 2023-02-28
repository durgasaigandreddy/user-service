package com.test.container.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.container.domain.User;
import com.test.container.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	@InjectMocks // inject the mock dependencies into userservice instance.
	private UserService userService;

	@Mock // dependency of userservice
	private UserRepository userRepository;

	private User user;

	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(1);
		user.setFirstName("John");
		user.setLastName("AdduRoad");
	}

	@Test
	public void createUserTest() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		User userEntity = userService.createUser(user);
		assertEquals(user,userEntity);
	}

	@Test
	public void createUserTestNegative() {
		User user1 = new User();
		user1.setId(1);
		user1.setFirstName("John");
		user1.setLastName("bhimavaram");
		when(userRepository.save(any(User.class))).thenReturn(user);
		User userEntity = userService.createUser(user);
		assertNotEquals(user1, userEntity);
	}

	@Test
	public void testUpdateTask() {
		User user = new User();
		user.setId(1);
		user.setFirstName("John");
		user.setLastName("AdduRoad");
		Optional<User> optionalUser = Optional.ofNullable(user);// optional.ifexists,optional.ispresent.then .get
																// applies.
		when(userRepository.findById(1)).thenReturn(optionalUser);// it returns optional object.
		when(userRepository.save(any(User.class))).thenReturn(user);// we can update presented object only.it gets
																	// findByid method internally.
		User UserEntity = userService.updateUser(user);//
		assertEquals(user, UserEntity);
	}

	@Test
	public void testUpdateTaskNegative() {
		User user2 = new User();
		user2.setId(1);
		user2.setFirstName("John");
		user2.setLastName("bhimavaram");
		Optional<User> optionalUser = Optional.ofNullable(user2);
		when(userRepository.findById(1)).thenReturn(optionalUser);
		when(userRepository.save(any(User.class))).thenReturn(user2);
		User userEntity = userService.updateUser(user2);
		assertNotEquals(user, userEntity);
	}

	@Test
	public void testGetUser() {
		User user = new User();
		user.setId(1);
		user.setFirstName("John");
		user.setLastName("AdduRoad");
		Optional<User> optionalUser1 = Optional.ofNullable(user);
		when(userRepository.findById(1)).thenReturn(optionalUser1);
		Optional<User> userOptionalEntity = userService.findByUserId(1);
		assertEquals(user, userOptionalEntity.get());
	}

	@Test
	public void testGetUserNegative() {
		User user1 = new User();
		user1.setId(1);
		user1.setFirstName("John");
		user1.setLastName("Bhimavaram");

		Optional<User> optionalUser1 = Optional.ofNullable(user1);
		when(userRepository.findById(1)).thenReturn(optionalUser1);
		Optional<User> userOptionalEntity = userService.findByUserId(1);
		assertEquals(user1, userOptionalEntity.get());
	}

	@Test
	public void testFindByFirstName() {
		List<User> list = new ArrayList<User>();
		list.add(user);
		when(userRepository.findByFirstName("John")).thenReturn(list);
		List<User> entityList = userService.findByFirstName("John");
		assertEquals(list.size(), entityList.size());
	}

	@Test
	public void testFindByFirstNameNegative() {
		List<User> list1 = new ArrayList<User>();
		list1.add(user);
		when(userRepository.findByFirstName("Sai")).thenReturn(list1);
		List<User> entityList1 = userService.findByFirstName("Sai");
		assertEquals(list1.size(), entityList1.size());
	}

	@Test
	public void testFindAllUsers() {
		List<User> list = new ArrayList<User>();
		list.add(user);
		when(userRepository.findAll()).thenReturn(list);
		List<User> entityList = userService.findAllUsers();
		assertEquals(list.size(), entityList.size());
	}

	@Test
	public void testFindAllUsersNegative() {
		List<User> list1 = new ArrayList<User>();
		list1.add(user);
		when(userRepository.findAll()).thenReturn(list1);
		List<User> entityList1 = userService.findAllUsers();
		assertEquals(list1.size(), entityList1.size());
	}
}
