package com.sparetimedevs.questlog.user;

import com.sparetimedevs.questlog.userpassword.UserPasswordController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserResourceProcessor implements ResourceProcessor<Resource<User>> {

	@Override
	public Resource<User> process(Resource<User> resource) {
		resource.add(linkTo(UserPasswordController.class).withRel("save_password"));
		return resource;
	}
}
