package integrationtest.com.sparetimedevs.questlog.functional.quest;

import com.sparetimedevs.questlog.quest.Quest;
import com.sparetimedevs.questlog.quest.QuestRepository;
import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
import integrationtest.com.sparetimedevs.questlog.functional.AbstractQuestlogApplicationIT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestRepositoryIT extends AbstractQuestlogApplicationIT {

	private MockMvc mockMvc;

	@Autowired
	private QuestRepository questRepository;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setUp() throws Exception {
		mockMvc = getMockMvc();
		User user = setUpUser();
		Quest quest = setUpQuest(user);
	}

	private User setUpUser() {
		User user = new User();
		user.setEmailAddress(TEST_EMAIL_ADDRESS_1);
		return userRepository.save(user);
	}

	private Quest setUpQuest(User user) {
		Quest quest = new Quest();
		quest.setUser(user);
		quest.setName("Always Be Cool");
		quest.setDescription("This started with ABC and the rest is history.");
		quest.setAchievementPoint(22L);
		return quest;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/quest")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}
//TODO write specific QuestRepository tests.
//	@Test
//	public void shouldCreateEntity() throws Exception {
//		mockMvc.perform(post("/user").content(
//				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
//				status().isCreated()).andExpect(
//				header().string("Location", containsString("user/")));
//	}
//
//	@Test
//	public void shouldRetrieveEntity() throws Exception {
//		MvcResult mvcResult = mockMvc.perform(post("/user").content(
//				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
//				status().isCreated()).andReturn();
//
//		String location = mvcResult.getResponse().getHeader("Location");
//		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
//				content().json("{\n" +
//						"  \"_links\" : {\n" +
//						"    \"self\" : {\n" +
//						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
//						"    },\n" +
//						"    \"user\" : {\n" +
//						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
//						"    }\n" +
//						"  }\n" +
//						"}"));
//	}
//
//	@Test
//	public void shouldNotUpdateEntity() throws Exception {
//		MvcResult mvcResult = mockMvc.perform(post("/user").content(
//				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
//				status().isCreated()).andReturn();
//
//		String location = mvcResult.getResponse().getHeader("Location");
//
//		try {
//			mockMvc.perform(put(location).content(
//					"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_2 + "\"}")).andExpect(
//					status().isNoContent());
//		} catch (NestedServletException e) {
//			//This exception is expected
//		}
//
//		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
//				content().json("{\n" +
//						"  \"_links\" : {\n" +
//						"    \"self\" : {\n" +
//						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
//						"    },\n" +
//						"    \"user\" : {\n" +
//						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
//						"    }\n" +
//						"  }\n" +
//						"}"));
//	}
//
//	@Test
//	public void shouldNotPartiallyUpdateEntity() throws Exception {
//		MvcResult mvcResult = mockMvc.perform(post("/user").content(
//				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
//				status().isCreated()).andReturn();
//
//		String location = mvcResult.getResponse().getHeader("Location");
//
//		try {
//			mockMvc.perform(patch(location).content(
//					"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_2 + "\"}")).andExpect(
//					status().isNoContent());
//		} catch (NestedServletException e) {
//			//This exception is expected
//		}
//
//		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
//				content().json("{\n" +
//						"  \"_links\" : {\n" +
//						"    \"self\" : {\n" +
//						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
//						"    },\n" +
//						"    \"user\" : {\n" +
//						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
//						"    }\n" +
//						"  }\n" +
//						"}"));
//	}
//
//	@Test
//	public void shouldDeleteEntity() throws Exception {
//		MvcResult mvcResult = mockMvc.perform(post("/user").content(
//				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
//				status().isCreated()).andReturn();
//
//		String location = mvcResult.getResponse().getHeader("Location");
//		mockMvc.perform(delete(location)).andExpect(status().isNoContent());
//
//		mockMvc.perform(get(location)).andExpect(status().isNotFound());
//	}
}
