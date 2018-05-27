package com.sparetimedevs.questlog.login

import com.sparetimedevs.questlog.login.validator.LoginValidator
import com.sparetimedevs.questlog.userpassword.UserPassword
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.hateoas.Resource
import org.springframework.http.HttpStatus
import testsetup.EMAIL_ADDRESS_1
import testsetup.PASSWORD_1
import testsetup.userId1
import testsetup.userPasswordId1

internal class LoginControllerTest : StringSpec({

	val loginValidator: LoginValidator = mock(LoginValidator::class.java)
	val loginController = LoginController(loginValidator)

	val userPassword = UserPassword(userPasswordId1, userId1, PASSWORD_1)

	"given matching email address and password when login is called then response login is returned??" {
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_1)
		val loginResource: Resource<Login> = Resource(login)

		`when`(loginValidator.validate(login)).thenReturn(userId1)

		val response = loginController.login(loginResource)

		response.statusCode shouldBe HttpStatus.OK
//		response.body shouldHave Matcher<Login>
		//TODO more assertions
	}

	//TODO write more tests.

})
