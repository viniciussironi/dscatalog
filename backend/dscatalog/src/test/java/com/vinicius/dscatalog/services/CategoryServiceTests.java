package com.vinicius.dscatalog.services;

import java.util.ArrayList;
import java.util.List;

import com.vinicius.dscatalog.dtos.CategoryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vinicius.dscatalog.entities.Category;
import com.vinicius.dscatalog.factories.Factory;
import com.vinicius.dscatalog.repositories.CategoryRepository;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
	
	@InjectMocks
	private CategoryService service;
	@Mock
	private CategoryRepository repository;
	
	private Category category;
	private List<Category> list;
	private PageImpl<Category> page;
	
	@BeforeEach
	void setUp() throws Exception {
		category = Factory.createCategory();
		list = new ArrayList<>();
		list.add(category);
		page = new PageImpl<>(list);

		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
	}

	@Test
	public void findAllShouldReturnPageCategoryDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<CategoryDTO> result = service.findAll(pageable);

		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.getContent().get(0).getId(), category.getId());
		Assertions.assertEquals(result.getContent().get(0).getName(), category.getName());
	}
}
