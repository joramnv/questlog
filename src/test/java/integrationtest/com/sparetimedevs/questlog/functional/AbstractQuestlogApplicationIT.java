package integrationtest.com.sparetimedevs.questlog.functional;

import com.sparetimedevs.questlog.QuestlogApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestlogApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractQuestlogApplicationIT {

	protected static final String TEST_EMAIL_ADDRESS_1 = "test@e-mail.address";
	protected static final String TEST_EMAIL_ADDRESS_2 = "second@test.mail";
	protected static final String TEST_PASSWORD_1 = "test_password";

	@Autowired
	private MockMvc mockMvc;

	public MockMvc getMockMvc() {
		return mockMvc;
	}
}
