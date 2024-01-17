package com.vinicius.dscatalog.dtos;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vinicius.dscatalog.entities.Category;
import com.vinicius.dscatalog.entities.Product;

public class ProductDTO {
	
	private Long id;
	private String name;
	private Instant date;
	private Double price;
	private String description;
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
