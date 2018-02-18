package com.sparetimedevs.questlog.quest

import com.sparetimedevs.questlog.user.User
import io.kotlintest.matchers.collections.contain
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import test.data.testQuest1
import test.data.testQuest2
import test.userId1

internal class QuestControllerTest : StringSpec({

	val repositoryEntityLinks: RepositoryEntityLinks = mock(RepositoryEntityLinks::class.java)
	val questRepository: QuestRepository = mock(QuestRepository::class.java)
	val questController = QuestController(repositoryEntityLinks, questRepository)

	"given valid user id when find user quests then response containing user quests is returned" {
		val quests = ArrayList<Quest>()
		quests.add(testQuest1)
		quests.add(testQuest2)
		`when`(questRepository.findByUserId(userId1)).thenReturn(quests)

		val link = Link("test_string")
		`when`(repositoryEntityLinks.linkToSingleResource(User::class.java, userId1)).thenReturn(link)

		val response = questController.findUserQuests(userId1)

		response.body!!.content should contain(testQuest1).and(contain(testQuest2))
		response.statusCode shouldBe HttpStatus.OK
	}

	"given I write more tests when I have time then I can determine if I like this" {

		//TODO write more tests.

	}
})
