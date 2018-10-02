package com.apache.camel.sql.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.apache.camel.sql.component")
public class ApacheCamelSqlComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApacheCamelSqlComponentApplication.class, args);
	}
}
