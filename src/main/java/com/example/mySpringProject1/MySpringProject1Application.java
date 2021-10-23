package com.example.mySpringProject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MySpringProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(MySpringProject1Application.class, args);
	}

}
