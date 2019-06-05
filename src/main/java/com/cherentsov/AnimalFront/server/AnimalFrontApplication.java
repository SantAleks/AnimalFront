package com.cherentsov.AnimalFront.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimalFrontApplication {
	private static final Log logger = LogFactory.getLog(AnimalFrontApplication.class);
	public static void main(String[] args) {

		SpringApplication.run(AnimalFrontApplication.class, args);
		logger.info("AnimalFront - Start");
	}

}
