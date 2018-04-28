package com.sparetimedevs.questlog.userpassword

import com.sparetimedevs.questlog.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserPasswordRepository : CrudRepository<UserPassword, Long> {

	fun findByUser(@Param("USER") user: User): Optional<UserPassword>

	fun findByUserEmailAddress(@Param("USER_EMAIL_ADDRESS") userEmailAddress: String): Optional<UserPassword>

}
