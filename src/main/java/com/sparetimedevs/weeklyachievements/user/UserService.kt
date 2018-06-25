package com.sparetimedevs.weeklyachievements.user

import com.sparetimedevs.weeklyachievements.login.Login
import com.sparetimedevs.weeklyachievements.user.exception.UserNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserService(
		private val userRepository: UserRepository
) {
	fun getUserId(login: Login): UUID {
		val user = userRepository.findByEmailAddress(login.emailAddress)
				.orElseThrow { UserNotFoundException("User with e-mail address " + login.emailAddress + " not found.") }
		return user.id
	}
}
