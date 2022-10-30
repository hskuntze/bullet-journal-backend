package com.hkprojects.BulletJournal.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.hkprojects.BulletJournal.tests.Factory;
import com.hkprojects.bulletjournal.entities.Streak;
import com.hkprojects.bulletjournal.repositories.StreakRepository;

@DataJpaTest
public class StreakRepositoryTests {
	@Autowired
	private StreakRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalStreaks;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2517L;
		countTotalStreaks = 2L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrement() {
		Streak streak = Factory.createStreak();
		streak.setId(null);
		streak = repository.save(streak);
		
		Assertions.assertEquals(countTotalStreaks + 1, streak.getId());
	}
	
	@Test
	public void deleteShouldDeleteWhenIdExists() {
		repository.deleteById(existingId);
		
		Optional<Streak> streak = repository.findById(existingId);
		Assertions.assertTrue(streak.isEmpty());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesntExists() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		Optional<Streak> streak = repository.findById(existingId);
		
		Assertions.assertTrue(streak.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyWhenIdDoesntExists() {
		Optional<Streak> streak = repository.findById(nonExistingId);
		
		Assertions.assertTrue(streak.isEmpty());
	}
}
