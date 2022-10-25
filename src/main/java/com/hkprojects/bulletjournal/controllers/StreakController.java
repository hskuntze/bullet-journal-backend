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
import com.hkprojects.bulletjournal.services.StreakService;

@RestController
@RequestMapping(value = "/streaks")
public class StreakController {
	
	@Autowired
	private StreakService service;
	
//	@GetMapping
//	public ResponseEntity<Page<StreakDTO>> findAll(Pageable pageable){
//		return ResponseEntity.ok().body(service.findAll(pageable));
//	}
	
	@GetMapping
	public ResponseEntity<Page<StreakDTO>> findAll(Pageable pageable,
			@RequestParam(value = "title", defaultValue="") String title){
		Page<StreakDTO> page = service.findAllWithFilters(title, pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<StreakDTO> findById(@PathVariable Long id){
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<StreakDTO> insert(@RequestBody StreakDTO dto){
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		dto = service.insert(dto);
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<StreakDTO> update(@PathVariable Long id, @RequestBody StreakDTO dto){
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
