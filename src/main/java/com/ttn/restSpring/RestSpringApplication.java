package com.ttn.restSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ttn.restSpring.entity")
@EnableJpaRepositories("com.ttn.restSpring.repository")
public class RestSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestSpringApplication.class, args);
	}

}
