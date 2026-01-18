package academy;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ApplicationTest {

    @Test
    void happyPathTest() {
        Application app = new Application();
        assertNotNull(app);
    }
}
