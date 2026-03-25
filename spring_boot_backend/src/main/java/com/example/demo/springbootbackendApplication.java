package com.example.springbootbackend;

import com.example.springbootbackend.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class springbootbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(springbootbackendApplication.class, args);
	}

}
