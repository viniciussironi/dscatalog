package com.vinicius.dscatalog.dtos;

import com.vinicius.dscatalog.services.validation.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO {
	
	@NotBlank(message = "Campo Obrigatório")
	@Size(min = 6, max = 15, message = "Deve ter entre 6 e 15 caracteres")
	private String password;
	
	public UserInsertDTO() {
		super();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
