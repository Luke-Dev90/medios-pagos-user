package com.lchalela.mediospagos.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lchalela.mediospagos.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    private String lastName;
    private Boolean enabled;
    private List<Role> roles;
    private List<AccountDTO> accountDTO;
}
