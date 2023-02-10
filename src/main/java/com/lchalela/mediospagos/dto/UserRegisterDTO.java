package com.lchalela.mediospagos.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterDTO {
	@Email
	private String email;
	@Size(min=8,max=8)
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	private String lastName;
}
