package com.test.container.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.test.container.domain.User;
import com.test.container.repository.UserRepository;

@Testcontainers//integration testing-replicating the test container,all layers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest1 {

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
			.withDatabaseName("test").withUsername("testuser").withPassword("testpass");

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	private User testUser;

	@BeforeEach
	void setUp() {
		User user1 = new User(1, "Satheesh", "Chepuri", "Venkata");
		User user2 = new User(2, "Praneetha", "Chepuri", "Lakshmi");
		testUser = new User(1, "Satheesh", "Chepuri", "Venkata");
		userRepository.save(testUser);
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void testCreateUser() {
		User newUser = new User(1, "Satheesh", "Chepuri", "Venkata");
		ResponseEntity<User> response = restTemplate.postForEntity("/svc/user/v1/create", newUser, User.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		// assertEquals(newUser.getFirstName(), response.getBody().getFirstName());
		// assertEquals(newUser.getLastName(), response.getBody().getLastName());
	}

	@Test
	void testUpdateUser() {
		testUser.setFirstName("Geethika");
		testUser.setLastName("Chepuri");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> requestEntity = new HttpEntity<>(testUser, headers);
		ResponseEntity<User> response = restTemplate.exchange("/svc/user/v1/update", HttpMethod.PUT, requestEntity,
				User.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		// assertEquals(testUser.getFirstName(), response.getBody().getFirstName());
		// assertEquals(testUser.getLastName(), response.getBody().getLastName());
	}

	@Test
	void testDeleteUser() {
		restTemplate.delete("/user/{id}", testUser.getId());
		assertTrue(userRepository.existsById(testUser.getId()));
	}

	@Test
	public void testGetAllUsers() {
		User user1 = new User(1, "Satheesh", "Chepuri", "Venkata");
		User user2 = new User(2, "Praneetha", "Chepuri", "Lakshmi");

		userRepository.save(user1);
		userRepository.save(user2);

		ResponseEntity<List<User>> responseEntity = restTemplate.exchange("/svc/user/v1/findAllUsers", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<User>>() {
				});

		List<User> users = responseEntity.getBody();
		System.out.println("users------>" + users);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, users.size());
	}

	@Test
	void testGetUser() {
		// Given
		testUser.setFirstName("Geethika");
		testUser.setLastName("Chepuri");
		userRepository.save(testUser);

		// When
		ResponseEntity<User> response = restTemplate.getForEntity("/svc/user/v1/findById/{id}", User.class,
				testUser.getId());

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(testUser.getId(), response.getBody().getId());
		assertEquals(testUser.getFirstName(), response.getBody().getFirstName());
		assertEquals(testUser.getLastName(), response.getBody().getLastName());
	}
}
