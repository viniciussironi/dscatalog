package com.vinicius.dscatalog.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.dscatalog.dtos.EmailDTO;
import com.vinicius.dscatalog.dtos.NewPasswordDTO;
import com.vinicius.dscatalog.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	AuthService authService;

	@PostMapping(value = "/recover-token")
	public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO dto) {
		authService.createRecoverToken(dto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/new-password")
	public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO dto) {
		authService.saveNewPassword(dto);
		return ResponseEntity.noContent().build();
	}
}
