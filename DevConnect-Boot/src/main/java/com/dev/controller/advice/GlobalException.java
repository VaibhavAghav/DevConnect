package com.dev.controller.advice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<?> handleDbDataAccess(DataAccessException ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("error", "Database operation failed");
		error.put("timestamp", LocalDate.now());
		error.put("details", ex.getMostSpecificCause().getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleJsonException(HttpMessageNotReadableException ex) {
		return new ResponseEntity<>(
				Map.of("error", "Invalid request payload: " + ex.getMostSpecificCause().getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneric(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
