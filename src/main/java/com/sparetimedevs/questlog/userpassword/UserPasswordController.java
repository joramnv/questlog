package com.sparetimedevs.questlog.userpassword;

import com.sparetimedevs.questlog.login.Login;
import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
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
@RequestMapping(value = "/save_password")
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

	@RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = HAL_JSON_VALUE)
	public HttpEntity<Login> createPassword(@RequestBody Resource<Login>  loginResource) {
		Login login = loginResource.getContent();

		User user = userRepository.findByEmailAddress(login.getEmailAddress());
		UserPassword userPassword = new UserPassword(user, login.getPassword());
		try {
			userPasswordRepository.save(userPassword);
		} catch (Exception e) {
			//TODO catch right hibernate constraint exception.
		}

		login.add(linkTo(methodOn(UserPasswordController.class).createPassword(loginResource)).withSelfRel());

		Link link = entityLinks.linkToSingleResource(User.class, user.getEmailAddress());
		login.add(link);

		return new ResponseEntity<Login>(login, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE, produces = HAL_JSON_VALUE)
	public HttpEntity<Login> updatePassword(@RequestBody Resource<Login>  loginResource) {
		Login login = loginResource.getContent();

		User user = userRepository.findByEmailAddress(login.getEmailAddress());
		UserPassword userPassword = userPasswordRepository.findByUser(user);
		userPassword.setPassword(login.getPassword());
		userPasswordRepository.save(userPassword);

		login.add(linkTo(methodOn(UserPasswordController.class).createPassword(loginResource)).withSelfRel());

		Link link = entityLinks.linkToSingleResource(User.class, user.getEmailAddress());
		login.add(link);

		return new ResponseEntity<Login>(login, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = HAL_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> savePassword() {
		List<String> producers = new ArrayList<>();
		Resources<String> resources = new Resources<>(producers);
		resources.add(linkTo(methodOn(UserPasswordController.class).createPassword(null)).withSelfRel());
		return ResponseEntity.ok(resources);
	}
}
