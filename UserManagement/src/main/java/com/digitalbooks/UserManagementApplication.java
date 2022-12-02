package com.digitalbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ComponentScan
public class UserManagementApplication  {



	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
