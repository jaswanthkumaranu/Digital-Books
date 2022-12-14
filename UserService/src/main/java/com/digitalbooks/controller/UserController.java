package com.digitalbooks.controller;

import static com.digitalbooks.utility.UserRoutings.DATA_MISSING;
import static com.digitalbooks.utility.UserRoutings.DELETE_USER;
import static com.digitalbooks.utility.UserRoutings.GET_ALL_USER;
import static com.digitalbooks.utility.UserRoutings.GET_USER_BY_ID;
import static com.digitalbooks.utility.UserRoutings.INSERT_USER_DATA;
import static com.digitalbooks.utility.UserRoutings.NOT_SUSBSCRIBED;
import static com.digitalbooks.utility.UserRoutings.SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.dto.BookDto;
import com.digitalbooks.dto.ReaderVo;
import com.digitalbooks.model.UserVo;
import com.digitalbooks.payload.response.MessageResponse;
import com.digitalbooks.service.UserService;
import com.digitalbooks.utility.UserManagmentException;

@RestController
@RequestMapping(USER)
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getContent() throws Exception{
		
		return  ResponseEntity.status(200).body("Welcome to Digital Books");
	}

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
//	@PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
	public ResponseEntity<?> createBook(@RequestBody BookDto book, @PathVariable String authorId)
			throws UserManagmentException {
		try {
			ResponseEntity<MessageResponse> bookvo = userService.createBook(book, authorId);
			MessageResponse msgRes=bookvo.getBody();
			System.out.println(bookvo);
			if (bookvo != null)
				return ResponseEntity.status(200).body(msgRes.getMessage());
			else
				throw new UserManagmentException(" cant create Book--->");
		} catch (Exception e) {
			throw new UserManagmentException("cant create Book---> error", e);
		}

	}

	@PostMapping(value="/author/{authorId}/books/{bookId}")
	public ResponseEntity<?> updateBook(@RequestBody BookDto book,@PathVariable String authorId,@PathVariable String bookId) throws UserManagmentException{
		
		try {
			Object bookvo= userService.updateBook(book,authorId,bookId);
			return ResponseEntity.status(200).body(bookvo);
			
		}catch(Exception e) {
			throw new UserManagmentException("Updating the Book got error--->",e);
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<BookDto>> searchBooksNew(@RequestParam(name="category" ,defaultValue="") String category,@RequestParam(name="title",defaultValue="") String title,@RequestParam(name="author",defaultValue="") String author,@RequestParam(name="price",defaultValue="0") String price,@RequestParam(name="publisher",defaultValue="") String publisher ) throws UserManagmentException{
		List<BookDto> books=null; 
		try {
			books= userService.searchBook(category,title,author,price,publisher);
			return ResponseEntity.status(200).body(books);
			
		}catch(Exception e) {
			throw new UserManagmentException("Search ERROR()--->",e);
		}
		
		
	}
	@PostMapping("/{bookId}/subscribe") 
	public  ResponseEntity<?> subscribeBook(@RequestBody ReaderVo reader,  @PathVariable String bookId) throws UserManagmentException{
		try {
			ResponseEntity<MessageResponse> result = userService.subscribeBook(bookId,reader);
			MessageResponse msgRes=result.getBody();
			return ResponseEntity.status(200).body(msgRes);
		}catch(Exception e) {
			throw new UserManagmentException(NOT_SUSBSCRIBED,e);
		}
	}
	
	
	@GetMapping("/readers/{emailId}/books")
	public ResponseEntity<List<BookDto>> getAllSubscribeBooksByReader(@PathVariable String emailId) throws UserManagmentException{
		if(emailId!=null&&!emailId.equalsIgnoreCase("")) {
			try {
			List<BookDto> subscribBooksByReader=userService.getAllSubscribeBooksByReader(emailId);
			return ResponseEntity.status(200).body(subscribBooksByReader);
			}catch(Exception e) {
				throw new UserManagmentException(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME,e);
			}
		}
		else {
			throw new UserManagmentException(DATA_MISSING);
		}
	}
	
	
	@GetMapping("/readers/{emailId}/books/{subscriptionId}")
	public ResponseEntity<BookDto> getSubscribeBookByReaderEmailId(@PathVariable String emailId,@PathVariable String subscriptionId) throws UserManagmentException{
		if(emailId!=null&&!emailId.equalsIgnoreCase("")) {
			try {
				BookDto subscribBookByReader=userService.getSubscribeBookByReaderEmailId(emailId,subscriptionId);
			return ResponseEntity.status(200).body(subscribBookByReader);
			}catch(Exception e) {
				throw new UserManagmentException(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME,e);
			}
		}
		else {
			throw new UserManagmentException(DATA_MISSING);
		}
	}
	
	
	@GetMapping("/readers/{emailId}/books/{subscriptionId}/read")
	public ResponseEntity<?> getSubscribeBookContentByReaderEmailId(@PathVariable String emailId,@PathVariable String subscriptionId) throws UserManagmentException{
		if(emailId!=null&&!emailId.equalsIgnoreCase("")) {
			try {
				ResponseEntity<MessageResponse> bookContent= userService.getSubscribeBookByReader(emailId,subscriptionId);
				MessageResponse msgRes=bookContent.getBody();
			return ResponseEntity.status(200).body(msgRes);
			}catch(Exception e) {
				throw new UserManagmentException(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME);
			}
		}
		else {
			throw new UserManagmentException(DATA_MISSING);
		}
	}
	
	
	@PostMapping("/readers/{readerId}/books/{subscriptionId}/cancel-subscription")
	public ResponseEntity<?>cancleSubscriptionWithIn24Hours(@PathVariable String readerId,@PathVariable String subscriptionId ) throws UserManagmentException{
		if(readerId!=null &&!readerId.equalsIgnoreCase("") && !subscriptionId.equalsIgnoreCase("") &&subscriptionId!=null) {
			ResponseEntity<MessageResponse> cancle=userService.cancleSubscriptionWithIn24Hours(readerId,subscriptionId);
			MessageResponse msgRes=cancle.getBody();
			return ResponseEntity.status(200).body(msgRes);
		}
		else {
			return ResponseEntity.status(400).body(DATA_MISSING);
		}
	}
	
	@PostMapping("author/{authorId}/books/{bookId}/block={block}")
	public ResponseEntity<?> blockOrUnBlockBookByAuthor(@PathVariable(value="authorId") String authorId,@PathVariable(value="bookId") String bookId,@PathVariable(value="block") String block) throws UserManagmentException{
		if(block!=null&&!block.equalsIgnoreCase("")&&authorId!=null&&bookId!=null) {
			try {
			ResponseEntity<MessageResponse> update=userService.blockOrUnBlockBookByAuthor(authorId,bookId,block);
			MessageResponse msgRes=update.getBody();
			return ResponseEntity.status(200).body(msgRes);
			}catch(Exception e) {
				throw new UserManagmentException(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME,e);
			}
		}
		else {
			return ResponseEntity.status(400).body("Data Missing!.. authorId:"+authorId+"bookId:"+bookId+"block:"+block);
		}
	}
	@GetMapping("/author/{authorId}/books")
	public ResponseEntity<?> getAuthorCreatedBooks(@PathVariable String authorId)
			throws UserManagmentException {
		if (authorId != null && !authorId.equalsIgnoreCase("")) {
			try {
				List<BookDto> authorCreatedBooks = userService.getAuthorCreatedBooks(authorId);
				return ResponseEntity.status(200).body(authorCreatedBooks);
			} catch (Exception e) {
				throw new UserManagmentException(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME,e);
			}
		} else {
			throw new UserManagmentException(DATA_MISSING);
		}
	}

	
}
