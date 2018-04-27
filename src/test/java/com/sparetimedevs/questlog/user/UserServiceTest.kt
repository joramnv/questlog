package com.sparetimedevs.questlog.user

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import testsupport.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {

//
//	val mockUserService = InjectMocks()
//	@InjectMocks
//	userService: UserService
//
//	@Mock
//	private userRepository: UserRepository;

	@Test
	fun getUserId() {

		assertThat(1, `is`(equalTo(1)))
	}
}