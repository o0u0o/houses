package com.aiuiot.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.aiuiot.house.web.autoconfig.EnableHttpClient;

@SpringBootApplication()
@EnableHttpClient
@EnableAsync 			//该注解开启异步框架进行异步操作
public class HousesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousesApplication.class, args);
	}

}
