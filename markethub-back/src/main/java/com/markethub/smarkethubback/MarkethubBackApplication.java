package com.markethub.smarkethubback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarkethubBackApplication {

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

	public static void main(String[] args) {
		SpringApplication.run(MarkethubBackApplication.class, args);
	}

}
