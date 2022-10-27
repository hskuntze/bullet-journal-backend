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

import com.hkprojects.bulletjournal.entities.dto.TodoDTO;
import com.hkprojects.bulletjournal.entities.swagger.TodoSchema;
import com.hkprojects.bulletjournal.services.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/todos")
@SecurityRequirement(name = "thebulletjournal-doc-scheme")
public class TodoController {
	@Autowired
	private TodoService service;

//	@GetMapping
//	public ResponseEntity<Page<TodoDTO>> findAll(Pageable pageable){
//		return ResponseEntity.ok().body(service.findAll(pageable));
//	}

	@GetMapping
	@Operation(tags = { "/todos" }, parameters = {
			@Parameter(name = "title", description = "Título para filtrar objetos Todo.", example = "Lorem"),
			@Parameter(name = "priority", description = "Nível de prioridade para filtrar objetos Todo.", example = "high") })
	public ResponseEntity<Page<TodoDTO>> findAll(Pageable pageable,
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "priority", defaultValue = "") String priority) {
		Page<TodoDTO> page = service.findAllWithFilters(pageable, title, priority);
		return ResponseEntity.ok().body(page);
	}

	@GetMapping(value = "/{id}")
	@Operation(tags = {
			"/todos" }, parameters = @Parameter(name = "id", description = "Id de um objeto Todo", example = "1"))
	public ResponseEntity<TodoDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	@Operation(tags = {
			"/todos" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
					@Content(schema = @Schema(implementation = TodoSchema.class)) }, description = "Objeto de entrada para inserir um Todo."))
	public ResponseEntity<TodoDTO> insert(@RequestBody TodoDTO dto) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(service.insert(dto));
	}

	@PutMapping(value = "/{id}")
	@Operation(tags = {
			"/todos" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
					@Content(schema = @Schema(implementation = TodoSchema.class)) }, description = "Objeto de entrada para atualizar um Todo."), parameters = @Parameter(name = "id", description = "Id de um objeto Todo.", example = "4"))
	public ResponseEntity<TodoDTO> update(@PathVariable Long id, @RequestBody TodoDTO dto) {
		return ResponseEntity.ok().body(service.update(id, dto));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(tags = {
			"/todos" }, parameters = @Parameter(name = "id", description = "Id de um usuário", example = "1"))
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
