package com.hkprojects.BulletJournal.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkprojects.BulletJournal.tests.Factory;
import com.hkprojects.BulletJournal.tests.TokenUtil;
import com.hkprojects.bulletjournal.entities.dto.StreakDTO;
import com.hkprojects.bulletjournal.services.StreakService;
import com.hkprojects.bulletjournal.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class StreakControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StreakService service;
	
	@Autowired
	private ObjectMapper objMapper;
	
	@Autowired
	private TokenUtil tk;

	private Long existingId;
	private Long nonExistingId;
	private StreakDTO streakDto;
	private PageImpl<StreakDTO> page;
	private String username;
	private String password;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 999L;
		streakDto = Factory.createStreakDTO();
		page = new PageImpl<>(List.of(streakDto));
		username = "Bob Black";
		password = "123456";
		
		Mockito.when(service.findAllWithFilters(any(), any())).thenReturn(page);
		Mockito.when(service.findById(existingId)).thenReturn(streakDto);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

		Mockito.when(service.update(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(streakDto);
		Mockito.when(service.update(ArgumentMatchers.eq(nonExistingId), ArgumentMatchers.any()))
				.thenThrow(ResourceNotFoundException.class);

		Mockito.doNothing().when(service).delete(existingId);
		Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);

		Mockito.when(service.insert(ArgumentMatchers.any())).thenReturn(streakDto);
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		String token = tk.obtainAccessToken(mockMvc, username, password);

		mockMvc.perform(get("/streaks").header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void findAllShouldReturnUnauthorizedWhenNoTokenIsProvided() throws Exception {
		mockMvc.perform(get("/streaks").accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	public void findByIdShouldReturnDTOObjectWhenIdExists() throws Exception {
		String token = tk.obtainAccessToken(mockMvc, username, password);

		mockMvc.perform(get("/streaks/{id}", existingId).header("Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
	}

	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		String token = tk.obtainAccessToken(mockMvc, username, password);

		mockMvc.perform(get("/streaks/{id}", nonExistingId).header("Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void findByIdShouldReturnUnauthorizedWhenNoTokenIsProvided() throws Exception {
		mockMvc.perform(get("/streaks/{id}", existingId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void updateShouldReturnDTOObjectWhenIdExists() throws Exception {
		String token = tk.obtainAccessToken(mockMvc, username, password);

		String jsonBody = objMapper.writeValueAsString(streakDto);

		mockMvc.perform(put("/streaks/{id}", existingId).header("Authorization", "Bearer " + token).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists());
	}

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		String accessToken = tk.obtainAccessToken(mockMvc, username, password);

		String jsonBody = objMapper.writeValueAsString(streakDto);

		mockMvc.perform(put("/streaks/{id}", nonExistingId).header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateShouldReturnUnauthorizedWhenNoTokenIsProvided() throws Exception {
		String jsonBody = objMapper.writeValueAsString(streakDto);

		mockMvc.perform(put("/streaks/{id}", nonExistingId).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	public void insertShouldReturnCreatedAndDTOObjectWhenSuccess() throws Exception {
		String accessToken = tk.obtainAccessToken(mockMvc, username, password);

		String jsonBody = objMapper.writeValueAsString(streakDto);

		mockMvc.perform(post("/streaks").header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void insertShouldReturnUnauthorizedWhenNoTokenIsProvided() throws Exception {
		String jsonBody = objMapper.writeValueAsString(streakDto);

		mockMvc.perform(post("/streaks").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
		String accessToken = tk.obtainAccessToken(mockMvc, username, password);

		ResultActions result = mockMvc
				.perform(delete("/streaks/{id}", existingId).header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		String accessToken = tk.obtainAccessToken(mockMvc, username, password);

		ResultActions result = mockMvc
				.perform(delete("/streaks/{id}", nonExistingId).header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void deleteShouldReturnUnauthorizedWhenNoTokenIsProvided() throws Exception {
		ResultActions result = mockMvc.perform(delete("/streaks/{id}", existingId));

		result.andExpect(status().isUnauthorized());
	}
}
