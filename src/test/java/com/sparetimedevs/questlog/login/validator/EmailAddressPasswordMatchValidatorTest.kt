package com.sparetimedevs.questlog.login.validator

import com.sparetimedevs.questlog.login.Login
import com.sparetimedevs.questlog.login.exception.EmailAddressPasswordDoNotMatchException
import com.sparetimedevs.questlog.user.UserService
import com.sparetimedevs.questlog.userpassword.UserPassword
import com.sparetimedevs.questlog.userpassword.UserPasswordService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import testsetup.EMAIL_ADDRESS_1
import testsetup.PASSWORD_1
import testsetup.PASSWORD_2
import testsetup.userId1
import testsetup.userPasswordId1
import testsupport.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class EmailAddressPasswordMatchValidatorTest {

	@InjectMocks
	private lateinit var loginValidator: LoginValidator

	@Mock
	private lateinit var userService: UserService

	@Mock
	private lateinit var userPasswordService: UserPasswordService

	@Test
	@Throws(Exception::class)
	fun givenMatchingEmailAddressAndPasswordWhenValidateIsCalledResultsInNoException() {
		val userPassword = UserPassword(userPasswordId1, userId1, PASSWORD_1)
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_1)

		`when`(userService.getUserId(login)).thenReturn(userId1)
		`when`(userPasswordService.getUserPassword(userId1)).thenReturn(userPassword)

		val returnedUserId = loginValidator.validate(login)

		assertThat(userPassword.password, `is`(equalTo(PASSWORD_1)))
		assertThat(returnedUserId, `is`(equalTo(userId1)))
	}

	@Test
	@Throws(Exception::class)
	fun givenMatchingEmailAddressAndPasswordWhenValidateIsCalledThrowsEmailAddressPasswordDoNotMatchException() {
		val userPassword = UserPassword(userPasswordId1, userId1, PASSWORD_1)
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_2)

		`when`(userService.getUserId(login)).thenReturn(userId1)
		`when`(userPasswordService.getUserPassword(userId1)).thenReturn(userPassword)

		assertThrows(EmailAddressPasswordDoNotMatchException::class.java) { loginValidator.validate(login) }
		assertThat(userPassword.password, `is`(not(equalTo(PASSWORD_2))))
	}
}
