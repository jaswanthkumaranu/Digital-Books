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
import com.digitalbooks.dto.ReaderVo;
import com.digitalbooks.model.UserVo;
import com.digitalbooks.payload.response.MessageResponse;
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
			ResponseEntity<MessageResponse> createdBook = restClient.postForBook("author/" + authorId + "/books", book);
			if (createdBook != null) {
				return createdBook;
			} else {
				throw new UserManagmentException("Something went wrong Please try after some time!");
			}
		}
	}

	public Object updateBook(BookDto book, String authorId, String bookId)
			throws UserManagmentException {
		if (book.getPrice() == null || book.getPrice() < 0) {
			throw new UserManagmentException("Price cant be  Negative or NUll!");

		} else if (book.getBookTitle() == null || book.getBookTitle().equalsIgnoreCase(EMPTY_STRING)) {
			throw new UserManagmentException("Book Title cant be Empty!");
		} else {
			ResponseEntity<?> createdBook = restClient.updateForBook("author/" + authorId + "/books/" + bookId,
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
		ResponseEntity<?> books = restClient.searchBook("search", category, title, author, price, publisher);
		List<BookDto> bookList = null;
		if (books.getStatusCode().equals(HttpStatus.OK))
			bookList = (List<BookDto>) books.getBody();
		return bookList;
	}

	public boolean subscribeBook(String bookId, ReaderVo reader) throws NumberFormatException, Exception {
		boolean subscribe=false;
		
		UserVo user=getUserById(Long.parseLong(reader.getReaderId()));
		if(user!=null&&user.getEmailId().equalsIgnoreCase(reader.getReaderEmail())) {
		
		ResponseEntity<String> result = restClient.subscribeBook(bookId + "/subscribe", bookId, reader);
		System.out.println("subscribe book status code" + result.getStatusCode());
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			subscribe=true;
		}
		}else {
			 throw  new UserManagmentException("User not valid");
		}

		return subscribe;
	}

	public List<BookDto> getAllSubscribeBooksByReader(String emailId) throws UserManagmentException {
		List<BookDto> bookList = null;
		boolean userExists=userRepository.existsByEmailId(emailId);
		
		if(userExists) {
			ResponseEntity<?> books = restClient.getAllSubscribeBooksByReader("readers/{emailId}/books", emailId);
			
			if (books.getStatusCode().equals(HttpStatus.OK))
				bookList = (List<BookDto>) books.getBody();
		}
		else {
			throw new UserManagmentException("User Not Valid..!");
		}
		
		return bookList;
	}

	public BookDto getSubscribeBookByReaderEmailId(String emailId, String subscriptionId) throws UserManagmentException {
		List<BookDto> bookList = null;
		boolean userExists=userRepository.existsByEmailId(emailId);
		if(userExists) {
			ResponseEntity<?> books = restClient.getSubscribeBookByReaderEmailId("readers/"+emailId+"/books/"+subscriptionId, emailId,subscriptionId);
			
			if (books.getStatusCode().equals(HttpStatus.OK))
				bookList =  (List<BookDto>)  books.getBody();
		}
		else {
			throw new UserManagmentException("User Not Valid..!");
		}
		return bookList.get(0);
		
	}

	public String getSubscribeBookByReader(String emailId, String subscriptionId) {
		
		String content="";
		ResponseEntity<String> result = restClient.getSubscribeBookByReader("readers/"+emailId + "/books/"+subscriptionId+"/read");
		if(result.getStatusCode().equals(HttpStatus.OK))
			content=result.getBody();
		
		return content;
	}

	public boolean cancleSubscriptionWithIn24Hours(String readerId, String subscriptionId) {
		boolean cancleSub=false;
		ResponseEntity<String> result = restClient.cancleSubscriptionWithIn24Hours("readers/"+readerId + "/books/"+subscriptionId+"/cancel-subscription",subscriptionId);
		if(result.getStatusCode().equals(HttpStatus.OK))
			cancleSub=true;
		return cancleSub;
	}

	public boolean blockOrUnBlockBookByAuthor(String authorId, String bookId, String block) {
		boolean blockOrUnBlock=false;
		ResponseEntity<String> result = restClient.blockOrUnBlockBookByAuthor("author/{authorId}/books/{bookId}/block={block}",authorId,bookId,block);
		if(result.getStatusCode().equals(HttpStatus.OK))
			blockOrUnBlock=true;
		return blockOrUnBlock;
	}
}
