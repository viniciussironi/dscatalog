package com.vinicius.dscatalog.dtos;

import java.util.HashSet;
import java.util.Set;

import com.vinicius.dscatalog.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
	
	private Long id;
	@NotBlank(message = "Campo Obrigatório")
	private String firstName;
	@NotBlank(message = "Campo Obrigatório")
	private String lastName;
	@Email(message = "Digite um e-mail válido")
	private String email;
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		entity.getRoles().forEach(x -> roles.add(new RoleDTO(x)));
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}
}
