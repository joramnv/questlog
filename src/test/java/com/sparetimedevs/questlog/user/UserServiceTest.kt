package com.sparetimedevs.questlog.user

import com.sparetimedevs.questlog.login.Login
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import testsetup.EMAIL_ADDRESS_1
import testsetup.PASSWORD_1
import testsetup.userId1
import testsupport.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {

	@InjectMocks
	private lateinit var userService: UserService

	@Mock
	private lateinit var userRepository: UserRepository

	private val user = User(userId1, EMAIL_ADDRESS_1)
	private val login = Login(user.emailAddress, PASSWORD_1)

	@Test
	internal fun givenLoginWithEmailAddressThatIsFindableWhenGetUserIdReturnsUsersId() {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.of(user))

		val userId = userService.getUserId(login)
		assertThat(userId, `is`(equalTo(userId1)))
	}

	@Test
	internal fun givenLoginWithEmailAddressThatIsNotFindableWhenGetUserIsThrowsRuntimeException() {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.empty())

		assertThrows<RuntimeException> {
			userService.getUserId(login)
		}
	}
}
