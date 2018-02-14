package com.mercury.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

	@SpringBootApplication
	@ComponentScan({"com.mercury"})
	public class MercuryApplication extends SpringBootServletInitializer {

	    @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(MercuryApplication.class);
	    }

	    public static void main(String[] args) throws Exception {
	        SpringApplication.run(MercuryApplication.class, args);
	    }
}
