package com.events.chalenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChalengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChalengeApplication.class, args);
	}

}
