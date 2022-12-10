package com.digitalbooks.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private static final String BOOK_URL = "http://localhost:8084/book/";
	@Autowired
	RestTemplate restTemplate;

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
		List<BookDto> result = restTemplate.postForObject(BOOK_URL+url, book, List.class);
		return ResponseEntity.ok(result);
	}
	
	public ResponseEntity<?> searchBook(String url,String category, String title, String author, String price, String publisher) throws JsonProcessingException {
	
		 List<BookDto> result = restTemplate.getForObject(BOOK_URL+"search?category={category}&title={title}&author={author}&price={price}&publisher={publisher}", List.class,category,title, author, price, publisher );
		 return ResponseEntity.ok(result);
		
	}

	public ResponseEntity<String> subscribeBook(String url, String bookId, ReaderVo reader) {

	       String result = restTemplate.postForObject(BOOK_URL+url, reader, String.class);
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

	public ResponseEntity<String> getSubscribeBookByReader(String url) {
		 String result = restTemplate.getForObject(BOOK_URL+url,  String.class);
	        return ResponseEntity.ok(result);
	}

	public ResponseEntity<String> cancleSubscriptionWithIn24Hours(String url,String subscriptionId) {
		 String result = restTemplate.postForObject(BOOK_URL+url,  subscriptionId,String.class);
	        return ResponseEntity.ok(result);
	}

	public ResponseEntity<String> blockOrUnBlockBookByAuthor(String url, String authorId, String bookId,
			String block) {
		 String result = restTemplate.postForObject(BOOK_URL+url,null, String.class,authorId,bookId,block);
	        return ResponseEntity.ok(result);
	}

	
	
}
