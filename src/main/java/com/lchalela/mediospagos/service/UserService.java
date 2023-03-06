package com.lchalela.mediospagos.service;

import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserDTOAuth;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.dto.UserUpdateEmailDTO;
import com.lchalela.mediospagos.dto.UserUpdatePasswordDTO;

import java.util.List;

public interface UserService {
	UserDTO getUserByID(Long id)  throws Exception;
    List<UserDTO> getAllUsers();
    void updateEmailUser(UserUpdateEmailDTO userDTO, Long id)  throws Exception;
    void updatePasswordUser(UserUpdatePasswordDTO userDTO, Long id);
    UserDTO createUser(UserRegisterDTO user) throws Exception;
    UserDTOAuth findByEmail(String username);
}
