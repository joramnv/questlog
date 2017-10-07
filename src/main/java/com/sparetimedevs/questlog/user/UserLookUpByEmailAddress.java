package com.sparetimedevs.questlog.user;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class UserLookUpByEmailAddress extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.withEntityLookup()
				.forRepository(UserRepository.class, User::getEmailAddress, UserRepository::findByEmailAddress);
	}
}
