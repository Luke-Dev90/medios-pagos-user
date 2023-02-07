package com.lchalela.mediospagos.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserUpdatePasswordDTO {
	@Size(min=8,max=8)
	private String password;
	@Size(min=8,max=8)
	private String password2;
}
