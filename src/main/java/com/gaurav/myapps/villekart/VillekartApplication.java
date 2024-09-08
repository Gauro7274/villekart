package com.gaurav.myapps.villekart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
public class VillekartApplication {

	public static void main(String[] args) {
		SpringApplication.run(VillekartApplication.class, args);
	}

}
