package com.vinicius.dscatalog.dtos;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vinicius.dscatalog.entities.Category;
import com.vinicius.dscatalog.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	
	private Long id;
	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	@NotBlank(message = "Campo Obrigatório")
	private String name;
	@PastOrPresent(message = "A data não pode ser futura")
	private Instant date;
	@Positive(message = "Digite um valor válido")
	private Double price;
	@NotBlank(message = "Campo Obrigatório")
	private String description;
	@NotBlank(message = "Campo Obrigatório")
	private String imgUrl;
	
	private List<CategoryDTO> categoriesDto = new ArrayList<>();
	
	public ProductDTO() {
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		date = entity.getDate();
		price = entity.getPrice();
		description = entity.getDescription();
		imgUrl = entity.getImgUrl();
	}
	
	public ProductDTO(Product entity, Set<Category> categories) {
		this(entity);
		categories.forEach(x -> categoriesDto.add(new CategoryDTO(x)));
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Instant getDate() {
		return date;
	}

	public Double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categoriesDto;
	}
}
