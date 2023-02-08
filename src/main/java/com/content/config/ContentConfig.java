package com.content.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.content.util.AppConstants;


@ConfigurationProperties(prefix =AppConstants.FILE_PROPERTIES_PREFIX)
public class ContentConfig {
	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
}
