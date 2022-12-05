package com.digitalbooks.controller;

import static com.digitalbooks.utility.UserRoutings.DELETE_USER;
import static com.digitalbooks.utility.UserRoutings.GET_ALL_USER;
import static com.digitalbooks.utility.UserRoutings.GET_USER_BY_ID;
import static com.digitalbooks.utility.UserRoutings.INSERT_USER_DATA;
import static com.digitalbooks.utility.UserRoutings.UPDATE_USER_DATA;
import static com.digitalbooks.utility.UserRoutings.USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.dto.BookDto;
import com.digitalbooks.model.UserVo;
import com.digitalbooks.service.UserService;
import com.digitalbooks.utility.UserManagmentException;

@RestController
@RequestMapping(USER)
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(GET_ALL_USER)
	@PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
	public ResponseEntity<List<UserVo>> getAllUserDetails() throws Exception {
		List<UserVo> users = null;
		try {
			users = userService.getAllUserDetailsUsers();
			return ResponseEntity.status(200).body(users);

		} catch (Exception e) {
			throw new UserManagmentException("getAllUserDetails()--->", e);
			// return ResponseEntity.status(500).body(null);
		}

	}

	@GetMapping(GET_USER_BY_ID)
	@PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
	public ResponseEntity<UserVo> getUserById(@PathVariable Long id) throws Exception {
		UserVo user = null;
		try {
			user = userService.getUserById(id);
			return ResponseEntity.status(200).body(user);

		} catch (Exception e) {
			throw new UserManagmentException("getUserById()--->", e);
		}

	}

	@PostMapping(value = INSERT_USER_DATA)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserVo> insertUserData(@RequestBody UserVo user) throws UserManagmentException {

		try {
			user = userService.insertUserData(user);
			return ResponseEntity.status(200).body(user);

		} catch (Exception e) {
			throw new UserManagmentException("insertUserData()--->", e);
		}
	}

	@PostMapping(value = UPDATE_USER_DATA)
	@PreAuthorize("hasRole('READER') or hasRole('AUTHOR') or hasRole('ADMIN')")
	public ResponseEntity<UserVo> updateUserData(@RequestBody UserVo user) throws UserManagmentException {

		try {
			user = userService.updateUserData(user);
			return ResponseEntity.status(200).body(user);

		} catch (Exception e) {
			throw new UserManagmentException("updateUserData()--->", e);
		}
	}

	@DeleteMapping(DELETE_USER)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserVo> deleteUserById(@PathVariable Long id) throws Exception {
		try {
			UserVo user = userService.deleteUserById(id);
			return ResponseEntity.status(200).body(user);
		} catch (Exception e) {
			throw new UserManagmentException("deleteUserById()--->", e);
		}
	}

	@PostMapping("/author/{authorId}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto book, @PathVariable String authorId)
			throws UserManagmentException {
		try {
			BookDto bookvo = userService.createBook(book, authorId);
			if (bookvo != null && bookvo.getBookId() > 0)
				return ResponseEntity.status(200).body(book);
			else
				throw new UserManagmentException(" cant create Book--->");
		} catch (Exception e) {
			throw new UserManagmentException("cant create Book--->", e);
		}

	}

}
