package com.vinicius.dscatalog.factories;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.vinicius.dscatalog.dtos.ProductDTO;
import com.vinicius.dscatalog.entities.Category;
import com.vinicius.dscatalog.entities.Product;
import com.vinicius.dscatalog.entities.Role;
import com.vinicius.dscatalog.entities.User;
import com.vinicius.dscatalog.projections.ProductProjection;
import com.vinicius.dscatalog.projections.UserDetailsProjection;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Bola de futebol", "Bola de futebol", 99.99, "Link da bola", Instant.now());
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		return new Category(1L, "Esportivos");
	}

	public static ProductProjection createProductProjection() {
		return new ProductProjection() {
			@Override
			public String getName() {
				return "Bola de futebol";
			}

			@Override
			public Long getId() {
				return 1L;
			}
		};
	}
	
	public static User createOperatorUser() {
		User user = new User(1L, "João", "da Silva", "joao@gmail.com", "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(1L, "ROLE_OPERATOR"));
		return user;
	}
	
	public static User createAdminUser() {
		User user = new User(2L, "Maria", "da Silva", "maria@gmail.com", "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}
	
	public static User createCustomOperatorUser(Long id, String username) {
		User user = new User(id, "João", "da Silva", username, "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(1L, "ROLE_OPERATOR"));
		return user;
	}
	
	public static User createCustomAdminUser(Long id, String username) {
		User user = new User(id, "Maria", "da Silva", username, "$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}
	
	public static List<UserDetailsProjection> createUserDetails(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(userDetailsProjection(username));
		return list;
	}

	private static UserDetailsProjection userDetailsProjection (String username) {
		return new UserDetailsProjection() {
			@Override
			public String getUsername() { return username; }

			@Override
			public String getPassword() {return "123";}

			@Override
			public Long getRoleId() {return 2L;}

			@Override
			public String getAuthority() {return "ROLE_ADMIN";}
		};
	}
}
