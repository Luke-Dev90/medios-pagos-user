package com.lchalela.mediospagos.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lchalela.mediospagos.clients.AccountRest;
import com.lchalela.mediospagos.clients.PublisherRest;
import com.lchalela.mediospagos.dto.AccountCreateDTO;
import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserDTOAuth;
import com.lchalela.mediospagos.dto.UserUpdateEmailDTO;
import com.lchalela.mediospagos.dto.UserUpdatePasswordDTO;
import com.lchalela.mediospagos.exception.PasswordInvalidException;
import com.lchalela.mediospagos.exception.UserNotFoundException;
import com.lchalela.mediospagos.model.User;
import com.lchalela.mediospagos.repository.RoleRepository;
import com.lchalela.mediospagos.repository.UserRepository;

import static com.lchalela.mediospagos.service.utils.UserUtils.userMock;
import static com.lchalela.mediospagos.service.utils.UserUtils.usersListMock;
import static com.lchalela.mediospagos.service.utils.UserUtils.roleUserMock;
import static com.lchalela.mediospagos.service.utils.UserUtils.accountList;
import static com.lchalela.mediospagos.service.utils.UserUtils.userRegisterMock;;

@SpringBootTest
public class UserServiceTest {
	
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private RoleRepository roleRepository;

	@MockBean
	private AccountRest accountRest;

	@MockBean
	private PublisherRest publisherRest;

	@Autowired
	private UserService userService;

	private final Long id = 1L;
	private User userEntity;
	
	@BeforeEach
	public void init() {
		userEntity = userMock().get();
	}

	@Test
	@Order(1)
	public void getUserByIDTestB() throws Exception {
		// Given
		when(this.userRepository.findById(id)).thenReturn(Optional.of(this.userEntity));

		// When
		UserDTO user = this.userService.getUserByID(id);

		// then
		assertEquals(user.getName(), "Lucas");
		assertEquals(user.getEnabled(), true);
		assertTrue(user.getEmail().contains("@"));
	}

	@Test
	@Order(2)
	public void getUserByIdNotFound() throws Exception {
		//given(this.userRepository.findById(4L)).willThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () ->  this.userService.getUserByID(id));
	}

	@Test
	@Order(3)
	public void getAllUsersTest() {

		// Given
		given(this.userRepository.findAll()).willReturn(usersListMock());

		// When
		List<UserDTO> users = this.userService.getAllUsers();

		// Then
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(2);
	}
	
	@Test
	@Order(4)
	public void updateEmailUserTest() throws Exception {
		//User user = userMock().get();
		
		UserUpdateEmailDTO userUpdateEmail = new UserUpdateEmailDTO();
		userUpdateEmail.setEmail("lucas-chalela@hotmail.com");
		
		//Given
		given(this.userRepository.findById(id)).willReturn(Optional.of( this.userEntity));
		given(this.userRepository.save(this.userEntity)).willReturn(this.userEntity);
		//When
		
		this.userService.updateEmailUser(userUpdateEmail, id);
		//Then
		
		verify(userRepository , times(1)).findById(id);
		verify(userRepository , times(1)).save(any());
	}
	
	@Test
	@Order(5)
	public void createUserTest() throws Exception {
	
		given(this.roleRepository.findById(1L)).willReturn( Optional.of(roleUserMock()));
		given(this.userRepository.save( this.userEntity )).willReturn( this.userEntity );
		
		given(this.accountRest.createAccount( any() )).willReturn( accountList());
				
		UserDTO userDTO = this.userService.createUser( userRegisterMock().get() );
		
		assertThat(userDTO.getAccountDTO().size()).isEqualTo(1);
		
		verify(roleRepository, times(1)).findById(any());
		verify(accountRest, times(1)).createAccount(any(AccountCreateDTO.class));
		verify( roleRepository , times(1)).findById( any() );
		verify(userRepository , times(1)).save(any()); 
	}
	
	@Test
	public void updatePasswordUserTest() {
		
		UserUpdatePasswordDTO userDTO = new UserUpdatePasswordDTO();
		userDTO.setPassword("90908080");
		userDTO.setPassword2("90908080");
		
		given(this.userRepository.findById(id)).willReturn(Optional.of(this.userEntity));
		
		this.userService.updatePasswordUser(userDTO, id);
		
		verify(userRepository, times(1)).findById(any());
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	public void updatePasswordUserInvalidTest() {
		UserUpdatePasswordDTO userDTO = new UserUpdatePasswordDTO();
		userDTO.setPassword("90908080");
		userDTO.setPassword2("90908082");
		assertThrows(PasswordInvalidException.class, () -> this.userService.updatePasswordUser(userDTO, id));
	}
	
	@Test
	public void findByEmailTest() {
		
		when(this.userRepository.findByEmail(any())).thenReturn(userEntity);
	
		UserDTOAuth userAuth = this.userService.findByEmail(any());
		
		verify(userRepository, times(1)).findByEmail(any());
		assertEquals(userAuth.getName() , "Lucas");
	}
	
}
