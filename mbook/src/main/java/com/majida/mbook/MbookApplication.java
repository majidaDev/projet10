package com.majida.mbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
/*
Décalere ce microservice comme client doit aller s'enregistrer dans le registre Eureka
 */
@EnableDiscoveryClient
public class MbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MbookApplication.class, args);
	}

}
