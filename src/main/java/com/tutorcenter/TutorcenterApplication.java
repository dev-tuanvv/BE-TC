package com.tutorcenter;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TutorcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorcenterApplication.class, args);
		System.out.println(new Date(System.currentTimeMillis()));
	}

}
