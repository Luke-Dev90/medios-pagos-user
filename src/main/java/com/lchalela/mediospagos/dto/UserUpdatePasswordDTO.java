package com.lchalela.mediospagos.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordDTO {
	@Size(min=8,max=8)
	private String password;
	@Size(min=8,max=8)
	private String password2;
}
