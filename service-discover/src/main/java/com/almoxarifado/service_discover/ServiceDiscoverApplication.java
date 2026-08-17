package com.almoxarifado.service_discover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoverApplication.class, args);
	}

}
