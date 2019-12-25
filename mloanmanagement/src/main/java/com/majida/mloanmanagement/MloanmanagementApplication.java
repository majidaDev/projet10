package com.majida.mloanmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class MloanmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MloanmanagementApplication.class, args);
	}

}
