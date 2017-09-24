package com.sparetimedevs.questlog.userpassword;

import com.sparetimedevs.questlog.login.Login;
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
public class UserPasswordController {

	private final RepositoryEntityLinks entityLinks;
	private final UserRepository userRepository;
	private final UserPasswordRepository userPasswordRepository;

	@Autowired
	public UserPasswordController(RepositoryEntityLinks entityLinks, UserRepository userRepository, UserPasswordRepository userPasswordRepository) {
		this.entityLinks = entityLinks;
		this.userRepository = userRepository;
		this.userPasswordRepository = userPasswordRepository;
	}

	@RequestMapping(value = "/save_password", method = RequestMethod.POST)
	public HttpEntity<Login> savePassword(
			@RequestParam(value = "email_address", defaultValue = "user_email_address") String emailAddress,
			@RequestParam(value = "password", defaultValue = "user_password") String password) {

		User user = userRepository.findOne(emailAddress);
		UserPassword userPassword = new UserPassword(user, password);
		userPassword = userPasswordRepository.save(userPassword);

		Login login = new Login(userPassword.getUser().getEmailAddress(), userPassword.getPassword());
		login.add(linkTo(methodOn(UserPasswordController.class).savePassword(emailAddress, password)).withSelfRel());

		Link link = entityLinks.linkToSingleResource(UserRepository.class, user.getEmailAddress());
		login.add(link);

		return new ResponseEntity<Login>(login, HttpStatus.OK);
	}

}
