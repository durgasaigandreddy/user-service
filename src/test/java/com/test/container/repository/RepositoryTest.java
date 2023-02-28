package com.test.container.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.test.container.domain.User;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class RepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Test
	void testSaveUser() {
		User user = new User();
		user.setId(1);
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setMiddleName("sam");
		userRepository.save(user);

		User savedUser = userRepository.findById(user.getId()).orElse(null);

		assertNotNull(savedUser);
		assertEquals(user.getFirstName(), savedUser.getFirstName());
		assertEquals(user.getLastName(), savedUser.getLastName());
		assertEquals(user.getMiddleName(), savedUser.getMiddleName());
	}

	@Test
	void testFindUserById() {
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setMiddleName("sam");
		userRepository.save(user);

		User foundUser = userRepository.findById(user.getId()).orElse(null);

		assertNotNull(foundUser);
		assertEquals(user.getId(), foundUser.getId());
		assertEquals(user.getFirstName(), foundUser.getFirstName());
		assertEquals(user.getLastName(), foundUser.getLastName());
		assertEquals(user.getMiddleName(), foundUser.getMiddleName());
	}

	@Test
	void testUpdateUser() {
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setMiddleName("sam");
		userRepository.save(user);

		user.setFirstName("Jane");
		userRepository.save(user);

		User updatedUser = userRepository.findById(user.getId()).orElse(null);

		assertNotNull(updatedUser);
		assertEquals(user.getId(), updatedUser.getId());
		assertEquals(user.getFirstName(), updatedUser.getFirstName());
		assertEquals(user.getLastName(), updatedUser.getLastName());
		assertEquals(user.getMiddleName(), updatedUser.getMiddleName());
	}

	@Test
	void testDeleteUser() {
		User user = new User();
		user.setId(2);
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setMiddleName("sam");
		userRepository.save(user);

		userRepository.delete(user);

		User deletedUser = userRepository.findById(user.getId()).orElse(null);

		assertNull(deletedUser);
	}
}