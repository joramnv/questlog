package com.sparetimedevs.questlog.user

import com.sparetimedevs.questlog.login.Login
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks
import java.util.Optional
import java.util.UUID

internal class UserServiceTest {

	@InjectMocks
	private lateinit var userService: UserService

	@Mock
	private lateinit var userRepository: UserRepository

	private val id = UUID.randomUUID()
	private val emailAddress = "abc"
	private val password = "def"
	private val user = User(id, emailAddress)
	private val login = Login(user.emailAddress, password)

	@BeforeEach
	internal fun init() {
		initMocks(this)
	}

	@Test
	internal fun givenLoginWithEmailAddressThatIsFindableWhenGetUserIdReturnsUsersId() {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.of(user))

		val userId = userService.getUserId(login)
		assertThat(userId, `is`(equalTo(id)))
	}

	@Test
	internal fun givenLoginWithEmailAddressThatIsNotFindableWhenGetUserIsThrowsRuntimeException() {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.empty())

		assertThrows<RuntimeException> {
			userService.getUserId(login)
		}
	}
}
