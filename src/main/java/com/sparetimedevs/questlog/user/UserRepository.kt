package com.sparetimedevs.questlog.user

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import java.util.Optional

interface UserRepository : PagingAndSortingRepository<User, Long> {

	fun findByEmailAddress(@Param("EMAIL_ADDRESS") emailAddress: String): Optional<User>

}
