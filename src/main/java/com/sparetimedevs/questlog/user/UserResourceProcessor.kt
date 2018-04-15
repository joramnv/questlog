package com.sparetimedevs.questlog.user

import com.sparetimedevs.questlog.login.LoginController
import com.sparetimedevs.questlog.userpassword.UserPasswordController
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class UserResourceProcessor : ResourceProcessor<Resource<User>> {

	override fun process(resource: Resource<User>): Resource<User> {
		resource.add(linkTo(UserPasswordController::class.java).withRel("save_password"))
		resource.add(linkTo(LoginController::class.java).withRel("login"))
		return resource
	}
}
