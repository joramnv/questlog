package com.sparetimedevs.questlog.quest

import com.sparetimedevs.questlog.user.User
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.Resources
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class QuestController(
		private val repositoryEntityLinks: RepositoryEntityLinks,
		private val questRepository: QuestRepository
) {
	@RequestMapping(method = [RequestMethod.GET], produces = [(MediaTypes.HAL_JSON_VALUE)], path = ["quests/user/{userId}"])
	@ResponseBody
	fun findUserQuests(@PathVariable(value = "userId") userId: UUID): ResponseEntity<Resources<Quest>> {
		val userQuests = questRepository.findByUserId(userId)

		val userQuestsResources = Resources(userQuests)

		val linkToUser = repositoryEntityLinks.linkToSingleResource(User::class.java, userId)
		userQuestsResources.add(linkToUser)
		return ResponseEntity(userQuestsResources, HttpStatus.OK)
	}
}
