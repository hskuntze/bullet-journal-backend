package com.hkprojects.BulletJournal.services;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.hkprojects.BulletJournal.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthServiceTests {
	@Autowired
	private TokenUtil tk;

	@Autowired
	private MockMvc mockMvc;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	@Test
	public void authenticationShouldReturnTokenWhenValidCredentials() throws Exception {
		String token = tk.obtainAccessToken(mockMvc, "Bob Black", "123456");
		Assertions.assertNotNull(token);
	}

	@Test
	public void authenticationShouldReturnCode400WhenInvalidCredentials() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", clientId);
		params.add("username", "Bob Black");
		params.add("password", "1234");

		mockMvc.perform(post("/oauth/token").params(params).with(httpBasic(clientId, clientSecret))
				.accept("application/json;charset=UTF-8"))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
}
