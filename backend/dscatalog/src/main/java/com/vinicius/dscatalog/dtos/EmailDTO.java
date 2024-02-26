package com.vinicius.dscatalog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

    @NotBlank(message = "Campo Obrigatório")
    @Email(message = "E-mail inválido")
    private String email;
    
    public EmailDTO() {
	}
    
    public EmailDTO(String email) {
		this.email = email;
	}

	public String getEmail() {
    	return email;
    }
    
	public void setEmail(String email) {
		this.email = email;
	}

}
