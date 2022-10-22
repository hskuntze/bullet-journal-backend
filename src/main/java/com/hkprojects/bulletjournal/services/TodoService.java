package com.hkprojects.bulletjournal.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkprojects.bulletjournal.entities.Todo;
import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.entities.dto.TodoDTO;
import com.hkprojects.bulletjournal.repositories.TodoRepository;
import com.hkprojects.bulletjournal.repositories.UserRepository;
import com.hkprojects.bulletjournal.services.exceptions.ResourceNotFoundException;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public Page<TodoDTO> findAll(Pageable pageable){
		Page<Todo> all = repository.findByUsername(authService.authenticated().getUsername(), pageable);
		return all.map(x -> new TodoDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<TodoDTO> findAllWithFilters(Pageable pageable, String name, String priority){
		String username = authService.authenticated().getUsername();
		Page<Todo> all = repository.findByUsernameWithFilters(name, priority, username, pageable);
		return all.map(x -> new TodoDTO(x));
	}
	
	@Transactional(readOnly = true)
	public TodoDTO findById(Long id) {
		Optional<Todo> obj = repository.findById(id);
		return new TodoDTO(obj.get());
	}
	
	@Transactional
	public TodoDTO insert(TodoDTO dto) {
		Todo todo = new Todo();
		dtoToEntity(todo, dto);
		todo = repository.save(todo);
		return new TodoDTO(todo);
	}
	
	@Transactional
	public TodoDTO update(Long id, TodoDTO dto) {
		try {
			Todo todo = repository.getOne(id);
			dtoToEntity(todo, dto);
			todo = repository.save(todo);
			return new TodoDTO(todo);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entidade: "+this.getClass()+". Não foi possível encontrar o objeto com id "+id+". Mais informações: "+e.getMessage());
		}
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	private void dtoToEntity(Todo entity, TodoDTO dto) {
		entity.setTitle(dto.getTitle());
		entity.setDone(dto.isDone());
		entity.setPriority(dto.getPriority());
		User u = userRepository.findById(authService.authenticated().getId()).get();
		entity.setUser(u);
	}
}
