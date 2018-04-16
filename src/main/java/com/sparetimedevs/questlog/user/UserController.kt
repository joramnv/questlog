package com.sparetimedevs.questlog.user

import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["user"])
class UserController(private val userRepository: UserRepository) {

	@RequestMapping(path = ["{emailAddress:.+}"], method = [RequestMethod.GET], produces = [MediaTypes.HAL_JSON_VALUE])
	@ResponseBody
	fun findUserByEmailAddress(@PathVariable emailAddress: String): ResponseEntity<User> {
		val user = userRepository.findByEmailAddress(emailAddress)
				.orElseThrow { UserNotFoundException("User with e-mail address $emailAddress not found.") }
		return ResponseEntity(user, HttpStatus.OK)
	}
}
