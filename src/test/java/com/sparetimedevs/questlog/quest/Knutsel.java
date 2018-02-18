package com.sparetimedevs.questlog.quest;

import com.sparetimedevs.questlog.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import test.TestDataKt;
import test.support.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(QuestController.class)
@ContextConfiguration(classes = {QuestController.class})
class Knutsel {

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public RepositoryEntityLinks repositoryEntityLinks() {
			return Mockito.mock(RepositoryEntityLinks.class);
		}

		@Bean
		public QuestRepository questRepository() {
			return Mockito.mock(QuestRepository.class);
		}
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RepositoryEntityLinks repositoryEntityLinks;

	@Autowired
	private QuestRepository questRepository;

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		List<Quest> quests = new ArrayList<Quest>();
		quests.add(test.data.TestQuest1Kt.getTestQuest1());
		quests.add(test.data.TestQuest2Kt.getTestQuest2());
		when(questRepository.findByUserId(TestDataKt.getUserId1())).thenReturn(quests);

		Link link = new Link("test_string");
		when(repositoryEntityLinks.linkToSingleResource(User.class, TestDataKt.getUserId1())).thenReturn(link);

//		val response = questController.findUserQuests(userId1)
//
//		response.body!!.content should contain(testQuest1).and(contain(testQuest2))
//		response.statusCode shouldBe HttpStatus.OK

		mockMvc.perform(get("/quests/user/" + TestDataKt.getUserId1().toString())).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

}
