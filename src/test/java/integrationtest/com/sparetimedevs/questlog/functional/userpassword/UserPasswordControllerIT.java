package integrationtest.com.sparetimedevs.questlog.functional.userpassword;

import com.sparetimedevs.questlog.user.User;
import com.sparetimedevs.questlog.user.UserRepository;
import integrationtest.com.sparetimedevs.questlog.functional.AbstractQuestlogApplicationIT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testsetup.TestDataKt.EMAIL_ADDRESS_1;

class UserPasswordControllerIT extends AbstractQuestlogApplicationIT {

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = getMockMvc();
	}

	@AfterEach
	void tearDown() throws Exception {
		Optional<User> optionalUser = userRepository.findByEmailAddress(EMAIL_ADDRESS_1);
		optionalUser.ifPresent(user -> userRepository.delete(user));
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/save-password")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	//TODO write specific UserPasswordController tests.
}
