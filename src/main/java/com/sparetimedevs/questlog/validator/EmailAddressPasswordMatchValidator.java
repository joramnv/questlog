package com.sparetimedevs.questlog.validator;

import com.sparetimedevs.questlog.userpassword.UserPassword;
import com.sparetimedevs.questlog.userpassword.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressPasswordMatchValidator {

	private final UserPasswordRepository userPasswordRepository;

	@Autowired
	public EmailAddressPasswordMatchValidator(UserPasswordRepository userPasswordRepository) {
		this.userPasswordRepository = userPasswordRepository;
	}

	public void validate(String emailAddress, String password) {
		UserPassword userPassword = userPasswordRepository.findByUserEmailAddress(emailAddress);

		if (userPassword == null || !password.equals(userPassword.getPassword())) {
			throw new IllegalArgumentException(); //TODO make this throw some api exception
		}
	}
}
