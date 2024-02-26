package com.vinicius.dscatalog.resources.its;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.entities.Product;
import com.vinicius.dscatalog.factories.Factory;
import com.vinicius.dscatalog.resources.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private TokenUtil tokenUtil;

	private Long existingId, nonExistingId, countTotalProducts;
	private String username, password, bearerToken, clientToken, invalidToken, productName, categoryIds;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
		username = "maria@gmail.com";
		password = "123456";
		bearerToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		productName = "PC";
		categoryIds = "3";
		productDTO = Factory.createProductDTO();
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
		result.andExpect(jsonPath("$.content").exists());		
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));		
	}

	@Test
	public void searchAllByNameOrCategoryIdShouldReturnPageWhenProductNameAndCategoryIdExists() throws Exception {
		ResultActions result =
				mockMvc.perform(get("/products?name={productName}&categoryId{categoryIds}", productName, categoryIds)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer Ex"));
	}

	@Test
	public void insertShouldReturnProductDTOWhenValidProduct() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(productDTO);

		String expectedName = productDTO.getName();
		String expectedDescription = productDTO.getDescription();

		ResultActions result =
				mockMvc.perform(post("/products")
						.header("Authorization", "Bearer " + bearerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").value(26L));
		result.andExpect(jsonPath("$.name").value(expectedName));
		result.andExpect(jsonPath("$.description").value(expectedDescription));
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenInvalidProductName() throws Exception {
		ProductDTO dto = new ProductDTO(new Product(null, "abc", "Descrição do produto", 99.99, "www.link.com.br", Instant.now()));
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result =
				mockMvc.perform(post("/products")
						.header("Authorization", "Bearer " + bearerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenInvalidProductDecription() throws Exception {
		ProductDTO dto = new ProductDTO(new Product(null, "Computador", "", 99.99, "www.link.com.br", Instant.now()));
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result =
				mockMvc.perform(post("/products")
						.header("Authorization", "Bearer " + bearerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenInvalidProductPrice() throws Exception {
		ProductDTO dto = new ProductDTO(new Product(null, "Computador", "Descrição do produto", -99.99, "www.link.com.br", Instant.now()));
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result =
				mockMvc.perform(post("/products")
						.header("Authorization", "Bearer " + bearerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenInvalidProductLink() throws Exception {
		ProductDTO dto = new ProductDTO(new Product(null, "Computador", "Descrição do produto", 99.99, "", Instant.now()));
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result =
				mockMvc.perform(post("/products")
						.header("Authorization", "Bearer " + bearerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenInvalidProductDate() throws Exception {
		ProductDTO dto = new ProductDTO(new Product(null, "Computador", "Descrição do produto", 99.99, "www.link.com.br", Instant.now().plusMillis(50000L)));
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result =
				mockMvc.perform(post("/products")
						.header("Authorization", "Bearer " + bearerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		String expectedName = productDTO.getName();
		String expectedDescription = productDTO.getDescription();
		
		ResultActions result = 
				mockMvc.perform(put("/products/{id}", existingId)
					.header("Authorization", "Bearer " + bearerToken)	
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));
		result.andExpect(jsonPath("$.name").value(expectedName));
		result.andExpect(jsonPath("$.description").value(expectedDescription));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = 
				mockMvc.perform(put("/products/{id}", nonExistingId)
					.header("Authorization", "Bearer " + bearerToken)		
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}

	@Test
	public void deleteWhenIdExists() throws Exception {
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));


		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteShouldNotFoundWhenIdDoesNotExis() throws Exception {
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));


		result.andExpect(status().isNotFound());
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() throws Exception {
		ResultActions result =
				mockMvc.perform(get("/products/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));


		result.andExpect(status().isOk());
		result.andExpect(jsonPath("name").value("The Lord of the Rings"));
	}
	@Test
	public void findByIdShouldNotFoundWhenIdDoesNotExis() throws Exception {
		ResultActions result =
				mockMvc.perform(get("/products/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));


		result.andExpect(status().isNotFound());
	}
}
