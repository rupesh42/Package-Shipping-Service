package com.abnamro.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.abnamro.assignment")
public class PackageShippingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackageShippingServiceApplication.class, args);
	}

}
