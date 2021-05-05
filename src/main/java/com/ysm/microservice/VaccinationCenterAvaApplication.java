package com.ysm.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class VaccinationCenterAvaApplication {

	public static void main(String[] args) {
			
		SpringApplication.run(VaccinationCenterAvaApplication.class, args);
		 System.out.println("We will send notification, once Slot is available near your pincode/ district id");
			
	}
	
	@Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }

}
