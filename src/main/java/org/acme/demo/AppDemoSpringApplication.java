package org.acme.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppDemoSpringApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppDemoSpringApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppDemoSpringApplication.class);
	}

}
