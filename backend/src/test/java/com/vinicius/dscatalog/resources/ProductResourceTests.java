package com.vinicius.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.factories.Factory;
import com.vinicius.dscatalog.services.ProductService;
import com.vinicius.dscatalog.services.exceptions.DatabaseException;
import com.vinicius.dscatalog.services.exceptions.ResourceNotFoundException;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductService service;
	
	private PageImpl<ProductDTO> page;
	private ProductDTO productDto;
	private long existsId;
	private long notExistsId;
	private long dependentId;
	
	@BeforeEach
	void setUp() {
		productDto = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDto));
		existsId = 1L;
		notExistsId = 2L;
		dependentId = 3L;
		
		when(service.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		when(service.findById(existsId)).thenReturn(productDto);
		when(service.findById(notExistsId)).thenThrow(ResourceNotFoundException.class);
		
		when(service.update(eq(existsId), any())).thenReturn(productDto);
		when(service.update(eq(notExistsId), any())).thenThrow(ResourceNotFoundException.class);
		
		doNothing().when(service).delete(existsId);
		doThrow(ResourceNotFoundException.class).when(service).delete(notExistsId);
		doThrow(DatabaseException.class).when(service).delete(dependentId);
		
		when(service.insert(any())).thenReturn(productDto);
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() throws Exception {
		mockMvc.perform(get("/products/{id}", existsId)).andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdNotExists() throws Exception {
		mockMvc.perform(get("/products/{id}", notExistsId)).andExpect(status().isNotFound());
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
		String json = objectMapper.writeValueAsString(productDto);
		
		mockMvc.perform(put("/products/{id}", existsId)
		.content(json).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdNotExists() throws Exception {
		String json = objectMapper.writeValueAsString(productDto);
		
		mockMvc.perform(put("/products/{id}", notExistsId)
		.content(json).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteShouldVoidWhenIdExists() throws Exception {
		mockMvc.perform(delete("/products/{id}", existsId)).andExpect(status().isNoContent());
	}
	
	@Test
	public void O() throws Exception {
		mockMvc.perform(delete("/products/{id}", notExistsId)).andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() throws Exception {
		mockMvc.perform(delete("/products/{id}", dependentId)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void insertShouldReturnProductDTO() throws Exception {
		String json = objectMapper.writeValueAsString(productDto);
		
		mockMvc.perform(post("/products")
		.content(json).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
}
