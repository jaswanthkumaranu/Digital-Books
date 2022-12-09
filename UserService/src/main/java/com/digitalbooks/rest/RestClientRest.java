package com.digitalbooks.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.dto.BookDto;
import com.digitalbooks.payload.response.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



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
	
	public ResponseEntity<BookDto> postForBook(String url,BookDto book) {
//		ResponseEntity<BookDto> savedBook = restTemplate.postForEntity(BOOK_URL+url, book , BookDto.class);
		MessageResponse result = restTemplate.postForObject(BOOK_URL+url, book, MessageResponse.class);
		System.out.println(result);
		 ResponseEntity.ok(result);
		
		return null;
	}
	
	public ResponseEntity<?> searchBook(String url,String category, String title, String author, String price, String publisher) throws JsonProcessingException {
	
		 RestTemplate restTemplate = new RestTemplate();
		 List<BookDto> result = restTemplate.getForObject(BOOK_URL+"search?category={category}&title={title}&author={author}&price={price}&publisher={publisher}", List.class,category,title, author, price, publisher );
		 return ResponseEntity.ok(result);
		
	}

}
