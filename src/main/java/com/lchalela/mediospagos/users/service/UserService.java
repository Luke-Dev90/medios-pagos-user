package com.lchalela.mediospagos.users.service;

import com.lchalela.mediospagos.users.dto.UserDTO;
import com.lchalela.mediospagos.users.entity.User;

import java.util.List;

public interface UserService {
    User getUserByID(Long id);
    List<User> getAllUsers();
    User updateUser(UserDTO userDTO, Long id);

    User createUser(UserDTO userDto);
}
