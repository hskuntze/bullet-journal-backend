package com.hkprojects.bulletjournal.controllers;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hkprojects.bulletjournal.controllers.utils.OnRegistrationCompleteEvent;
import com.hkprojects.bulletjournal.entities.dto.UserDTO;
import com.hkprojects.bulletjournal.entities.dto.UserInsertDTO;
import com.hkprojects.bulletjournal.services.UserService;
import com.hkprojects.bulletjournal.services.exceptions.UserAlreadyExistsException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@GetMapping
	@SecurityRequirement(name = "thebulletjournal-doc-scheme")
	@Operation(tags = { "/users" })
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	@SecurityRequirement(name = "thebulletjournal-doc-scheme")
	@Operation(tags = { "/users" })
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	@Operation(tags = {
			"/users" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true))
	public ResponseEntity<UserDTO> insert(@RequestBody UserInsertDTO obj, HttpServletRequest request, Errors errors)
			throws MalformedURLException {
		try {
			UserDTO user = service.register(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
					.toUri();
			String aux = request.getRequestURL().toString();
			String appUrl = aux.substring(0, StringUtils.ordinalIndexOf(aux, "/", 3));
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
			return ResponseEntity.created(uri).body(user);
		} catch (UserAlreadyExistsException e) {
			throw new UserAlreadyExistsException("Nome de usuário e/ou e-mail já existem.");
		}
	}

	@DeleteMapping(value = "/{id}")
	@SecurityRequirement(name = "thebulletjournal-doc-scheme")
	@Operation(tags = { "/users" })
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
