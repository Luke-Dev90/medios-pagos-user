package com.lchalela.mediospagos.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    @JsonIgnore
    private String pasword;
    private String name;
    private String lastName;
    private List<AccountDTO> accountDTO;
}
