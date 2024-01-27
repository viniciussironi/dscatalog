package com.vinicius.dscatalog.dtos;

import com.vinicius.dscatalog.entities.Role;

public class RoleDTO {
	
	private Long id;
	private String authority;
	
	public RoleDTO() {
	}
	
	public RoleDTO(Role entity) {
		id = entity.getId();
		authority = entity.getAuthority();
	}

	public Long getId() {
		return id;
	}

	public String getAuthority() {
		return authority;
	}
}
