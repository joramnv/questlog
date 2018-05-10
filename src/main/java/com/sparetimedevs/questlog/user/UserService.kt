package com.sparetimedevs.questlog.user

import com.sparetimedevs.questlog.login.Login
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserService(
		private val userRepository: UserRepository
) {
	fun getUserId(login: Login): UUID {
		val user = userRepository.findByEmailAddress(login.emailAddress)
				.orElseThrow { RuntimeException("User with e-mail address " + login.emailAddress + " not found.") } //TODO throw different error.
		return user.id
	}
}
