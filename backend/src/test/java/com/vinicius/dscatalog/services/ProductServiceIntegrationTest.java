package com.vinicius.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.repositories.ProductRepository;
import com.vinicius.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {
	
	@Autowired
	private ProductService service;
	@Autowired
	private ProductRepository repository;
	
	private long existsId;
	private long notExistsId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() {
		existsId = 1L;
		notExistsId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void deleteShoudDeleteResourceWhenIdExists() {
		service.delete(existsId);		
		Assertions.assertEquals(countTotalProducts - 1L, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(notExistsId);
		});
	}
	
	@Test
	public void findAllShouldReturnPageWhenPage0Size10() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<ProductDTO> page = service.findAll(pageRequest);
		
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals(10, page.getSize());
		Assertions.assertEquals(0, page.getNumber());
		Assertions.assertEquals(countTotalProducts, page.getTotalElements());
	}
}
