package com.sparetimedevs.questlog.login.validator;

import com.sparetimedevs.questlog.login.Login;
import com.sparetimedevs.questlog.user.UserService;
import com.sparetimedevs.questlog.userpassword.UserPassword;
import com.sparetimedevs.questlog.userpassword.UserPasswordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import testsupport.MockitoExtension;

import java.util.UUID;

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
	private UserService userService;

	@Mock
	private UserPasswordService userPasswordService;

	@Test
	void givenMatchingEmailAddressAndPasswordWhenValidateIsCalledResultsInNoException() throws Exception {
		UUID userId = UUID.randomUUID();
		UUID userPasswordId = UUID.randomUUID();
		String emailAddress = "ab@cd.ef";
		String password = "ghijkl";
		UserPassword userPassword = new UserPassword(userPasswordId, userId, password);
		Login login = new Login(emailAddress, password);

		when(userService.getUserId(login)).thenReturn(userId);
		when(userPasswordService.getUserPassword(userId)).thenReturn(userPassword);

		UUID returnedUserId = loginValidator.validate(login);

		assertThat(userPassword.getPassword(), is(equalTo(password)));
		assertThat(returnedUserId, is(equalTo(userId)));
	}

	@Test
	void givenMatchingEmailAddressAndPasswordWhenValidateIsCalledThrowsEmailAddressPasswordDoNotMatchException() throws Exception {
		UUID userId = UUID.randomUUID();
		UUID userPasswordId = UUID.randomUUID();
		String emailAddress = "ab@cd.ef";
		String password = "ghijkl";
		String notMatchingPassword = "mnopqrs";
		UserPassword userPassword = new UserPassword(userPasswordId, userId, password);
		Login login = new Login(emailAddress, notMatchingPassword);

		when(userService.getUserId(login)).thenReturn(userId);
		when(userPasswordService.getUserPassword(userId)).thenReturn(userPassword);

		assertThrows(EmailAddressPasswordDoNotMatchException.class, () -> {
			loginValidator.validate(login);
		});
		assertThat(userPassword.getPassword(), is(not(equalTo(notMatchingPassword))));
	}
}
//TODO write kotlin test?