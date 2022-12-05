package com.digitalbooks.service;

import static com.digitalbooks.utility.UserRoutings.EMPTY_STRING;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalbooks.dto.BookDto;
import com.digitalbooks.model.UserVo;
import com.digitalbooks.repository.UserRepository;
import com.digitalbooks.rest.RestClientRest;
import com.digitalbooks.utility.UserManagmentException;

@Service
public class UserService implements UserDetailsService{

	public static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired(required=true)
	private UserRepository userRepository;

	@Autowired
	private RestClientRest restClient;
	
	public List<UserVo> getAllUserDetailsUsers() {
		return userRepository.findAll();
	}
	@Cacheable(value="movielibrary", key="#id")
	public UserVo getUserById(Long id) throws Exception {
		Optional<UserVo> user= userRepository.findById(id);
		if(user.isEmpty()) {
			throw new Exception("Can not find movie with id: "+id);
		} else {
			return user.get();
		}
	}

	public UserVo insertUserData(UserVo user) {
		user.setIsActive(1L);
		return userRepository.save(user);
	}

	@CachePut(value="userlibrary", key="#id")
	public UserVo updateUserData(UserVo user) throws Exception {
		if(user!=null&& user.getUserId()>0) {
			return userRepository.save(user);
		}
		else {
			throw new Exception("Can not find user with id: ");
		}
	}
	@CacheEvict(value="userlibrary", key="#id")
	public UserVo deleteUserById(Long id) throws Exception {
		if(id>0) {
			UserVo user=getUserById(id);
			if(user!=null&&!"".equalsIgnoreCase(user.getUserName())) {
				 userRepository.deleteById(id);
				 return user;
			}
		}
		 throw new UserManagmentException("Can not delete User with id: "+id);
		
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVo user = userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
	public BookDto createBook(BookDto book, String authorId) throws UserManagmentException {
		if(book.getPrice()==null ||book.getPrice()<0) {
			throw new UserManagmentException("Price cant be  Negative or NUll!");
			
		}else if(book.getBookTitle()==null||book.getBookTitle().equalsIgnoreCase(EMPTY_STRING)) {
			throw new UserManagmentException("Book Title cant be Empty!");
		}
		else {
			List<BookDto> books=	(List<BookDto>) restClient.getBookDetails("author/"+authorId+"/books",book);
			if(books!=null&&books.size()>0) {
				return books.get(0);
			}
			else {
				 throw new UserManagmentException("Something went wrong Please try after some time!");
			}
		}
	}
}
