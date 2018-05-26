package com.gitfeeling.restadvertiser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.gitfeeling.restadvertiser.dao.AdvertiserDao;

@SpringBootApplication
@ComponentScan(basePackages = "com")
@EnableAsync
@EnableAutoConfiguration
public class Application implements CommandLineRunner {
	
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AdvertiserDao repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("Advertiser 1 -> {}", repository.findByName("Ogilvy"));
	}	
}
