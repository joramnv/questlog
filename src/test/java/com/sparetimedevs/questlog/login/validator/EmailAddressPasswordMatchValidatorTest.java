package com.sparetimedevs.questlog.login.validator;

import com.sparetimedevs.questlog.login.Login;
import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.userpassword.UserPassword;
import com.sparetimedevs.questlog.userpassword.UserPasswordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import testsupport.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailAddressPasswordMatchValidatorTest {

	@InjectMocks
	private LoginValidator loginValidator;

	@Mock
	private UserPasswordRepository userPasswordRepository;

	@Test
	void givenMatchingEmailAddressAndPasswordWhenValidateIsCalledResultsInNoException() throws Exception {
		String emailAddress = "ab@cd.ef";
		String password = "ghijkl";
		User user = new User(emailAddress);
		UserPassword userPassword = new UserPassword(user, password);
		Login login = new Login(emailAddress, password);

		when(userPasswordRepository.findByUserEmailAddress(emailAddress)).thenReturn(userPassword);

		loginValidator.validate(login);

		assertThat(userPassword.getPassword(), is(equalTo(password)));
	}

	@Test
	void givenMatchingEmailAddressAndPasswordWhenValidateIsCalledThrowsIllegalArgumentException() throws Exception {
		String emailAddress = "ab@cd.ef";
		String password = "ghijkl";
		String notMatchingPassword = "mnopqrs";
		User user = new User(emailAddress);
		UserPassword userPassword = new UserPassword(user, password);
		Login login = new Login(emailAddress, notMatchingPassword);

		when(userPasswordRepository.findByUserEmailAddress(emailAddress)).thenReturn(userPassword);

		assertThrows(EmailAddressPasswordDoNotMatchException.class, () -> {
			loginValidator.validate(login);
		});
		assertThat(userPassword.getPassword(), is(not(equalTo(notMatchingPassword))));
	}
}
//TODO write kotlin test?