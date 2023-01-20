package com.lchalela.mediospagos.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	public UserDTO userToUserDTO(User user);
	public User userDtoToUser(UserDTO userDto);
	public List<UserDTO> userListToDTO(List<User> users);
	public User userRegisterToUser(UserRegisterDTO user);
}
