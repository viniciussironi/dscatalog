package com.vinicius.dscatalog.factories;

import java.time.Instant;

import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.entities.Category;
import com.vinicius.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Produto", "Description", 1.99, "Link", Instant.now());
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		return new Category(1L, "Categoria");
	}
}
