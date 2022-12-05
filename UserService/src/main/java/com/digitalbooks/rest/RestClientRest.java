package com.digitalbooks.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.dto.BookDto;

@Component
public class RestClientRest {

	BookDto books = null;

	public ResponseEntity<BookDto> getBookDetails(String url,BookDto book) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<BookDto> entity = new HttpEntity<>(book,headers);
		try {
//			books = (List<BookVo>) restTemplate.getForObject("http://localhost:8084/book/" + url, BookVo.class);
			books=restTemplate.exchange("http://localhost:8084/book/" +url, HttpMethod.POST, entity, BookDto.class).getBody();

			return ResponseEntity.ok(books);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(books);
		}

	}

}
