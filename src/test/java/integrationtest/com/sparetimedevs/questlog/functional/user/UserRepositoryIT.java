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

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testsetup.TestDataKt.*;
import static testsetup.TestDataKt.EMAIL_ADDRESS_1;
import static testsetup.TestDataKt.EMAIL_ADDRESS_2;

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
		Optional<User> optionalUser1 = userRepository.findByEmailAddress(EMAIL_ADDRESS_1);
		optionalUser1.ifPresent(user -> userRepository.delete(user));

		Optional<User> optionalUser2 = userRepository.findByEmailAddress(EMAIL_ADDRESS_2);
		optionalUser2.ifPresent(user -> userRepository.delete(user));
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	@Test
	void shouldCreateEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/users").content(
				"{\"emailAddress\": \"" + EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		String userId = getUserIdFromLocation(location);
		assertThat(userId, is(notNullValue()));
	}

	@Test
	void shouldRetrieveEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/users").content(
				"{\"emailAddress\": \"" + EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		String userId = getUserIdFromLocation(location);
		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				content().json("{\n" +
						"  \"_links\" : {\n" +
						"    \"self\" : {\n" +
						"      \"href\" : \"" + TEST_BASE_URL + "/users/" + userId + "\"\n" +
						"    },\n" +
						"    \"user\" : {\n" +
						"      \"href\" : \"" + TEST_BASE_URL + "/users/" + userId + "\"\n" +
						"    }\n" +
						"  }\n" +
						"}"));
	}

	@Test
	void shouldNotUpdateEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/users").content(
				"{\"emailAddress\": \"" + EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		try {
			mockMvc.perform(put(location).content(
					"{\"emailAddress\": \"" + EMAIL_ADDRESS_2 + "\"}")).andExpect(
					status().isNoContent());
		} catch (Exception e) {
			//TODO instead of expecting an exception, this should return an api error message stating that the emailaddress can not be changed... (why not actually? Seems like a valid action)
		}

		String userId = getUserIdFromLocation(location);

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				content().json("{\n" +
						"  \"_links\" : {\n" +
						"    \"self\" : {\n" +
						"      \"href\" : \"" + TEST_BASE_URL + "/users/" + userId + "\"\n" +
						"    },\n" +
						"    \"user\" : {\n" +
						"      \"href\" : \"" + TEST_BASE_URL + "/users/" + userId + "\"\n" +
						"    }\n" +
						"  }\n" +
						"}"));
	}

	@Test
	void shouldNotPartiallyUpdateEntity() throws Exception { //TODO fix org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint ["UK_D0AR1H7WCP7LDY6QG5859SOL6_INDEX_2 ON PUBLIC.USER(EMAIL_ADDRESS) VALUES ('test@e-mail.address', 2)"; SQL statement:
		MvcResult mvcResult = mockMvc.perform(post("/users").content(
				"{\"emailAddress\": \"" + EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		try {
			mockMvc.perform(patch(location).content(
					"{\"emailAddress\": \"" + EMAIL_ADDRESS_2 + "\"}")).andExpect(
					status().isNoContent());
		} catch (Exception e) {
			//TODO instead of expecting an exception, this should return an api error message stating that the emailaddress can not be changed... (why not actually? Seems like a valid action)
		}

		String userId = getUserIdFromLocation(location);

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				content().json("{\n" +
						"  \"_links\" : {\n" +
						"    \"self\" : {\n" +
						"      \"href\" : \"" + TEST_BASE_URL + "/users/" + userId + "\"\n" +
						"    },\n" +
						"    \"user\" : {\n" +
						"      \"href\" : \"" + TEST_BASE_URL + "/users/" + userId + "\"\n" +
						"    }\n" +
						"  }\n" +
						"}"));
		//TODO this does not assert the same e-mail address (is it still the same, don't think so?)
	}

	@Test
	void shouldDeleteEntity() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/users").content(
				"{\"emailAddress\": \"" + EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}

	@Test
	void shouldReturnStatusIsNotFoundWhenTryingToGetAnEntityThatIsNotPresentInTheDatabase() throws Exception {
		String url = "http://localhost/users/" + getUserId1();

		mockMvc.perform(get(url)).andExpect(status().isNotFound());
	}

	private String getUserIdFromLocation(String location) {
		String[] locationUrlInParts = location.split("/");
		return locationUrlInParts[locationUrlInParts.length - 1];
	}
}
