package com.lchalela.mediospagos.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.lchalela.mediospagos.dto.AccountDTO;
import com.lchalela.mediospagos.dto.TransactionDTO;
import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserDTOAuth;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.dto.UserUpdateEmailDTO;
import com.lchalela.mediospagos.dto.UserUpdatePasswordDTO;
import com.lchalela.mediospagos.model.Role;
import com.lchalela.mediospagos.model.User;

@SpringBootTest
public class UserUtils {
	
	public static UserUpdatePasswordDTO userPasswordMock() {
		return new UserUpdatePasswordDTO("12345678","12345678");
	}
	
	@Test
	public void userTest() {
		UserDTO user = mock(UserDTO.class);
		user.setEmail("lucas@hotmail.com");
		user.setEnabled(true);
		user.setLastName("Chalela");
		user.setName("Lucas");
		user.setPassword("12345");
		user.setRoles( List.of( new Role(1L,"ROLE_USER") ));
		user.setAccountDTO(  List.of(  accountMock() ));
		
		verify(user, times(1)).setAccountDTO(anyList());
		verify(user, times(1)).setRoles(anyList());
		verify(user, times(1)).setName(anyString());
		verify(user, times(1)).setLastName( anyString());
		verify(user, times(1)).setPassword(anyString());
		verify(user, times(1)).setEmail( anyString());
		verify(user, times(1)).setEnabled(true);
	}
	
	public static UserDTO userDTOMock() {
		UserDTO user = new UserDTO();
		user.setEmail("lucas@hotmail.com");
		user.setEnabled(true);
		user.setLastName("Chalela");
		user.setName("Lucas");
		user.setPassword("12345");
		user.setRoles( List.of( new Role(1L,"ROLE_USER") ));
		user.setAccountDTO(  List.of(  accountMock() ));
		return user;
	}
	
	public static UserDTOAuth userAuthMock() {
		UserDTOAuth userAuth = new UserDTOAuth();
		userAuth.setEmail("lucas@hotmail.com");
		userAuth.setEnabled(true);
		userAuth.setLastName("Chalela");
		userAuth.setName("Lucas");
		userAuth.setPassword("12345");
		userAuth.setRoles( List.of( new Role(1L,"ROLE_USER") ));
		userAuth.setAccountDTO(  List.of(  accountMock() ));
		return userAuth;
	}
	public static UserDTO userDTOMockB() {
		UserDTO user = new UserDTO();
		user.setEmail("lucas@hotmail.com");
		user.setEnabled(true);
		user.setLastName("Chalela");
		user.setName("Lucas");
		user.setPassword("12345");
		user.setRoles( List.of( new Role(1L,"ROLE_USER") ));
		user.setAccountDTO(  List.of(  accountMock() ));
		return user;
	}
	
	public static Optional<UserRegisterDTO> userRegisterMock(){
		UserRegisterDTO userRegister = new UserRegisterDTO();
		userRegister.setEmail("lucas@hotmail.com");
		userRegister.setLastName("Chalela");
		userRegister.setName("Lucas");
		userRegister.setPassword("12345678");
		return Optional.of(userRegister);
	}
	
	public static Optional<User> userMock() {
		User user = new User();
		user.setEmail("lucas@hotmail.com");
		user.setEnabled(true);
		user.setLastName("Chalela");
		user.setName("Lucas");
		user.setPassword("12345");
		user.setRoles( List.of( new Role(1L,"ROLE_USER") ));
		user.setAccountDTO(  List.of(  accountMock() ));
		return Optional.of(user);
	}
	public static Optional<User> userMockB() {
		User user = new User();
		user.setEmail("lucas@hotmail.com");
		user.setEnabled(true);
		user.setLastName("Chalela");
		user.setName("Lucas");
		user.setPassword("12345");
		user.setRoles( List.of( new Role(1L,"ROLE_USER") ));
		user.setAccountDTO(  List.of(  accountMock() ));
		return Optional.of(user);
	}
	
	public static AccountDTO accountMock() {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountNumber("1111666688");
		accountDTO.setAlias("Chalela.1234.Lucas");
		accountDTO.setBalance(new BigDecimal(0));
		accountDTO.setCbu("9999111199991111333312");
		accountDTO.setIsActived(true);
		accountDTO.setTransactionDTO(List.of( transactionMock() ));
		accountDTO.setTypeAccount("Caja ahorro en pesos");
		accountDTO.setUserId(1L);
		return accountDTO;
	}
	
	public static TransactionDTO transactionMock() {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAccountDestination("9999111199991111333300");
		transaction.setAccountOrigin("9999111199991111333312");
		transaction.setAmount(new BigDecimal(10));
		transaction.setCreateAt( LocalDateTime.now());
		transaction.setReason("alquiler");
		return transaction;
	}
	
	public static UserUpdateEmailDTO userUpdateEmailMock() {
		UserUpdateEmailDTO userEmail = new UserUpdateEmailDTO();
		userEmail.setEmail("lucas2@hotmail.com");
		return userEmail;
	}
	public static List<User> usersListMock(){
		return List.of(userMock().get(), userMockB().get() );
	}
	
	public static List<UserDTO> userListDTOMock(){
		return List.of(userDTOMock(), userDTOMockB());
	}
	public static List<AccountDTO> accountList(){
		return List.of(accountMock());
	}
	public static Role roleUserMock() {
		return new Role(1L,"ROLE_USER");
	}
}
