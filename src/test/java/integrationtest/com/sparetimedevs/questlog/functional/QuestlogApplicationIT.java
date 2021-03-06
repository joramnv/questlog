package integrationtest.com.sparetimedevs.questlog.functional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestlogApplicationIT extends AbstractQuestlogApplicationIT {

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = getMockMvc();
	}

	@Test
	public void checkIfApplicationCanRun() throws Exception {
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.profile").exists());
	}
}
