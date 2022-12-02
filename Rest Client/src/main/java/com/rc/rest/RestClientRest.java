package com.rc.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rc.model.UserVo;

@RestController
public class RestClientRest {

	List<UserVo> user = new ArrayList<UserVo>();

	@GetMapping("/demo")
	public ResponseEntity<List<UserVo>> getUserDetails() {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8082/um/getAllUsers";

		try {
			user = (List<UserVo>) restTemplate.getForObject(url, List.class);

			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(user);
		}

	}

}
