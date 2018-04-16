package com.sparetimedevs.questlog.login.validator

import com.sparetimedevs.questlog.login.Login
import com.sparetimedevs.questlog.userpassword.UserPasswordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils.isEmpty

@Component
class LoginValidator @Autowired
constructor( @Autowired private val userPasswordRepository: UserPasswordRepository) {

    fun validate(login: Login) {
        val userPassword = userPasswordRepository.findByUserEmailAddress(login.emailAddress)
                .orElseThrow { RuntimeException("User password for e-mail address " + login.emailAddress + " not found.") } //TODO throw different error.

        if (isEmpty(userPassword) || login.password != userPassword.password) {
            throw EmailAddressPasswordDoNotMatchException("E-mail address password combination do not match for e-mail address " + login.emailAddress + ".") //TODO catch this somewhere in apiresponse and throw some api error code/response
        }
    }
}
