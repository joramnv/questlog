package com.sparetimedevs.questlog.user

import com.sparetimedevs.questlog.login.LoginController
import com.sparetimedevs.questlog.quest.QuestController
import com.sparetimedevs.questlog.userpassword.UserPasswordController
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class UserResourceProcessor : ResourceProcessor<Resource<User>> {

	override fun process(resource: Resource<User>): Resource<User> {
		resource.add(linkTo(methodOn(QuestController::class.java).findUserQuests(resource.content.id)).withRel("user-quests"))
		resource.add(linkTo(UserPasswordController::class.java).withRel("save-password"))
		resource.add(linkTo(LoginController::class.java).withRel("login"))
		return resource
	}
}
