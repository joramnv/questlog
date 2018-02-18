package com.sparetimedevs.questlog.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;
import testsupport.MockitoExtension;

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

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(UserRepository.class)
@ContextConfiguration(classes = {UserRepository.class})
class UserRepositoryTest {

	private static final String TEST_EMAIL_ADDRESS_1 = "test@e-mail.address";
	private static final String TEST_EMAIL_ADDRESS_2 = "second@test.mail";

	@Autowired
	private MockMvc mockMvc;

	//TODO write these tests as unit tests instead of it tests.

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/user")).andDo(print());
//				.andExpect(status().isOk()).andExpect(
//				jsonPath("$._links.self").exists());
	}
//
//	@Test
//	void shouldCreateEntity() throws Exception {
//		mockMvc.perform(post("/user").content(
//				"{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\"}")).andExpect(
//				status().isCreated()).andExpect(
//				header().string("Location", containsString("user/")));
//	}
//
//	@Test
//	void shouldRetrieveEntity() throws Exception {
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
//	void shouldNotUpdateEntity() throws Exception {
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
//	void shouldNotPartiallyUpdateEntity() throws Exception {
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
//	void shouldDeleteEntity() throws Exception {
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
