package com.vinicius.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vinicius.dscatalog.dtos.CategoryDTO;
import com.vinicius.dscatalog.entities.Category;
import com.vinicius.dscatalog.repositories.CategoryRepository;
import com.vinicius.dscatalog.services.exceptions.DatabaseException;
import com.vinicius.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(Pageable pageable) {
		Page<Category> page = repository.findAll(pageable);
		return page.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return new CategoryDTO(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada")));
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getReferenceById(id); // Evita duas idas no banco
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Categoria não encotrada");
		}	
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Categoria não encotrada");
		}
		try {
			repository.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade");
		}
	}
}
