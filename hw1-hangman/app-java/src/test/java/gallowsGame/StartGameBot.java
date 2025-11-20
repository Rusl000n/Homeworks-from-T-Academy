package gallowsGame;

import org.junit.jupiter.api.Test;
import src.main.java.gallowsGame.GameEngine;
import src.main.java.gallowsGame.dataBase.Dictionary;
import src.main.java.gallowsGame.dataBase.YamlDictionary;
import src.main.java.gallowsGame.iomodule.IOModule;
import src.main.java.gallowsGame.Utils.UtilsGallows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartGameBot {

    @Test
    public void startGameBotPos() {
        IOModule testIOModule = new IOModule();
        Dictionary testDictionary = new YamlDictionary();
        UtilsGallows utilsGallows = new UtilsGallows();
        GameEngine engine = new GameEngine(testIOModule, testDictionary, utilsGallows);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        engine.startGame("DOBDOBDOB", "DOBDOBDOB");
        String result = output.toString().trim();
        assertTrue(result.contains("DOBDOBDOB;POS"));
    }

    @Test
    public void startGameBotNeg() {
        IOModule testIOModule = new IOModule();
        Dictionary testDictionary = new YamlDictionary();
        UtilsGallows utilsGallows = new UtilsGallows();
        GameEngine engine = new GameEngine(testIOModule, testDictionary, utilsGallows);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        engine.startGame("волокно", "барахло");
        String result = output.toString().trim();
        assertTrue(result.contains("*оло**о;NEG"));
    }
}
