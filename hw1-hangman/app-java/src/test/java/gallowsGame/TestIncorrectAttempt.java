package gallowsGame;

import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.Utils.UtilsGallows;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIncorrectAttempt {

    @Test
    public void testIncorrectAttempt() {
        Map<Integer, Character> word = new HashMap<>();
        UtilsGallows utilsGallows = new UtilsGallows();
        utilsGallows.initializeHiddenWord(word, 1);
        assertFalse(utilsGallows.processAttempt("a", "ab", word));
        assertEquals('_', word.get(0).charValue());
    }

    @Test
    public void testWinGame() {
        Map<Integer, Character> word = new HashMap<>();
        UtilsGallows utilsGallows = new UtilsGallows();
        utilsGallows.initializeHiddenWord(word, 0);
        assertTrue(utilsGallows.processAttempt("r", "r", word));
        assertTrue(utilsGallows.isWordComplete(word, "r"));
        assertEquals('r', word.get(0).charValue());
    }
}
