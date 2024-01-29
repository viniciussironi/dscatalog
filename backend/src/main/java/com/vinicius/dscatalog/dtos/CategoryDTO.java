package com.vinicius.dscatalog.dtos;

import com.vinicius.dscatalog.entities.Category;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
	
	private Long id;
	@NotBlank(message = "Campo Obrigatório")
	private String name;
	
	public CategoryDTO() {
	}
	
	public CategoryDTO(Category entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
