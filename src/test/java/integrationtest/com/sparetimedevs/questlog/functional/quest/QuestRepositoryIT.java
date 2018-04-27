package integrationtest.com.sparetimedevs.questlog.functional.quest;

import com.sparetimedevs.questlog.quest.Quest;
import com.sparetimedevs.questlog.quest.QuestRepository;
import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
import integrationtest.com.sparetimedevs.questlog.functional.AbstractQuestlogApplicationIT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestRepositoryIT extends AbstractQuestlogApplicationIT {

	private MockMvc mockMvc;

	@Autowired
	private QuestRepository questRepository;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = getMockMvc();
		User user = setUpUser();
		Quest quest = setUpQuest(user);
	}

	private User setUpUser() {
		User user = new User(USER_ID_1, TEST_EMAIL_ADDRESS_1);
		return userRepository.save(user);
	}

	private Quest setUpQuest(User user) {
		return new Quest(QUEST_ID_1, USER_ID_1, "Always Be Cool", "This started with ABC and the rest is history.", 22L);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/quests")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}
//TODO write specific QuestRepository tests.
}
