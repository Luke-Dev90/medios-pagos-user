package com.lchalela.mediospagos.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiHandlerException extends ResponseEntityExceptionHandler {

	private Map<String, Object> response = new HashMap<>();
	private Logger logger = LoggerFactory.getLogger(ApiHandlerException.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		response.clear();

		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField().concat(": ").concat(err.getDefaultMessage())).collect(Collectors.toList());
		logger.error(errors.toString());
		response.put("errors", errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
