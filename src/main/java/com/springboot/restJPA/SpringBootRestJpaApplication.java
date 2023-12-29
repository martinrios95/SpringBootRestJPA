package com.springboot.restJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SpringBootRestJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestJpaApplication.class, args);
	}

}
