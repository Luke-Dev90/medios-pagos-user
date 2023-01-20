package com.lchalela.mediospagos.service;

import com.lchalela.mediospagos.dto.AccountDTO;
import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.mapper.UserMapper;
import com.lchalela.mediospagos.model.User;
import com.lchalela.mediospagos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
    private UserMapper userMapper;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
    	this.userRepository = userRepository;
    	this.userMapper = userMapper;
    }
    
    @Override
    public User getUserByID(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return this.userMapper.userListToDTO( this.userRepository.findAll());
    }

    @Override
    public User updateUser(UserDTO userDTO, Long id) {
        return null;
    }

    @Override
    public UserDTO createUser(UserRegisterDTO userDto)
    {
        // 1 EL user dto Convertirlo en Entity user  y 2 Salvar el usuario en DB
    	User userSave = this.userRepository.save( this.userMapper.userRegisterToUser(userDto));
    	
    	// 3 crear una nueva cuenta para usuario
    	AccountDTO account = new AccountDTO();
    	account.setAlias( userSave.getLastName().concat(".alias") );
    	account.setCbu( UUID.randomUUID().toString() );
    	account.setIdUser( userSave.getId());
    	account.setTypeAccount("Caja Ahorros en pesos");
    	account.setTransactionsDTO( null );
    	
    	userSave.setAccountDTO(account);
    	// 4 Enviarla y persistirla
    	// TODO: feign client 

        return this.userMapper.userToUserDTO(userSave);
    }
}
