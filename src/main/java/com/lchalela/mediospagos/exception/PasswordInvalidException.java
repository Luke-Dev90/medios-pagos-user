package com.lchalela.mediospagos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PasswordInvalidException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6023689379607984355L;

	public PasswordInvalidException() { super();}
	
	public PasswordInvalidException(String message) {
		super(message);
	}
	
}
