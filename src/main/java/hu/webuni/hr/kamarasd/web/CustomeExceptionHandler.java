package hu.webuni.hr.kamarasd.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomeExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyError> HandleValidationError(MethodArgumentNotValidException e, WebRequest req) {
		MyError myError = new MyError(e.getMessage(), 1002);
		myError.setFieldErrors(e.getBindingResult().getFieldErrors());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myError);
	}

}
