package com.vinicius.dscatalog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	
	private List<FieldError> errors = new ArrayList<>();
	
	public List<FieldError> getErrors() {
		return errors;
	}
}
