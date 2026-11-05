package com.coder.fe_be.exception;

import java.util.HashMap;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.server.ResponseStatusException;
import com.coder.fe_be.response.ApiResponseWrapper;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseWrapper<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		logger.warn("Validation failed: {}", errors);

		ApiResponseWrapper<Map<String, String>> response = new ApiResponseWrapper<>(false, "Validation failed", errors);

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleStudentNotFound(StudentNotFoundException ex) {

		logger.warn("Student not found: {}", ex.getMessage());

		ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(false, ex.getMessage(), null);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleResponseStatusException(
	        ResponseStatusException ex) {

	    logger.warn("Request error: {}", ex.getReason());

	    ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(
	            false,
	            ex.getReason(),
	            null
	    );

	    return ResponseEntity.status(ex.getStatusCode()).body(response);
	} 
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleGeneralException(Exception ex) {

		logger.error("Unexpected error occurred", ex);

		ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(false, "Something went wrong. Please try again later.", null);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleInvalidJson(
	        HttpMessageNotReadableException ex) {

	    logger.warn("Invalid JSON request received");

	    ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(
	            false,
	            "Invalid JSON request. Please check your request body.",
	            null
	    );

	    return ResponseEntity.badRequest().body(response);
	}

}
