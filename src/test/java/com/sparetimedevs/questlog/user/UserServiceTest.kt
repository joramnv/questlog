package com.sparetimedevs.questlog.user

import com.sparetimedevs.questlog.login.Login
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import test.EMAIL_ADDRESS_1
import test.PASSWORD_1
import test.userId1
import java.util.Optional

class UserServiceTest : StringSpec({

	val userRepository: UserRepository = mock(UserRepository::class.java)
	val userService = UserService(userRepository)

	val user = User(userId1, EMAIL_ADDRESS_1)
	val login = Login(user.emailAddress, PASSWORD_1)

	"given login with email address that is findable when get user id then users id is returned" {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.of(user))

		val userId = userService.getUserId(login)
		userId shouldBe userId1
	}

	"given login with email address that is not findable when get user id then RuntimeException is thrown" {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.empty())

		assertThrows<RuntimeException> {        		//TODO use kotlintest instead of assertThat..
			userService.getUserId(login)
		}
	}

})
