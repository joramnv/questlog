package com.sparetimedevs.questlog.quest

import com.sparetimedevs.questlog.user.User
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks
import org.springframework.hateoas.Link
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import test.data.testQuest1
import test.data.testQuest2
import test.support.MockitoExtension
import test.userId1
import java.util.ArrayList

@ExtendWith(SpringExtension::class, MockitoExtension::class)
@WebMvcTest(QuestController::class)
@ContextConfiguration(classes = [QuestController::class])
internal class KnutselKotlin : StringSpec() {

	@Autowired
	private val mockMvc: MockMvc? = null

	@Autowired
	private val repositoryEntityLinks: RepositoryEntityLinks? = null

	@Autowired
	private val questRepository: QuestRepository? = null

	@TestConfiguration
	internal class EmployeeServiceImplTestContextConfiguration {

		@Bean
		fun repositoryEntityLinks(): RepositoryEntityLinks {
			return Mockito.mock(RepositoryEntityLinks::class.java)
		}

		@Bean
		fun questRepository(): QuestRepository {
			return Mockito.mock(QuestRepository::class.java)
		}
	}

//	@Test
//	@Throws(Exception::class)
//	fun shouldReturnRepositoryIndex() {
//		val quests = ArrayList<Quest>()
//		quests.add(testQuest1)
//		quests.add(testQuest2)
//		`when`(questRepository!!.findByUserId(userId1)).thenReturn(quests)
//
//		val link = Link("test_string")
//		`when`(repositoryEntityLinks!!.linkToSingleResource(User::class.java, userId1)).thenReturn(link)
//
//		//		val response = questController.findUserQuests(userId1)
//		//
//		//		response.body!!.content should contain(testQuest1).and(contain(testQuest2))
//		//		response.statusCode shouldBe HttpStatus.OK
//
//		mockMvc!!.perform(get("/quests/user/" + userId1.toString())).andDo(print()).andExpect(status().isOk).andExpect(
//				jsonPath("$._links.self").exists())
//	}

	init {

		"given valid user id when find user quests then response containing user quests is returned" {
			val quests = ArrayList<Quest>()
			quests.add(testQuest1)
			quests.add(testQuest2)
			`when`(questRepository!!.findByUserId(userId1)).thenReturn(quests)

			val link = Link("test_string")
			`when`(repositoryEntityLinks!!.linkToSingleResource(User::class.java, userId1)).thenReturn(link)

			//		val response = questController.findUserQuests(userId1)
			//
			//		response.body!!.content should contain(testQuest1).and(contain(testQuest2))
			//		response.statusCode shouldBe HttpStatus.OK

			mockMvc!!.perform(get("/quests/user/" + userId1.toString())).andDo(print()).andExpect(status().isOk).andExpect(
					jsonPath("$._links.self").exists())
		}

		"given I write more tests when I have time then I can determine if I like this" {

			//TODO write more tests.

		}
	}

}
