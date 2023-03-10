package com.lchalela.mediospagos.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lchalela.mediospagos.repository.UserRepository;
import com.lchalela.mediospagos.service.UserService;
import com.lchalela.mediospagos.service.utils.UserUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void init() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Test
	public void getAll() throws JsonProcessingException, Exception {

		given(userService.getAllUsers()).willReturn(UserUtils.userListDTOMock());

		mvc.perform(get("/v1/all")).andExpect(status().isOk())
				.andExpect(jsonPath("[0].name").value("Lucas")).andExpect(jsonPath("[0].lastName").value("Chalela"));
	}

	@Test
	public void getById() throws Exception {
		given(userService.getUserByID(anyLong())).willReturn(UserUtils.userDTOMock());

		mvc.perform(get("/v1/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("name").value("Lucas"))
		.andExpect(jsonPath("lastName").value("Chalela"));
	}

	@Test
	public void findByEmailTest() throws JsonProcessingException, Exception {
		String email = "lucas@hotmail.com";
		given(userService.findByEmail(email)).willReturn(UserUtils.userAuthMock());

		mvc.perform(get("/v1/findByEmail")
				.param("email", email))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("Lucas"))
				.andExpect(jsonPath("lastName").value("Chalela"))
				.andExpect(jsonPath("email").value(email));
	}
	
	@Test
	public void crateUserTest() throws Exception {
		given(userService.createUser(UserUtils.userRegisterMock().get())).willReturn(UserUtils.userDTOMock());
		
		mvc.perform(post("/v1/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(UserUtils.userRegisterMock().get())))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("name").value("Lucas"))
		.andExpect(jsonPath("lastName").value("Chalela"))
		.andExpect(jsonPath("email").value("lucas@hotmail.com"));
	}
	
	@Test
	public void userUpdateEmailTest () throws JsonProcessingException, Exception {
		when(userRepository.findById(anyLong())).thenReturn((UserUtils.userMock()));
		
		MvcResult mvcResult = mvc.perform(put("/v1/update/email/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(UserUtils.userUpdateEmailMock())))
		.andDo(print())
		.andExpect(status().isOk()).andReturn();
		MockHttpServletResponse mock = mvcResult.getResponse();
		assertEquals(mock.getContentAsString(), "Email updated");
	}
	
	@Test
	public void updatePasswordTest() throws JsonProcessingException, Exception {
		when(userRepository.findById(anyLong())).thenReturn((UserUtils.userMock()));
		
		MvcResult mvcResult = mvc.perform(put("/v1/update/password/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(UserUtils.userPasswordMock())))
		.andDo(print())
		.andExpect(status().isOk()).andReturn();
		MockHttpServletResponse mock = mvcResult.getResponse();
		assertEquals(mock.getContentAsString(), "password updated");
	}
}
