package com.lchalela.mediospagos.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class UserUpdateEmailDTO {
	@Email
	private String email;
}
