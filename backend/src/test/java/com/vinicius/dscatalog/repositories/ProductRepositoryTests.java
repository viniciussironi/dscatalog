package com.vinicius.dscatalog.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.vinicius.dscatalog.entities.Product;
import com.vinicius.dscatalog.factories.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long notExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		notExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void getShouldGetObjectWhenIdExists() {
		Assertions.assertFalse(repository.findById(existingId).isEmpty());
	}
	
	@Test
	public void getShouldGetObjectWhenIdNotExists() {
		Assertions.assertTrue(repository.findById(notExistingId).isEmpty());
	}
	
	@Test
	public void saveShouldPersistAutoIncrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.getCategories().add(Factory.createCategory());
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1L, product.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(existingId);
		Assertions.assertFalse(repository.findById(1L).isPresent());
	}
}
