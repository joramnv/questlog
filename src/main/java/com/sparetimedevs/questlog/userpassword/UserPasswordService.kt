package com.sparetimedevs.questlog.userpassword

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserPasswordService
@Autowired
constructor(
		private val userPasswordRepository: UserPasswordRepository
) {
	fun getUserPassword(userId: UUID): UserPassword {
		return userPasswordRepository.findByUserId(userId)
				.orElseThrow { RuntimeException("User password not found.") } //TODO throw different error.
	}
}
