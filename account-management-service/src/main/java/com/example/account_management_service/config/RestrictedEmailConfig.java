package com.example.account_management_service.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * fetch the configuration for restricted mails from global configuration.
 */
@Component
@ConfigurationProperties(prefix = "restricted")
public class RestrictedEmailConfig {
	private List<String> emails;
	
	public List<String> getEmails(){
		return emails;
	}
	
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

}
