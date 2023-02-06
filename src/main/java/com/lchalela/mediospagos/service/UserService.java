package com.lchalela.mediospagos.service;

import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {
	UserDTO getUserByID(Long id)  throws Exception;
    List<UserDTO> getAllUsers();
    void updateUser(UserUpdateDTO userDTO, Long id)  throws Exception;
    UserDTO createUser(UserRegisterDTO user) throws Exception;
}
