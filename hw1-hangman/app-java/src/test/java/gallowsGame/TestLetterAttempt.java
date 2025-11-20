package gallowsGame;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.Utils.UtilsGallows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLetterAttempt {

    @Test
    public void testLetterAttempt() {
        Map<Integer, Character> word = new HashMap<>();
        UtilsGallows utilsGallows = new UtilsGallows();
        utilsGallows.initializeHiddenWord(word, 4);
        assertTrue(utilsGallows.processAttempt("maan", "m", word));
        assertEquals('m', word.get(0).charValue());
        assertTrue(utilsGallows.processAttempt("maan", "a", word));
        assertEquals('a', word.get(2).charValue());
    }
}
