package com.majida.mperson.mperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class MpersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpersonApplication.class, args);
	}

}
