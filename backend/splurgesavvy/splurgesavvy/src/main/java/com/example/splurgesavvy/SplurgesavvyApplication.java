package com.example.splurgesavvy;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.splurgesavvy")
@EntityScan("com.example.splurgesavvy")
public class SplurgesavvyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplurgesavvyApplication.class, args);
	}
	
	// Method to start H2 web server and TCP server
    private static void startH2Servers() {
        try {
            Server.createWebServer().start();
            Server.createTcpServer().start();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}

}
