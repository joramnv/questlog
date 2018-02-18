package com.sparetimedevs.questlog.userpassword;

import com.sparetimedevs.questlog.user.UserRepository;
import com.sparetimedevs.questlog.validator.EmailAddressPasswordMatchValidator;
import org.junit.jupiter.api.AfterEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(UserPasswordController.class)
@ContextConfiguration(classes = {UserPasswordController.class})
class UserPasswordControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RepositoryEntityLinks entityLinks;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserPasswordRepository userPasswordRepository;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	//TODO write unit tests for UserPasswordController
	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/save_password")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	@Test
	void createPassword() {
	}

	@Test
	void updatePassword() {
	}

	@Test
	void savePassword() {
	}
}