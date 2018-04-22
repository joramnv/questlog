//package com.sparetimedevs.questlog.user
//
//import com.sparetimedevs.questlog.quest.Quest
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks
//import org.springframework.hateoas.Link
//import org.springframework.hateoas.MediaTypes
//import org.springframework.hateoas.Resource
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod
//import org.springframework.web.bind.annotation.ResponseBody
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@RequestMapping(path = ["user"])
//class UserController
//@Autowired
//constructor(
//		private val entityLinks: RepositoryEntityLinks,
//		private val userRepository: UserRepository
//) {
//
//	@RequestMapping(path = ["{emailAddress:.+}"], method = [RequestMethod.GET], produces = [MediaTypes.HAL_JSON_VALUE])
//	@ResponseBody
//	fun findUserByEmailAddress(@PathVariable emailAddress: String): ResponseEntity<Resource<User>> {
//		val user = userRepository.findByEmailAddress(emailAddress)
//				.orElseThrow { UserNotFoundException("User with e-mail address $emailAddress not found.") }
//
//		val linkToUserQuest = entityLinks.linkToSingleResource(Quest::class.java, user.id)
//
//		val userResource = Resource(user, linkToUserQuest)
//		return ResponseEntity(userResource, HttpStatus.OK)
//	}
//}
