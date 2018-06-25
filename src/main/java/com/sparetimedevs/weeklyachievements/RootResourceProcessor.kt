package com.sparetimedevs.weeklyachievements

import com.sparetimedevs.weeklyachievements.login.LoginController
import org.springframework.data.rest.webmvc.RepositoryLinksResource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.stereotype.Component

import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

@Component
class RootResourceProcessor : ResourceProcessor<RepositoryLinksResource> {

	override fun process(resource: RepositoryLinksResource): RepositoryLinksResource {
		resource.add(linkTo(LoginController::class.java).withRel("login"))
		return resource
	}
}
