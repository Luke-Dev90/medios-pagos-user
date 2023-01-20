package com.lchalela.mediospagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterDTO {
	private String email;
	private String password;
	private String name;
	private String lastName;
}
