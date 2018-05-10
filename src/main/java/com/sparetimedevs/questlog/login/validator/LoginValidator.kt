package com.sparetimedevs.questlog.login.validator

import com.sparetimedevs.questlog.login.Login
import com.sparetimedevs.questlog.user.UserService
import com.sparetimedevs.questlog.userpassword.UserPasswordService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class LoginValidator(
		private val userService: UserService,
		private val userPasswordService: UserPasswordService
) {
    fun validate(login: Login): UUID {
	    val userId = userService.getUserId(login)
        val userPassword = userPasswordService.getUserPassword(userId)

        if (login.password != userPassword.password) {
            throw EmailAddressPasswordDoNotMatchException("E-mail address password combination do not match for e-mail address " + login.emailAddress + ".") //TODO catch this somewhere in apiresponse and throw some api error code/response
        }
	    return userId
    }
}
