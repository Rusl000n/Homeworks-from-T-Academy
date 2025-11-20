package src.main.java.gallowsGame;

import src.main.java.gallowsGame.dataBase.Dictionary;
import src.main.java.gallowsGame.dataBase.YamlDictionary;
import src.main.java.gallowsGame.iomodule.IOModule;
import src.main.java.gallowsGame.Utils.GameDifficulty;
import src.main.java.gallowsGame.Utils.ConstantsGallows;
import src.main.java.gallowsGame.Utils.UtilsGallows;
import src.main.java.gallowsGame.Utils.WordDifficulty;
import src.main.java.gallowsGame.Utils.WordEntry;
import java.util.HashMap;
import java.util.Map;

public class GameEngine {
    private final IOModule iomodule;
    private final Dictionary dictionary;
    private final UtilsGallows utilsGallows;

    public GameEngine() {
        this.iomodule = new IOModule();
        this.dictionary = new YamlDictionary();
        this.utilsGallows = new UtilsGallows();
    }

    public GameEngine(String pathDataBase) {
        this.iomodule = new IOModule();
        this.dictionary = new YamlDictionary(pathDataBase);
        this.utilsGallows = new UtilsGallows();
    }

    public GameEngine(IOModule iomodule, Dictionary dictionary, UtilsGallows utilsGallows) {
        this.iomodule = iomodule;
        this.dictionary = dictionary;
        this.utilsGallows = utilsGallows;
    }

    public void startGame(String input, String output) {
        StringBuilder result = new StringBuilder();
        boolean isPerfectMatch = input.equals(output);

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (output.indexOf(currentChar) != -1) {
                result.append(currentChar);
            } else {
                result.append('*');
            }
        }
        iomodule.printForBot(result.toString(), isPerfectMatch);
    }

    public void startGame() {
        iomodule.showWelcomeMessage();
        WordEntry wordEntry = takeWord();
        if (wordEntry == null || wordEntry.word() == null) {
            iomodule.showErrorLoadWord();
            return;
        }
        GameDifficulty gameDifficulty = iomodule.chooseGameDifficulty();
        runGallowsGame(wordEntry, gameDifficulty);
    }

    private WordEntry takeWord() {
        WordDifficulty wordDifficulty = iomodule.chooseWordDifficulty();
        WordEntry wordEntry = dictionary.getRandomWord(wordDifficulty);
        return wordEntry;
    }

    private void runGallowsGame(WordEntry wordEntry, GameDifficulty gameDifficulty) {
        String word = wordEntry.word().toLowerCase();
        int maxAttempts = gameDifficulty == GameDifficulty.SIMPLE ? ConstantsGallows.SIMPLE_GAME : ConstantsGallows.HARD_GAME;
        int wrongAttempts = 0;
        Map<Integer, Character> revealedLetters = new HashMap<>();
        utilsGallows.initializeHiddenWord(revealedLetters, word.length());
        while (wrongAttempts < maxAttempts && !utilsGallows.isWordComplete(revealedLetters, word)) {
            iomodule.displayGallows(wrongAttempts, maxAttempts);
            iomodule.displayWord(revealedLetters, word.length(), wordEntry.hint());
            String attempt = iomodule.getUserAttempt();
            if (utilsGallows.processAttempt(word, attempt, revealedLetters)) {
                iomodule.showCorrectAnswer();
            } else if (utilsGallows.isHint(attempt)) {
                iomodule.showHint(wordEntry.hint());
            }else {
                iomodule.showIncorrectAnswer();
                wrongAttempts++;
            }
        }
        iomodule.displayGallows(wrongAttempts, maxAttempts);
        if (utilsGallows.isWordComplete(revealedLetters, word)) {
            iomodule.showWinMessage(wrongAttempts, maxAttempts);
        } else {
            iomodule.showLoseMessage(wrongAttempts, maxAttempts, word);
        }
    }
}
