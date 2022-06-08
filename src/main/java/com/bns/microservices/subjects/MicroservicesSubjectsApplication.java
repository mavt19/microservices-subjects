package com.bns.microservices.subjects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicesSubjectsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesSubjectsApplication.class, args);
	}

}
