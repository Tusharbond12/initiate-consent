package com.hdfc.initiateconsentrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.hdfc.controller"})
@EnableFeignClients("com.hdfc.feignClient")
public class InitiateconsentrequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitiateconsentrequestApplication.class, args);
	}

}
