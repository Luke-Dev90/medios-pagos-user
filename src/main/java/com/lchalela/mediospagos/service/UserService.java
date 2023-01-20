package com.lchalela.mediospagos.service;

import com.lchalela.mediospagos.model.User;
import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {
    User getUserByID(Long id);
    List<UserDTO> getAllUsers();
    User updateUser(UserDTO userDTO, Long id);

    UserDTO createUser(UserRegisterDTO user);
}
