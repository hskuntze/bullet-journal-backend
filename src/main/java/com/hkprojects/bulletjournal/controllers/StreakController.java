package com.hkprojects.bulletjournal.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hkprojects.bulletjournal.entities.dto.StreakDTO;
import com.hkprojects.bulletjournal.entities.swagger.StreakSchema;
import com.hkprojects.bulletjournal.services.StreakService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/streaks")
@SecurityRequirement(name = "thebulletjournal-doc-scheme")
public class StreakController {

	@Autowired
	private StreakService service;

//	@GetMapping
//	public ResponseEntity<Page<StreakDTO>> findAll(Pageable pageable){
//		return ResponseEntity.ok().body(service.findAll(pageable));
//	}

	@GetMapping
	@Operation(tags = { "/streaks" }, parameters = {
			@Parameter(name = "title", description = "Título para filtrar objetos Streak", example = "Estudar") })
	public ResponseEntity<Page<StreakDTO>> findAll(Pageable pageable,
			@RequestParam(value = "title", defaultValue = "") String title) {
		Page<StreakDTO> page = service.findAllWithFilters(title, pageable);
		return ResponseEntity.ok().body(page);
	}

	@GetMapping(value = "/{id}")
	@Operation(tags = { "/streaks" }, parameters = {
			@Parameter(name = "id", description = "Id de um objeto Streak", example = "2") })
	public ResponseEntity<StreakDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	@Operation(tags = { "/streaks" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
			@Content(schema = @Schema(implementation = StreakSchema.class)) }, description = "Objeto de entrada para inserir um Streak no sistema", required = true))
	public ResponseEntity<StreakDTO> insert(@RequestBody StreakDTO dto) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		dto = service.insert(dto);
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	@Operation(tags = { "/streaks" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
			@Content(schema = @Schema(implementation = StreakSchema.class)) }, description = "Objeto de entrada para atualizar um Streak no sistema", required = true), parameters = {
					@Parameter(name = "id", description = "Id de um objeto Streak", example = "1") })
	public ResponseEntity<StreakDTO> update(@PathVariable Long id, @RequestBody StreakDTO dto) {
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	
	@PutMapping(value = "/updateCount/{id}")
	@Operation(tags = { "/streaks" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
			@Content(schema = @Schema(implementation = StreakSchema.class)) }, description = "Objeto de entrada para atualizar o contador, em 1 unidade, de um Streak específico no sistema", required = true), parameters = {
					@Parameter(name = "id", description = "Id de um objeto Streak", example = "1") })
	public ResponseEntity<StreakDTO> updateCountByOne(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.updateCountByOne(id));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(tags = { "/streaks" }, parameters = {
			@Parameter(name = "id", description = "Id de um objeto Streak", example = "1") })
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
