package gallowsGame;

import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.iomodule.GallowsRenderer;
import src.main.java.gallowsGame.Utils.GameDifficulty;
import src.main.java.gallowsGame.Utils.ConstantsGallows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRenderGallows {

    @Test
        public void testRenderGallows() {
            GallowsRenderer renderer = new GallowsRenderer();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            renderer.renderGallows(3, ConstantsGallows.SIMPLE_GAME);
            String result = output.toString();
            assertTrue(result.contains("o"));
            assertTrue(result.contains("|"));
    }

}
