package com.digitalbooks.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.dto.BookDto;
import com.digitalbooks.dto.ReaderVo;
import com.digitalbooks.payload.response.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;



@Component
public class RestClientRest {
	
	@Autowired
	RestTemplate restTemplate;
	@Value("${um.app.bookUrl}")
	private String BOOK_URL;

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> getBookDetails(String url,BookDto book) {
		ResponseEntity<Object> response= null;

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<BookDto> entity = new HttpEntity<>(book, headers);
		try {
//			books = (List<BookVo>) restTemplate.getForObject("http://localhost:8084/book/" + url, BookVo.class);
			response = restTemplate
					.exchange(BOOK_URL + url, HttpMethod.POST, entity, ResponseEntity.class)
					.getBody();
			System.out.println("response from book : "+response);
			return ResponseEntity.ok(response.getBody());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<MessageResponse> postForBook(String url,BookDto book) {
		MessageResponse result = restTemplate.postForObject(BOOK_URL+url, book, MessageResponse.class);
		return ResponseEntity.ok(result);
	}
	public ResponseEntity<?> updateForBook(String url,BookDto book) {
		MessageResponse result = restTemplate.postForObject(BOOK_URL+url, book, MessageResponse.class);
		return ResponseEntity.ok(result);
	}
	
	public ResponseEntity<?> searchBook(String url,String category, String title, String author, String price, String publisher) throws JsonProcessingException {
	
		 List<BookDto> result = restTemplate.getForObject(BOOK_URL+"search?category={category}&title={title}&author={author}&price={price}&publisher={publisher}", List.class,category,title, author, price, publisher );
		 return ResponseEntity.ok(result);
		
	}

	public ResponseEntity<MessageResponse> subscribeBook(String url, String bookId, ReaderVo reader) {

		MessageResponse result = restTemplate.postForObject(BOOK_URL+url, reader, MessageResponse.class);
	        return ResponseEntity.ok(result);
	}

	public ResponseEntity<?> getAllSubscribeBooksByReader(String url, String emailId) {
		List<BookDto> result = restTemplate.getForObject(BOOK_URL+url, List.class,emailId );
		 return ResponseEntity.ok(result);
	}

	public ResponseEntity<?> getSubscribeBookByReaderEmailId(String url, String emailId, String subscriptionId) {
		System.out.println(BOOK_URL+url);
		List<BookDto> result = restTemplate.getForObject(BOOK_URL+url, List.class );
		 return ResponseEntity.ok(result);
	}

	public ResponseEntity<MessageResponse> getSubscribeBookByReader(String url) {
		MessageResponse result = restTemplate.getForObject(BOOK_URL+url,  MessageResponse.class);
	        return ResponseEntity.ok(result);
	}

	public ResponseEntity<MessageResponse> cancleSubscriptionWithIn24Hours(String url,String subscriptionId) {
		MessageResponse result = restTemplate.postForObject(BOOK_URL+url,  subscriptionId,MessageResponse.class);
	        return ResponseEntity.ok(result);
	}

	public ResponseEntity<MessageResponse> blockOrUnBlockBookByAuthor(String url, String authorId, String bookId,
			String block) {
		MessageResponse result = restTemplate.postForObject(BOOK_URL+url,null, MessageResponse.class,authorId,bookId,block);
	        return ResponseEntity.ok(result);
	}

	public ResponseEntity<?> getAuthorCreatedBooks(String url,String authorId) {
		List<BookDto> result = restTemplate.getForObject(BOOK_URL+url, List.class );
		 return ResponseEntity.ok(result);
	}

	
	
}
