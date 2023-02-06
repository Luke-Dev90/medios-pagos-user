package com.lchalela.mediospagos.controller;

import com.lchalela.mediospagos.dto.UserDTO;
import com.lchalela.mediospagos.dto.UserRegisterDTO;
import com.lchalela.mediospagos.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
    	logger.info("init request get all users");
        List<UserDTO> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws Exception{
    	logger.info("init request get user by id");
    	return new ResponseEntity<>(this.userService.getUserByID(id),HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegisterDTO user) throws Exception{
    	logger.info("Init request post create user");
        return new ResponseEntity<>(this.userService.createUser(user) , HttpStatus.CREATED);
    }
}