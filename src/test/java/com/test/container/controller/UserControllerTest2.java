package com.test.container.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.container.domain.User;
import com.test.container.service.UserService;

@ExtendWith(MockitoExtension.class) // junit testing
public class UserControllerTest2 {
	@Mock
	private UserService userService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	void testGetUser() throws Exception {
		User user = new User();
		user.setId(1);
		user.setFirstName("John");
		user.setLastName("AdduRoad");
		Optional<User> optionalUser = Optional.ofNullable(user);
		when(userService.findByUserId(1)).thenReturn(optionalUser);
		mockMvc.perform(get("/svc/user/v1/findById/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName", is("AdduRoad"))).andExpect(jsonPath("$.firstName", is("John")));
	}

	@Test
	void testCreateUser() throws Exception {
		User user = new User();
		user.setId(1);
		user.setFirstName("John");
		user.setLastName("AdduRoad");

		when(userService.createUser(any(User.class))).thenReturn(user);

		mockMvc.perform(post("/svc/user/v1/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John")));
	}

	@Test
	void testUpdateTask() throws Exception {
		User user = new User();
		user.setId(1);
		user.setFirstName("John");
		user.setLastName("AdduRoad");

		when(userService.updateUser(any(User.class))).thenReturn(user);

		mockMvc.perform(put("/svc/user/v1/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John")));
	}

	@Test
	void testDeleteTask() throws Exception {
		mockMvc.perform(delete("/svc/user/v1/delete/{Id}", 1)).andExpect(status().isOk()).andReturn();
	}

}
