package com.sparetimedevs.questlog.validator;

import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
import com.sparetimedevs.questlog.userpassword.UserPassword;
import com.sparetimedevs.questlog.userpassword.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressPasswordMatchValidator {

	private final UserRepository userRepository;
	private final UserPasswordRepository userPasswordRepository;

	@Autowired
	public EmailAddressPasswordMatchValidator(UserRepository userRepository, UserPasswordRepository userPasswordRepository) {
		this.userRepository = userRepository;
		this.userPasswordRepository = userPasswordRepository;
	}

	public void validate(String emailAddress, String password) {
		User user = userRepository.findOne(emailAddress);
		UserPassword userPassword = userPasswordRepository.findByUser(user);

		if (userPassword == null || !password.equals(userPassword.getPassword())) {
			throw new IllegalArgumentException(); //TODO make this throw some api exception
		}
	}

}
