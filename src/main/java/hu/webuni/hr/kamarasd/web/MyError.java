package hu.webuni.hr.kamarasd.web;

import java.util.List;

import org.springframework.validation.FieldError;

public class MyError {
	
	private String message;
	private int exceptionCode;
	
	private List<FieldError> fieldErrors;

	public MyError(String message, int exceptionCode) {
		this.message = message;
		this.exceptionCode = exceptionCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public List<FieldError> getFieldError() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
