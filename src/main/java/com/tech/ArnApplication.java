package com.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ArnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArnApplication.class, args);
	}

}
