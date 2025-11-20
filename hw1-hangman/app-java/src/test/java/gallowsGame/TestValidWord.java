package gallowsGame;

import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.Utils.WordEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestValidWord {

    @Test
    void testValidWord() {
        WordEntry entry = new WordEntry("test", "hint");
        assertEquals("test", entry.word());
        assertEquals("hint", entry.hint());
    }
}
