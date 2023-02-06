package com.lchalela.mediospagos.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
	private String email;
	private String password;
	private String password2;
}
