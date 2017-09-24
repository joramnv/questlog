package com.sparetimedevs.questlog.login;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Login extends ResourceSupport {

	private final String emailAddress;
	private final String password;

	@JsonCreator
	public Login(@JsonProperty("emailAddress") String emailAddress, @JsonProperty("password") String password) {
		this.emailAddress = emailAddress;
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getPassword() {
		return password;
	}

}
