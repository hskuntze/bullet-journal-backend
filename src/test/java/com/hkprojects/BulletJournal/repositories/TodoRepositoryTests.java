package com.hkprojects.BulletJournal.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.hkprojects.BulletJournal.tests.Factory;
import com.hkprojects.bulletjournal.entities.Todo;
import com.hkprojects.bulletjournal.repositories.TodoRepository;

@DataJpaTest
public class TodoRepositoryTests {
	
	@Autowired
	private TodoRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalTodos;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2517L;
		countTotalTodos = 17L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrement() {
		Todo todo = Factory.createTodo();
		todo.setId(null);
		todo = repository.save(todo);
		
		Assertions.assertEquals(countTotalTodos + 1, todo.getId());
	}
	
	@Test
	public void deleteShoudDeleteWhenIdExists() {
		repository.deleteById(existingId);
		
		Assertions.assertTrue(repository.findById(existingId).isEmpty());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesntExists() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		Optional<Todo> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyWhenIdDoesntExists() {
		Optional<Todo> result = repository.findById(nonExistingId);
		
		Assertions.assertTrue(result.isEmpty());
	}
}
