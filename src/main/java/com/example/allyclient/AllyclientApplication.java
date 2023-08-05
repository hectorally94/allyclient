package com.example.allyclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class AllyclientApplication {
	public static void main(String[] args) {
		SpringApplication.run(AllyclientApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate (){
		return new RestTemplate();
	}

}
