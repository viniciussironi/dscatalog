package com.vinicius.dscatalog.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.entities.Category;
import com.vinicius.dscatalog.entities.Product;
import com.vinicius.dscatalog.factories.Factory;
import com.vinicius.dscatalog.repositories.CategoryRepository;
import com.vinicius.dscatalog.repositories.ProductRepository;
import com.vinicius.dscatalog.services.exceptions.DatabaseException;
import com.vinicius.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private CategoryRepository catRepository;
	@Mock
	private ProductRepository repository;
	
	private long existsId;
	private long notExistsId;
	private long dependentId;
	private Product product;
	private Category category;
	private PageImpl<Product> page;
	private ProductDTO dto;
	@BeforeEach
	void setUp() {
		existsId = 1L;
		notExistsId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		dto = Factory.createProductDTO();
		page = new PageImpl<>(List.of(product));
		
		when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		when(repository.findById(existsId)).thenReturn(Optional.of(product));
		when(repository.findById(notExistsId)).thenReturn(Optional.empty());
		
		when(repository.existsById(existsId)).thenReturn(true); // Importante para não retornar "ResourceNotFoundException";
		when(repository.existsById(notExistsId)).thenReturn(false);
		when(repository.existsById(dependentId)).thenReturn(true);
		
		when(repository.getReferenceById(existsId)).thenReturn(product);
		when(repository.getReferenceById(notExistsId)).thenThrow(EntityNotFoundException.class);
		
		when(catRepository.getReferenceById(existsId)).thenReturn(category);
		when(catRepository.getReferenceById(notExistsId)).thenThrow(EntityNotFoundException.class);
		
		doNothing().when(repository).deleteById(existsId);
		//Vazio^^
		doThrow(DatabaseException.class).when(repository).deleteById(dependentId);
	}
	
	@Test
	public void updateShouldReturnProductWhenIdExists() {			
		service.update(existsId, dto);
		Assertions.assertNotNull(dto);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(notExistsId, dto);
		});
	}
	
	@Test
	public void findAllShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<ProductDTO> page = service.findAll(pageable);
		Assertions.assertNotNull(page);
		
		verify(repository, times(1)).findAll(pageable);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO productDto = service.findById(existsId);
		Assertions.assertNotNull(productDto);
		verify(repository, times(1)).findById(existsId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(notExistsId);
		});
		verify(repository, times(1)).findById(notExistsId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existsId);			
		});
		verify(repository, times(1)).deleteById(existsId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(notExistsId);
		});
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependedId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
	}
}
