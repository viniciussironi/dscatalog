package com.vinicius.dscatalog.entities;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "tb_password_recover")
public class PasswordRecover {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private String email;
	private Instant expiration;
	
	public PasswordRecover() {
	}

	public PasswordRecover(Long id, String token, String email, Instant expiration) {
		this.id = id;
		this.token = token;
		this.email = email;
		this.expiration = expiration;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getExpiration() {
		return expiration;
	}

	public void setExpiration(Instant expiration) {
		this.expiration = expiration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, expiration, id, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordRecover other = (PasswordRecover) obj;
		return Objects.equals(email, other.email) && Objects.equals(expiration, other.expiration)
				&& Objects.equals(id, other.id) && Objects.equals(token, other.token);
	}
}
