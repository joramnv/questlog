package integrationtest.com.sparetimedevs.questlog.functional;

import com.sparetimedevs.questlog.QuestlogApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QuestlogApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractQuestlogApplicationIT {

	protected static final String TEST_EMAIL_ADDRESS_1 = "test@e-mail.address";
	protected static final String TEST_EMAIL_ADDRESS_2 = "second@test.mail";
	protected static final String TEST_PASSWORD_1 = "test_password";

	protected static final UUID USER_ID_1 = UUID.randomUUID(); //TODO actually do not use random uuid but fixed.
	protected static final UUID QUEST_ID_1 = UUID.randomUUID();
	protected static final UUID USER_PASSWORD_ID_1 = UUID.randomUUID();


	@Autowired
	private MockMvc mockMvc;

	public MockMvc getMockMvc() {
		return mockMvc;
	}
}
