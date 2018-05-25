package integrationtest.com.sparetimedevs.questlog.functional;

import com.sparetimedevs.questlog.QuestlogApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QuestlogApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractQuestlogApplicationIT {

	protected static final String TEST_BASE_URL = "http://localhost";

	@Autowired
	private MockMvc mockMvc;

	protected MockMvc getMockMvc() {
		return mockMvc;
	}
}
