package com.sparetimedevs.questlog.login;

import com.sparetimedevs.questlog.validator.EmailAddressPasswordMatchValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import testsupport.MockitoExtension;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = {LoginController.class})
class LoginControllerTest {

	private static final String TEST_EMAIL_ADDRESS_1 = "test@e-mail.address";
	private static final String TEST_PASSWORD_1 = "test_password";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RepositoryEntityLinks entityLinks;

	@MockBean
	private EmailAddressPasswordMatchValidator emailAddressPasswordMatchValidator;

	@BeforeEach
	void setUp() {
		//Todo change this to return something valid
		Link link = new Link("quests", "quests");

		LinkBuilder linkBuilder = new LinkBuilder() {
			@Override
			public LinkBuilder slash(Object object) {
				return null;
			}

			@Override
			public LinkBuilder slash(Identifiable<?> identifiable) {
				return null;
			}

			@Override
			public URI toUri() {
				return null;
			}

			@Override
			public Link withRel(String rel) {
				return link;
			}

			@Override
			public Link withSelfRel() {
				return link;
			}
		};
		when(entityLinks.linkFor(any(), anyString())).thenReturn(linkBuilder);
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	@Test
	void givenCorrectEmailAddressAndPasswordWhenPerformingPostToLoginResultsInLinksToUsersQuestsAndSavePassword() throws Exception {
		doNothing().when(emailAddressPasswordMatchValidator).validate(TEST_EMAIL_ADDRESS_1, TEST_PASSWORD_1);

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
		doThrow(IllegalArgumentException.class).when(emailAddressPasswordMatchValidator).validate(TEST_EMAIL_ADDRESS_1, TEST_PASSWORD_1);

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
