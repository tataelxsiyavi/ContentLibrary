package com.content;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.content.config.ContentConfig;


@SpringBootApplication
@EnableConfigurationProperties({ContentConfig.class})
public class ContentLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentLibraryApplication.class, args);
	}

}
