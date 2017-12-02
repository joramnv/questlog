package com.sparetimedevs.questlog.validator;

import testsupport.MockitoExtension;
import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.userpassword.UserPassword;
import com.sparetimedevs.questlog.userpassword.UserPasswordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailAddressPasswordMatchValidatorTest {

	@InjectMocks
	private EmailAddressPasswordMatchValidator emailAddressPasswordMatchValidator;

	@Mock
	private UserPasswordRepository userPasswordRepository;

	@Test
	void givenMatchingEmailAddressAndPasswordWhenValidateIsCalledResultsInNoException() throws Exception {
		String emailAddress = "ab@cd.ef";
		String password = "ghijkl";
		User user = new User();
		user.setEmailAddress(emailAddress);
		UserPassword userPassword = new UserPassword(user, password);

		when(userPasswordRepository.findByUserEmailAddress(emailAddress)).thenReturn(userPassword);

		emailAddressPasswordMatchValidator.validate(emailAddress, password);

		assertThat(userPassword.getPassword(), is(equalTo(password)));
	}

	@Test
	void givenMatchingEmailAddressAndPasswordWhenValidateIsCalledThrowsIllegalArgumentException() throws Exception {
		String emailAddress = "ab@cd.ef";
		String password = "ghijkl";
		String notMatchingPassword = "mnopqrs";
		User user = new User();
		user.setEmailAddress(emailAddress);
		UserPassword userPassword = new UserPassword(user, password);

		when(userPasswordRepository.findByUserEmailAddress(emailAddress)).thenReturn(userPassword);

		assertThrows(IllegalArgumentException.class, () -> {
			emailAddressPasswordMatchValidator.validate(emailAddress, notMatchingPassword);
		});
		assertThat(userPassword.getPassword(), is(not(equalTo(notMatchingPassword))));
	}
}
