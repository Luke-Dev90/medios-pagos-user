package com.lchalela.mediospagos.service;

import com.lchalela.mediospagos.clients.AccountRest;
import com.lchalela.mediospagos.dto.AccountCreateDTO;
import com.lchalela.mediospagos.dto.AccountDTO;
import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.dto.UserUpdateDTO;
import com.lchalela.mediospagos.mapper.UserMapper;
import com.lchalela.mediospagos.model.User;
import com.lchalela.mediospagos.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger( UserServiceImpl.class);
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,AccountRest accountRest) {
    	this.userRepository = userRepository;
    	this.userMapper = userMapper;
    	this.accountRest = accountRest;
    }
    
    @Override
    public UserDTO getUserByID(Long id) throws Exception {
    	logger.info("init find user by id");
    	
    	User user = this.userRepository.findById(id)
    			.orElseThrow( () ->
    			{
    				logger.error("user not found");
    				return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    			}
    			);
    	    	
    	try {			
    		logger.info("Init request to ms account ");
    		List<AccountDTO> accountDto = this.accountRest.getAccountByUserId( user.getId() );
    		user.setAccountDTO(accountDto);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
    	
        return this.userMapper.userToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
    	logger.info("find all user");
        return this.userMapper.userListToDTO( this.userRepository.findAll());
    }

    @Override
    public void updateUser(UserUpdateDTO userDTO, Long id) throws Exception {
    	
    	if(userDTO.getPassword().equals(userDTO.getPassword2())) {    		
    		User user = this.userRepository.findById(id).orElseThrow( () -> new Exception("User not found") ); 
    		this.userMapper.updateUser(userDTO, user);
    		this.userRepository.save(user);
    	}else {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Passwords are not equals ");
    	}
    	
    }

    @Override
    public UserDTO createUser(UserRegisterDTO userDto) throws Exception
    {
    	logger.info("Init mapper userDto to user and persist");
    	User userSave = this.userRepository.save( this.userMapper.userRegisterToUser(userDto));
    	
    	logger.info("Add new account by default");
    	AccountCreateDTO account = new AccountCreateDTO();	
    	
    	account.setUserId( userSave.getId());
    	account.setLastName( userSave.getLastName());
    	account.setName(userSave.getName());
    	account.setTypeAccount("Caja de ahorro en Pesos");
    	
    	try {
    		logger.info("request create new account and persist in the db  MS: account ");
    		List<AccountDTO> accountDTO = this.accountRest.createAccount(account);			
    		userSave.setAccountDTO(accountDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
    	
        return this.userMapper.userToUserDTO(userSave);
    }
}
