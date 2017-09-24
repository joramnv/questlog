package com.sparetimedevs.questlog.login;

import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class LoginController {

	private final RepositoryEntityLinks entityLinks;

	@Autowired
	public LoginController(RepositoryEntityLinks entityLinks) {
		this.entityLinks = entityLinks;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HttpEntity<Login> login(
			@RequestParam(value = "email_address", defaultValue = "user_email_address") String emailAddress,
			@RequestParam(value = "password", defaultValue = "user_password") String password) {

		//TODO validate login

		Login login = new Login(emailAddress, password);
		login.add(linkTo(methodOn(LoginController.class).login(emailAddress, password)).withSelfRel());

		Link link = entityLinks.linkToSingleResource(UserRepository.class, emailAddress);
		login.add(link);

		return new ResponseEntity<Login>(login, HttpStatus.OK);
	}

}
