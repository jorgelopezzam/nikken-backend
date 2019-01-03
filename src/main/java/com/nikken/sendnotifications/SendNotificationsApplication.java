package com.nikken.sendnotifications;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {
        "com.nikken.sendnotifications"})
public class SendNotificationsApplication {

    private static final Logger logger = LogManager.getLogger(SendNotificationsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SendNotificationsApplication.class, args);
	}

}
