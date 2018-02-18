package com.sparetimedevs.questlog.login.validator

import com.sparetimedevs.questlog.login.Login
import com.sparetimedevs.questlog.login.exception.EmailAddressPasswordDoNotMatchException
import com.sparetimedevs.questlog.user.UserService
import com.sparetimedevs.questlog.userpassword.UserPassword
import com.sparetimedevs.questlog.userpassword.UserPasswordService
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not
import org.junit.jupiter.api.Assertions.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import test.EMAIL_ADDRESS_1
import test.PASSWORD_1
import test.PASSWORD_2
import test.userId1
import test.userPasswordId1

class EmailAddressPasswordMatchValidatorTest : StringSpec({

	val userService: UserService = mock(UserService::class.java)
	val userPasswordService: UserPasswordService = mock(UserPasswordService::class.java)
	val loginValidator = LoginValidator(userService, userPasswordService)

	val userPassword = UserPassword(userPasswordId1, userId1, PASSWORD_1)

	"given matching email address and password when validate then users id is returned" {
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_1)

		`when`(userService.getUserId(login)).thenReturn(userId1)
		`when`(userPasswordService.getUserPassword(userId1)).thenReturn(userPassword)

		val returnedUserId = loginValidator.validate(login)

		userPassword.password shouldBe PASSWORD_1
		returnedUserId shouldBe userId1
	}

	"given matching email address and password when validate then EmailAddressPasswordDoNotMatchException is thrown" {
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_2)

		`when`(userService.getUserId(login)).thenReturn(userId1)
		`when`(userPasswordService.getUserPassword(userId1)).thenReturn(userPassword)

		assertThrows(EmailAddressPasswordDoNotMatchException::class.java) { loginValidator.validate(login) }
		assertThat(userPassword.password, `is`(not(equalTo(PASSWORD_2))))
		//TODO use kotlintest instead of assertThat..
	}

})
