package src.test.java.gallowsGame;

import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.Utils.UtilsGallows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestRandomHint {

    @Test
    public void testRandom() {
        UtilsGallows utilsGallows = new UtilsGallows();
        int testValue = 100;
        testValue = utilsGallows.getRandomNumber(99);
        assertFalse(testValue == 100);
    }

    @Test
    public void testHint() {
        UtilsGallows utilsGallows = new UtilsGallows();
        boolean testHint = utilsGallows.isHint("hint");
        assertTrue(testHint);
    }


}
