package edward.duong.hospital_mgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public abstract class BaseTestConfig {
    protected static ObjectMapper objectMapper = new ObjectMapper();
    protected final Random random = new Random();
}
