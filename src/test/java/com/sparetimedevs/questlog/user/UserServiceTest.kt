package com.sparetimedevs.questlog.user

import com.sparetimedevs.questlog.login.Login
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import testsetup.EMAIL_ADDRESS_1
import testsetup.PASSWORD_1
import testsetup.userId1
import java.util.Optional

class UserServiceTest: StringSpec() {

	init {

		val userRepository: UserRepository = Mockito.mock(UserRepository::class.java)
		val userService = UserService(userRepository)

		val user = User(userId1, EMAIL_ADDRESS_1)
		val login = Login(user.emailAddress, PASSWORD_1)

		"givenLoginWithEmailAddressThatIsFindableWhenGetUserIdReturnsUsersId" {
			`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.of(user))

			val userId = userService.getUserId(login)
			userId shouldBe userId1
		}

		"givenLoginWithEmailAddressThatIsNotFindableWhenGetUserIsThrowsRuntimeException" {
			`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.empty())

			assertThrows<RuntimeException> {
				userService.getUserId(login)
			}
		}
	}
}
