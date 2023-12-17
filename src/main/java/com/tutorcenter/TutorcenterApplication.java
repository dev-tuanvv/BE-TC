package com.tutorcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TutorcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorcenterApplication.class, args);
	}

}
