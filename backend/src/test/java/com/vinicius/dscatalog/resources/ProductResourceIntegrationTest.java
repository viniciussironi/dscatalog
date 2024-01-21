package com.vinicius.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.factories.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private long existsId;
	private long notExistsId;
	private long countTotalProducts;
	private ProductDTO productDto;
	
	@BeforeEach
	void setUp() {
		existsId = 1L;
		notExistsId = 1000L;
		countTotalProducts = 25L;
		productDto = Factory.createProductDTO();
	}
	
	@Test
	public void findAllShouldReturnPageWhenSortByName() throws Exception {
		mockMvc.perform(get("/products?page=0&size=10&sort=name,asc"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalElements").value(countTotalProducts))
		.andExpect(jsonPath("$.content").exists())
		.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));	
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
		String json = objectMapper.writeValueAsString(productDto);
		
		mockMvc.perform(put("/products/{id}", existsId)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(productDto.getName()));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdNotExists() throws Exception {
		String json = objectMapper.writeValueAsString(productDto);
		
		mockMvc.perform(put("/products/{id}", notExistsId)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
