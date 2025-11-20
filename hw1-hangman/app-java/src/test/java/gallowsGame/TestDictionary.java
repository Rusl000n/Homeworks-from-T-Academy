package src.test.java.gallowsGame;

import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.dataBase.YamlDictionary;
import src.main.java.gallowsGame.Utils.WordDifficulty;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestDictionary {

    @Test
    public void testDictionary() {
        String testPath = "config/test.yaml";
        YamlDictionary testYamlDictionary = new YamlDictionary(testPath);
        assertEquals("тест", testYamlDictionary.getRandomWord(WordDifficulty.SIMPLE).word());
        assertEquals("тест", testYamlDictionary.getRandomWord(WordDifficulty.SIMPLE).hint());
    }
}
