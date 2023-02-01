package com.lchalela.mediospagos.service;

import com.lchalela.mediospagos.clients.AccountRest;
import com.lchalela.mediospagos.dto.AccountCreateDTO;
import com.lchalela.mediospagos.dto.AccountDTO;
import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.mapper.UserMapper;
import com.lchalela.mediospagos.model.User;
import com.lchalela.mediospagos.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
    private UserMapper userMapper;
    private AccountRest accountRest;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,AccountRest accountRest) {
    	this.userRepository = userRepository;
    	this.userMapper = userMapper;
    	this.accountRest = accountRest;
    }
    
    @Override
    public UserDTO getUserByID(Long id) {
    	
    	User user = this.userRepository.findById(id)
    			.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    	
    	List<AccountDTO> accountDto = this.accountRest.getAccountByUserId( user.getId() );
    	user.setAccountDTO(accountDto);
    	
        return this.userMapper.userToUserDTO(user);
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
    	User userSave = this.userRepository.save( this.userMapper.userRegisterToUser(userDto));
    	
    	AccountCreateDTO account = new AccountCreateDTO();	
    	
    	account.setUserId( userSave.getId());
    	account.setLastName( userSave.getLastName());
    	account.setName(userSave.getName());
    	account.setTypeAccount("Caja de ahorro en Pesos");
    	
    	List<AccountDTO> accountDTO = this.accountRest.createAccount(account);
    	
    	userSave.setAccountDTO(accountDTO);
        return this.userMapper.userToUserDTO(userSave);
    }
}
