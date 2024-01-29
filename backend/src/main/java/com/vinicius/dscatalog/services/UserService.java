package com.vinicius.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vinicius.dscatalog.dtos.UserDTO;
import com.vinicius.dscatalog.dtos.UserInsertDTO;
import com.vinicius.dscatalog.dtos.UserUpdateDTO;
import com.vinicius.dscatalog.entities.User;
import com.vinicius.dscatalog.repositories.RoleRepository;
import com.vinicius.dscatalog.repositories.UserRepository;
import com.vinicius.dscatalog.services.exceptions.DatabaseException;
import com.vinicius.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository repository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(Pageable pageable) {
		Page<User> page = repository.findAll(pageable);
		return page.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encotrado"));
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		dtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			User entity = repository.getReferenceById(id); // Evita duas idas no banco
			dtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuário não encotrado");
		}	
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Usuário não encotrado");
		}
		try {
			repository.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade");
		}
	}
	
	public void dtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		dto.getRoles().forEach(x -> entity.getRoles().add(roleRepository.getReferenceById(x.getId())));
	}
}
