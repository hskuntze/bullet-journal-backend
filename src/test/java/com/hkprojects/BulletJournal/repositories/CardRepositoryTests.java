package com.hkprojects.BulletJournal.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.hkprojects.BulletJournal.tests.Factory;
import com.hkprojects.bulletjournal.entities.Card;
import com.hkprojects.bulletjournal.repositories.CardRepository;

@DataJpaTest
public class CardRepositoryTests {
	@Autowired
	private CardRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalCards;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 157L;
		countTotalCards = 4L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrement() {
		Card card = Factory.createCard();
		card.setId(null);
		card = repository.save(card);
		
		Assertions.assertNotNull(card.getId());
		Assertions.assertEquals(countTotalCards + 1, card.getId());
	}
	
	@Test
	public void deleteShouldDeleteWhenIdNotNull() {
		repository.deleteById(existingId);
		
		Optional<Card> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesntExists() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		Optional<Card> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals("Dia 07/10", result.get().getTitle());
	}
	
	@Test
	public void findByIdShouldReturnEmptyWhenIdDoesntExists() {
		Optional<Card> result = repository.findById(nonExistingId);
		
		Assertions.assertTrue(result.isEmpty());
	}
}
