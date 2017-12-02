package com.sparetimedevs.questlog.login;

import com.sparetimedevs.questlog.quest.Quest;
import com.sparetimedevs.questlog.userpassword.UserPasswordController;
import com.sparetimedevs.questlog.validator.EmailAddressPasswordMatchValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	private final RepositoryEntityLinks entityLinks;
	private final EmailAddressPasswordMatchValidator emailAddressPasswordMatchValidator;

	@Autowired
	public LoginController(RepositoryEntityLinks entityLinks, EmailAddressPasswordMatchValidator emailAddressPasswordMatchValidator) {
		this.entityLinks = entityLinks;
		this.emailAddressPasswordMatchValidator = emailAddressPasswordMatchValidator;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = HAL_JSON_VALUE)
	public HttpEntity<Login> login(@RequestBody Resource<Login>  loginResource) {
		Login login = loginResource.getContent();

		emailAddressPasswordMatchValidator.validate(login.getEmailAddress(), login.getPassword());

		Link linkToSelf = linkTo(methodOn(LoginController.class).login(loginResource)).withSelfRel();
		Link linkToQuests = entityLinks.linkFor(Quest.class, login.getEmailAddress()).withRel("quests");
		Link linkToUserPassword = linkTo(UserPasswordController.class).withRel("save_password");

		login.add(linkToSelf);
		login.add(linkToQuests);
		login.add(linkToUserPassword);

		return new ResponseEntity<Login>(login, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = HAL_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> login() {
		List<String> producers = new ArrayList<>();
		Resources<String> resources = new Resources<>(producers);
		resources.add(linkTo(methodOn(LoginController.class).login(null)).withSelfRel());
		return ResponseEntity.ok(resources);
	}
}
