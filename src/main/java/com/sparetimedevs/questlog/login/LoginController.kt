package com.sparetimedevs.questlog.login

import com.sparetimedevs.questlog.login.validator.LoginValidator
import com.sparetimedevs.questlog.quest.QuestController
import com.sparetimedevs.questlog.user.UserService
import com.sparetimedevs.questlog.userpassword.UserPasswordController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.ArrayList

@RestController
@RequestMapping(value = ["/login"])
class LoginController
@Autowired
constructor(
		private val loginValidator: LoginValidator,
		private val userService: UserService
) {
	@RequestMapping(method = [POST], consumes = [APPLICATION_JSON_VALUE], produces = [HAL_JSON_VALUE])
	fun login(@RequestBody loginResource: Resource<Login>?): HttpEntity<Login> {
		val login = loginResource!!.content

		loginValidator.validate(login)

		val linkToSelf = linkTo(methodOn(LoginController::class.java).login(loginResource)).withSelfRel()

		val userId = userService.getUserId(login)
		val linkToUserQuests = linkTo(methodOn(QuestController::class.java).findUserQuests(userId)).withRel("user-quests")

		val linkToUserPassword = linkTo(UserPasswordController::class.java).withRel("save-password")

		login.add(linkToSelf)
		login.add(linkToUserQuests)
		login.add(linkToUserPassword)

		return ResponseEntity(login, HttpStatus.OK)
	}

	@RequestMapping(method = [GET], produces = [HAL_JSON_VALUE])
	@ResponseBody
	fun login(): ResponseEntity<*> {
		val producers = ArrayList<String>()
		val resources = Resources(producers)
		resources.add(linkTo(methodOn(LoginController::class.java).login()).withSelfRel())
		return ResponseEntity.ok(resources)
	}
}
