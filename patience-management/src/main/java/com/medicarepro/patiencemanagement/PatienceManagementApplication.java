package com.medicarepro.patiencemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PatienceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatienceManagementApplication.class, args);
	}

}
