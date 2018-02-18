package integrationtest.com.sparetimedevs.questlog.functional.login;

import integrationtest.com.sparetimedevs.questlog.functional.AbstractQuestlogApplicationIT;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerIT extends AbstractQuestlogApplicationIT {

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = getMockMvc();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	//TODO write tests using db (which means putting the data in the setUp in the db and removing it after).
	@Test
	public void givenCorrectEmailAddressAndPasswordWhenPerformingPostToLoginResultsInLinksToUsersQuestsAndSavePassword() throws Exception {
		mockMvc.perform(
				post("/login")
					.header("Accept", HAL_JSON_VALUE)
					.header("Content-Type", APPLICATION_JSON_VALUE)
					.content("{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\","
							+ "\"password\": \"" + TEST_PASSWORD_1 + "\"}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self").exists())
				.andExpect(jsonPath("$._links.quests").exists())
				.andExpect(jsonPath("$._links.save_password").exists());
	}

	@Test
	public void givenWrongEmailAddressAndPasswordWhenPerformingPostToLoginResultsInGracefulErrorMessage() throws Exception {
		mockMvc.perform(
				post("/login")
						.header("Accept", HAL_JSON_VALUE)
						.header("Content-Type", APPLICATION_JSON_VALUE)
						.content("{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\","
								+ "\"password\": \"" + TEST_PASSWORD_1 + "\"}")
		)
				.andExpect(status().isBadRequest())
				.andExpect(header().string("Location", containsString("login/")));
	}

	@Test
	public void loginPost() {
	}

	@Test
	public void loginGet() {
	}
}
