package src.test.java.gallowsGame;

import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.Utils.WordDifficulty;
import src.main.java.gallowsGame.iomodule.IOModule;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IOTests {
    @Test
    public void testWelcomeMessage() {
        IOModule ioModule = new IOModule();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        ioModule.showWelcomeMessage();
        String res = byteArrayOutputStream.toString();
        assertEquals("Привет, предлагаем тебе сыграть в игру The Gallows game!\n", res);
    }

    @Test
    public void testHint() {
        IOModule ioModule = new IOModule();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        ioModule.showHint("hint");
        String res = byteArrayOutputStream.toString();
        assertEquals("Подсказка: hint\n", res);
    }

    @Test
    public void testCorrectAnswer() {
        IOModule ioModule = new IOModule();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        ioModule.showCorrectAnswer();
        String res = byteArrayOutputStream.toString();
        assertEquals("Правильный ответ!\n", res);
    }

    @Test
    public void testStatistic() {
        IOModule ioModule = new IOModule();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        int testAttempts = 100;
        int testMAttempts = 100;
        ioModule.showWinMessage(testAttempts, testMAttempts);
        String res = byteArrayOutputStream.toString();
        assertEquals("Поздравляем! Ты победил!\nОшибок: 100 из 100 возможных\n", res);
    }
}
