package com.hkprojects.bulletjournal.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkprojects.bulletjournal.entities.Card;
import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.entities.dto.CardDTO;
import com.hkprojects.bulletjournal.repositories.CardRepository;
import com.hkprojects.bulletjournal.repositories.UserRepository;
import com.hkprojects.bulletjournal.services.exceptions.ResourceNotFoundException;

@Service
public class CardService {
	
	@Autowired
	private CardRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public Page<CardDTO> findAll(Pageable pageable){
		User user = authService.authenticated();
		Page<Card> page = repository.findByUsername(user.getUsername(), pageable);
		return page.map(x -> new CardDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<CardDTO> findAllWithFilters(Pageable pageable, String title){
		String username = authService.authenticated().getUsername();
		Page<Card> all = repository.findByUsernameWithFilters(username, title, pageable);
		return all.map(x -> new CardDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CardDTO findById(Long id) {
		Optional<Card> obj = repository.findById(id);
		Card card = obj.orElseThrow(() -> new ResourceNotFoundException("Card com id "+id+" não foi localizado."));
		return new CardDTO(card);
	}
	
	@Transactional
	public CardDTO insert(CardDTO dto) {
		Card obj = new Card();
		dtoToEntity(obj, dto);
		obj = repository.save(obj);
		return new CardDTO(obj);
	}
	
	@Transactional
	public CardDTO update(Long id, CardDTO dto) {
		try {
			Card obj = repository.getOne(id);
			dtoToEntity(obj, dto);
			obj = repository.save(obj);
			return new CardDTO(obj);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Não foi possível encontrar um Card com id "+id+". Mais informações: "+e.getMessage());
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possível localizar um objeto Card com id " +id);
		}
	}
	
	private void dtoToEntity(Card entity, CardDTO dto) {
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		User u = userRepository.findById(authService.authenticated().getId()).get();
		entity.setUser(u);
	}
}
