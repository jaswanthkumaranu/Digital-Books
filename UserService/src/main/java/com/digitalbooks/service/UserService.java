package com.digitalbooks.service;

import static com.digitalbooks.utility.UserRoutings.EMPTY_STRING;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalbooks.dto.BookDto;
import com.digitalbooks.model.UserVo;
import com.digitalbooks.repository.UserRepository;
import com.digitalbooks.rest.RestClientRest;
import com.digitalbooks.utility.UserManagmentException;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class UserService implements UserDetailsService {

	public static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired(required = true)
	private UserRepository userRepository;

	@Autowired
	private RestClientRest restClient;

	public List<UserVo> getAllUserDetailsUsers() {
		return userRepository.findAll();
	}

	@Cacheable(value = "movielibrary", key = "#id")
	public UserVo getUserById(Long id) throws Exception {
		Optional<UserVo> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new Exception("Can not find movie with id: " + id);
		} else {
			return user.get();
		}
	}

	public UserVo insertUserData(UserVo user) {
		user.setIsActive(1L);
		return userRepository.save(user);
	}

	@CachePut(value = "userlibrary", key = "#id")
	public UserVo updateUserData(UserVo user) throws Exception {
		if (user != null && user.getUserId() > 0) {
			return userRepository.save(user);
		} else {
			throw new Exception("Can not find user with id: ");
		}
	}

	@CacheEvict(value = "userlibrary", key = "#id")
	public UserVo deleteUserById(Long id) throws Exception {
		if (id > 0) {
			UserVo user = getUserById(id);
			if (user != null && !"".equalsIgnoreCase(user.getUserName())) {
				userRepository.deleteById(id);
				return user;
			}
		}
		throw new UserManagmentException("Can not delete User with id: " + id);

	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVo user = userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	public Object createBook(BookDto book, String authorId) throws UserManagmentException {
		if (book.getPrice() == null || book.getPrice() < 0) {
			throw new UserManagmentException("Price cant be  Negative or NUll!");

		} else if (book.getBookTitle() == null || book.getBookTitle().equalsIgnoreCase(EMPTY_STRING)) {
			throw new UserManagmentException("Book Title cant be Empty!");
		} else {
			ResponseEntity<BookDto> createdBook = restClient.postForBook("author/" + authorId + "/books", book);
			if (createdBook != null) {
				return createdBook;
			} else {
				throw new UserManagmentException("Something went wrong Please try after some time!");
			}
		}
	}

	public ResponseEntity<BookDto> updateBook(BookDto book, String authorId, String bookId)
			throws UserManagmentException {
		if (book.getPrice() == null || book.getPrice() < 0) {
			throw new UserManagmentException("Price cant be  Negative or NUll!");

		} else if (book.getBookTitle() == null || book.getBookTitle().equalsIgnoreCase(EMPTY_STRING)) {
			throw new UserManagmentException("Book Title cant be Empty!");
		} else {
			ResponseEntity<BookDto> createdBook = restClient.postForBook("author/" + authorId + "/books/" + bookId,
					book);
			if (createdBook != null) {
				return createdBook;
			} else {
				throw new UserManagmentException("Something went wrong Please try after some time!");
			}
		}
	}

	public List<BookDto> searchBook(String category, String title, String author, String price, String publisher)
			throws JsonProcessingException {
//		String jsonString=
		ResponseEntity<?> books = restClient.searchBook("search", category, title, author, price, publisher);

//		ObjectMapper mapper = new ObjectMapper();
//		StudentList studentList = mapper.readValue(jsonString, StudentList.class);
		List<BookDto> bookList= null;
		if(books.getStatusCode().equals(HttpStatus.OK))
		 bookList=  (List<BookDto>) books.getBody();
		return bookList;
	}
}
