package com.digitalbooks.controller;

import static com.digitalbooks.utility.UserRoutings.DELETE_USER;
import static com.digitalbooks.utility.UserRoutings.GET_ALL_USER;
import static com.digitalbooks.utility.UserRoutings.GET_USER_BY_ID;
import static com.digitalbooks.utility.UserRoutings.INSERT_USER_DATA;
import static com.digitalbooks.utility.UserRoutings.UM;
import static com.digitalbooks.utility.UserRoutings.UPDATE_USER_DATA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.model.UserVo;
import com.digitalbooks.service.UserService;
import com.digitalbooks.utility.UserManagmentException;

@RestController
@RequestMapping(UM)
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(GET_ALL_USER)
	public ResponseEntity<List<UserVo>> getAllUserDetails() throws Exception {
		List<UserVo> users =null;
		try {
			users= userService.getAllUserDetailsUsers();
			return ResponseEntity.status(200).body(users);
			
		}catch(Exception e) {
			throw new UserManagmentException("getAllUserDetails()--->",e);
			//return ResponseEntity.status(500).body(null);
		}

		
	}

	@GetMapping(GET_USER_BY_ID)
	public ResponseEntity<UserVo> getUserById(@PathVariable Long id) throws Exception {
		UserVo user =null;
		try {
			user= userService.getUserById(id);
			return ResponseEntity.status(200).body(user);
			
		}catch(Exception e) {
			throw new UserManagmentException("getUserById()--->",e);
		}

		
	}
	
	@PostMapping(value=INSERT_USER_DATA)
	public ResponseEntity<UserVo> insertUserData(@RequestBody UserVo user) throws UserManagmentException{
		
		try {
			user= userService.insertUserData(user);
			return ResponseEntity.status(200).body(user);
			
		}catch(Exception e) {
			throw new UserManagmentException("insertUserData()--->",e);
		}
	}
	
	@PostMapping(value=UPDATE_USER_DATA)
	public ResponseEntity<UserVo> updateUserData(@RequestBody UserVo user) throws UserManagmentException{
		
		try {
			user= userService.updateUserData(user);
			return ResponseEntity.status(200).body(user);
			
		}catch(Exception e) {
			throw new UserManagmentException("updateUserData()--->",e);
		}
	}
	
	@DeleteMapping(DELETE_USER)
	public ResponseEntity<UserVo> deleteUserById(@PathVariable Long id) throws Exception{
		try {
		UserVo user=userService.deleteUserById(id);
		return ResponseEntity.status(200).body(user);
		}
		catch(Exception e) {
			throw new UserManagmentException("deleteUserById()--->",e);
		}
	}

}
