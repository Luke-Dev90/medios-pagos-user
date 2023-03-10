package com.lchalela.mediospagos.service;

import com.lchalela.mediospagos.clients.AccountRest;
import com.lchalela.mediospagos.clients.PublisherRest;
import com.lchalela.mediospagos.dto.AccountCreateDTO;
import com.lchalela.mediospagos.dto.AccountDTO;
import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserDTOAuth;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.dto.UserUpdateEmailDTO;
import com.lchalela.mediospagos.dto.UserUpdatePasswordDTO;
import com.lchalela.mediospagos.exception.PasswordInvalidException;
import com.lchalela.mediospagos.exception.UserNotFoundException;
import com.lchalela.mediospagos.mapper.UserMapper;
import com.lchalela.mediospagos.model.Role;
import com.lchalela.mediospagos.model.User;
import com.lchalela.mediospagos.repository.RoleRepository;
import com.lchalela.mediospagos.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private AccountRest accountRest;
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private PublisherRest publisherRest;
	private RoleRepository roleRepository;
	private Tracer trace;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AccountRest accountRest,
			PublisherRest publisherRest, RoleRepository roleRepository, Tracer trace) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.accountRest = accountRest;
		this.publisherRest = publisherRest;
		this.roleRepository = roleRepository;
		this.trace = trace;

	}

	@Override
	public UserDTO getUserByID(Long id) throws ConnectException {
		logger.info("init find user by id");

		User user = this.userRepository.findById(id).orElseThrow(() -> {
			String error = "user not found";
			logger.error(error);
			// TODO: find the correct solution to test the trace
			//trace.currentSpan().tag("error", error);
			return new UserNotFoundException(error);
		});

		logger.info("Init request to ms account ");
		List<AccountDTO> accountDto = this.accountRest.getAccountByUserId(user.getId());

		user.setAccountDTO(accountDto);

		return this.userMapper.userToUserDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		logger.info("find all user");
		return this.userMapper.userListToDTO(this.userRepository.findAll());
	}

	@Transactional
	@Override
	public void updateEmailUser(UserUpdateEmailDTO userDTO, Long id) throws Exception {
		User user = this.userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
		this.userMapper.updateEmailUser(userDTO, user);
		this.userRepository.save(user);
	}

	@Override
	public UserDTO createUser(UserRegisterDTO userDto) throws ConnectException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		logger.info("Init mapper userDto to user and persist");
		
		Role role = this.roleRepository.findById(1L).get();
		
		userDto.setPassword( passwordEncoder.encode( userDto.getPassword() ));
		User userSave = this.userMapper.userRegisterToUser(userDto);
		
		userSave.setRoles( List.of(role) );
		
		this.userRepository.save(userSave);

		logger.info("Roles: " + userSave.getRoles());
		logger.info("Add new account by default");
		AccountCreateDTO account = new AccountCreateDTO();

		account.setUserId(userSave.getId());
		account.setLastName(userSave.getLastName());
		account.setName(userSave.getName());
		account.setTypeAccount("Caja de ahorro en Pesos");

		logger.info("request create new account and persist in the db  MS: account ");
		List<AccountDTO> accountDTO = this.accountRest.createAccount(account);
		userSave.setAccountDTO(accountDTO);
		
		
		logger.info("set message email and sent email");
		
		String messageEmail = "User created: "
				.concat(userSave.getEmail()
				.concat(", your cbu is: ")
				.concat( accountDTO.get(0).getCbu())
				.concat(", your account is actived thank you and welcome"));
		
		// TODO: find the correct solution to test the trace
		//trace.currentSpan().tag("Send-Email", messageEmail);

		this.publisherRest.sendEmail(messageEmail);
		
		return this.userMapper.userToUserDTO(userSave);
	}

	@Transactional
	@Override
	public void updatePasswordUser(UserUpdatePasswordDTO userDTO, Long id) {
		if (userDTO.getPassword().equals(userDTO.getPassword2())) {
			// TODO: encrypt password
			User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
			this.userMapper.updatePasswordUser(userDTO, user);
			this.userRepository.save(user);
		} else {
			String error = "Password are not equals";
			logger.error(error);
			//trace.currentSpan().tag("error", error);
			throw new PasswordInvalidException(error);
		}
	}

	@Override
	public UserDTOAuth findByEmail(String email) {
		UserDTOAuth userDto = this.userMapper.userToUserDTOAuth(this.userRepository.findByEmail(email));
		return userDto;
	}
}
