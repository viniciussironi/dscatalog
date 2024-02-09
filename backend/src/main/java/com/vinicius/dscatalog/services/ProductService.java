package com.vinicius.dscatalog.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.entities.Product;
import com.vinicius.dscatalog.projections.ProductProjection;
import com.vinicius.dscatalog.repositories.CategoryRepository;
import com.vinicius.dscatalog.repositories.ProductRepository;
import com.vinicius.dscatalog.services.exceptions.DatabaseException;
import com.vinicius.dscatalog.services.exceptions.ResourceNotFoundException;
import com.vinicius.dscatalog.util.Utils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> page = repository.findAll(pageable);
		return page.map(x -> new ProductDTO(x, x.getCategories()));
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPageable(String name, String categoryId, Pageable pageable) {
		List<Long> categoryList = Arrays.asList();
		if(!"0".equals(categoryId)) {
			categoryList = Arrays.asList(categoryId.split(",")).stream().map(Long::parseLong).toList();
		}
		Page<ProductProjection> page = repository.searchProducts(categoryList, name, pageable);
		List<Product> entities = repository.searchProductsWithCategories(page.map(x -> x.getId()).toList());
		entities = (List<Product>) Utils.replace(page.getContent(), entities);
		List<ProductDTO> dtos = entities.stream().map(x -> new ProductDTO(x, x.getCategories())).toList();
		return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encotrado"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		dtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id); // Evita duas idas no banco
			dtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Produto não encotrado");
		}	
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) {
			throw new ResourceNotFoundException("Produto não encotrado");
		}
		try {
			repository.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade");
		}
	}
	
	public void dtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setPrice(dto.getPrice());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		
		entity.getCategories().clear();
		dto.getCategories().forEach(x -> entity.getCategories().add(categoryRepository.getReferenceById(x.getId())));
	}
}
