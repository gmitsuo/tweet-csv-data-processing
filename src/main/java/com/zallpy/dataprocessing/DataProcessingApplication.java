package com.zallpy.dataprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DataProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataProcessingApplication.class, args);
	}

}
