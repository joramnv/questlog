package com.sparetimedevs.questlog.user

import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import org.springframework.stereotype.Component

@Component
class UserLookUpByEmailAddress : RepositoryRestConfigurerAdapter() {

	override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration?) {
		config!!.withEntityLookup()
				.forRepository<User, String, UserRepository>(UserRepository::class.java, { it.emailAddress }, { userRepository, emailAddress -> userRepository.findByEmailAddress(emailAddress) })
	}
}
