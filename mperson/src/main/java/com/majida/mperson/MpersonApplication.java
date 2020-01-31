package com.majida.mperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MpersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpersonApplication.class, args);
	}

}
