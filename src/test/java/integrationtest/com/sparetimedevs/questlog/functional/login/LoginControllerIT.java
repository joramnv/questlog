package integrationtest.com.sparetimedevs.questlog.functional.login;

import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
import com.sparetimedevs.questlog.userpassword.UserPassword;
import com.sparetimedevs.questlog.userpassword.UserPasswordRepository;
import integrationtest.com.sparetimedevs.questlog.functional.AbstractQuestlogApplicationIT;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

class LoginControllerIT extends AbstractQuestlogApplicationIT {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserPasswordRepository userPasswordRepository;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = getMockMvc();
		User user = setUpUser();
		UserPassword userPassword = setUpUserPassword(user);
	}

	private User setUpUser() {
		Optional<User> optionalUser = userRepository.findByEmailAddress(TEST_EMAIL_ADDRESS_1);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			User user = new User(TEST_EMAIL_ADDRESS_1);
			user = userRepository.save(user);
			return user;
		}
	}

	private UserPassword setUpUserPassword(User user) {
		Optional<UserPassword> optionalUserPassword = userPasswordRepository.findByUser(user);
		if (optionalUserPassword.isPresent()) {
			return optionalUserPassword.get();
		} else {
			UserPassword userPassword = new UserPassword(user, TEST_PASSWORD_1);
			userPassword = userPasswordRepository.save(userPassword);
			return userPassword;
		}
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	@Test
	void givenCorrectEmailAddressAndPasswordWhenPerformingPostToLoginResultsInLinksToUsersQuestsAndSavePassword() throws Exception {
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

	//TODO fix error handeling, then fix this test
	@Test
	void givenWrongEmailAddressAndPasswordWhenPerformingPostToLoginResultsInGracefulErrorMessage() throws Exception {
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
	void loginPost() {
	}

	@Test
	void loginGet() {
	}
}
