package com.hkprojects.bulletjournal.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkprojects.bulletjournal.entities.Streak;
import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.entities.dto.StreakDTO;
import com.hkprojects.bulletjournal.repositories.StreakRepository;
import com.hkprojects.bulletjournal.repositories.UserRepository;
import com.hkprojects.bulletjournal.services.exceptions.ResourceNotFoundException;

@Service
public class StreakService {
	
	@Autowired
	private StreakRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public Page<StreakDTO> findAll(Pageable pageable){
		String username = authService.authenticated().getUsername();
		Page<Streak> page = repository.findByUsername(username, pageable);
		return page.map(x -> new StreakDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<StreakDTO> findAllWithFilters(String title, Pageable pageable){
		String username = authService.authenticated().getUsername();
		Page<Streak> page = repository.findByUsernameWithFilters(title, username, pageable);
		return page.map(x -> new StreakDTO(x));
	}
	
	@Transactional(readOnly = true)
	public StreakDTO findById(Long id) {
		Optional<Streak> obj = repository.findById(id);
		Streak streak = obj.orElseThrow(() -> new ResourceNotFoundException("Streak de id "+id+" não foi localizado."));
		return new StreakDTO(streak);
	}
	
	@Transactional
	public StreakDTO insert(StreakDTO dto) {
		Streak entity = new Streak();
		dtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new StreakDTO(entity);
	}
	
	@Transactional
	public StreakDTO update(Long id, StreakDTO dto) {
		try {
			Streak entity = repository.getOne(id);
			dtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new StreakDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Não foi possível encontrar um Streak com id "+id+". Mais informações: "+e.getMessage());
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possível localizar um objeto Streak com id "+id);
		}
	}
	
	private void dtoToEntity(StreakDTO dto, Streak entity) {
		entity.setTitle(dto.getTitle());
		entity.setTotal(dto.getTotal());
		entity.setCount(dto.getCount());
		entity.setDisabled(dto.isDisabled());
		entity.setTotalPerLabel(dto.getTotalPerLabel());
		entity.setLabel(dto.getLabel());
		entity.setLast(dto.getLast());
		User u = userRepository.findById(authService.authenticated().getId()).get();
		entity.setUser(u);
	}
}
