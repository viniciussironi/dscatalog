package com.vinicius.dscatalog.resources.exceptions;

public class FieldError {
	
	private String field;
	private String message;
	
	public FieldError() {
	}
	
	public FieldError(String field, String message) {
		this.field = field;
		this.message = message;
	}
	
	public String getField() {
		return field;
	}
	public void setName(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
