package integrationtest.com.sparetimedevs.questlog.functional.user;

import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
import integrationtest.com.sparetimedevs.questlog.functional.AbstractQuestlogApplicationIT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

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

class UserRepositoryIT extends AbstractQuestlogApplicationIT {

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = getMockMvc();
	}

	@AfterEach
	void tearDown() throws Exception {
		Optional<User> optionalUser = userRepository.findByEmailAddress(TEST_EMAIL_ADDRESS_1);
		optionalUser.ifPresent(user -> userRepository.delete(user));
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/user")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	@Test
	void shouldCreateEntity() throws Exception {
		mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andExpect(
				header().string("Location", containsString("user/" + TEST_EMAIL_ADDRESS_1)));
	}

	@Test
	void shouldRetrieveEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				content().json("{\n" +
						"  \"_links\" : {\n" +
						"    \"self\" : {\n" +
						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
						"    },\n" +
						"    \"user\" : {\n" +
						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
						"    }\n" +
						"  }\n" +
						"}"));
	}

	@Test
	void shouldNotUpdateEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		try {
			mockMvc.perform(put(location).content(
					"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_2 + "\"}")).andExpect(
					status().isNoContent());
		} catch (NestedServletException e) {
			//This exception is expected
		}

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				content().json("{\n" +
						"  \"_links\" : {\n" +
						"    \"self\" : {\n" +
						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
						"    },\n" +
						"    \"user\" : {\n" +
						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
						"    }\n" +
						"  }\n" +
						"}"));
	}

	@Test
	void shouldNotPartiallyUpdateEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		try {
			mockMvc.perform(patch(location).content(
					"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_2 + "\"}")).andExpect(
					status().isNoContent());
		} catch (NestedServletException e) {
			//This exception is expected
		}

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				content().json("{\n" +
						"  \"_links\" : {\n" +
						"    \"self\" : {\n" +
						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
						"    },\n" +
						"    \"user\" : {\n" +
						"      \"href\" : \"http://localhost/user/"+TEST_EMAIL_ADDRESS_1 + "\"\n" +
						"    }\n" +
						"  }\n" +
						"}"));
	}

	@Test
	void shouldDeleteEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}

	@Test
	void shouldReturnStatusIsNotFoundWhenTryingToGetAnEntityThatIsNotPresentInTheDatabase() throws Exception {
		String uri = "http://localhost/user/" + TEST_EMAIL_ADDRESS_1;
//		String uri = "http://localhost/" + TEST_EMAIL_ADDRESS_1;

		mockMvc.perform(get(uri)).andExpect(status().isNotFound());
	}
}
