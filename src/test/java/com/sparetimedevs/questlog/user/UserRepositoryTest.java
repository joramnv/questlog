package com.sparetimedevs.questlog.user;

import com.sparetimedevs.questlog.userpassword.UserPasswordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRepositoryTest {

	private static final String TEST_EMAIL_ADDRESS_1 = "test@e-mail.address";
	private static final String TEST_EMAIL_ADDRESS_2 = "second@test.mail";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserPasswordRepository userPasswordRepository;

	@Before
	public void deleteAllBeforeTests() throws Exception {
		userPasswordRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.user").exists());
	}

	@Test
	public void shouldCreateEntity() throws Exception {

		mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andExpect(
				header().string("Location", containsString("user/")));
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {

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
	public void shouldNotUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(put(location).content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_2 + "\"}")).andExpect(
				status().isNoContent());

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
	public void shouldNotPartiallyUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(
				patch(location).content("{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_2 + "\"}")).andExpect(
				status().isNoContent());

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
	public void shouldDeleteEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/user").content(
				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
				status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}

}
