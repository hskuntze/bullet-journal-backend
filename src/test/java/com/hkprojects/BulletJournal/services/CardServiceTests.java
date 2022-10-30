package com.hkprojects.BulletJournal.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hkprojects.BulletJournal.tests.Factory;
import com.hkprojects.bulletjournal.entities.Card;
import com.hkprojects.bulletjournal.entities.dto.CardDTO;
import com.hkprojects.bulletjournal.repositories.CardRepository;
import com.hkprojects.bulletjournal.services.CardService;
import com.hkprojects.bulletjournal.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class CardServiceTests {
	
	@InjectMocks
	private CardService service;
	
	@Mock
	private CardRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private PageImpl<Card> page;
	private Card card;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 516L;
		card = Factory.createCard();
		page = new PageImpl<>(List.of(card));
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		
		Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
		Mockito.when(repository.findByUsernameWithFilters(any(), any(), any())).thenReturn(page);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(card));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.getOne(existingId)).thenReturn(card);
		Mockito.when(repository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesntExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void findByIdShouldReturnDTOObjectWhenIdExists() {
		CardDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).findById(existingId);
	}
	
	@Test
	public void findByIdShouldThrowEntityNotFoundExceptionWhenIdDoesnExists() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}
}
