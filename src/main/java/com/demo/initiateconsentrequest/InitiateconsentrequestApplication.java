package com.demo.initiateconsentrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.demo.controller"})
@EnableFeignClients("com.demo.feignClient")
public class InitiateconsentrequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitiateconsentrequestApplication.class, args);
	}

}
