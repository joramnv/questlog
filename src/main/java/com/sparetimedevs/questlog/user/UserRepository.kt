package com.sparetimedevs.questlog.user

import java.util.Optional
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
interface UserRepository : PagingAndSortingRepository<User, Long> {

	@RestResource(exported = true)
	fun findByEmailAddress(@Param("EMAIL_ADDRESS") emailAddress: String): Optional<User>

	@RestResource(exported = false)
	override fun findById(id: Long): Optional<User>
}
