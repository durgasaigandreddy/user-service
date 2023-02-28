package com.test.container.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import com.test.container.domain.User;
import com.test.container.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

	@SuppressWarnings("rawtypes")
	private static PostgreSQLContainer postgresqlContainer;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@SuppressWarnings({ "rawtypes", "resource" })
	@BeforeAll
	public static void setUp() {
		postgresqlContainer = new PostgreSQLContainer("postgres:13").withDatabaseName("test").withUsername("testuser")
				.withPassword("testpass");

		postgresqlContainer.start();
	}

	@AfterAll
	public static void tearDown() {
		postgresqlContainer.stop();
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
}
