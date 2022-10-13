package com.hkprojects.bulletjournal.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkprojects.bulletjournal.entities.Role;
import com.hkprojects.bulletjournal.entities.dto.RoleDTO;
import com.hkprojects.bulletjournal.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;
	
	@Transactional(readOnly = true)
	private List<RoleDTO> findAll(){
		List<Role> list = repository.findAll();
		return list.stream().map(x -> new RoleDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	private RoleDTO findById(Long id) {
		return new RoleDTO(repository.findById(id).get());
	}
}
