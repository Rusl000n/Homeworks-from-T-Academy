package src.main.java.gallowsGame.dataBase;

import src.main.java.gallowsGame.Utils.WordDifficulty;
import src.main.java.gallowsGame.Utils.WordEntry;
import java.util.List;

public interface Dictionary {
    WordEntry getRandomWord(WordDifficulty difficulty);
    List<WordEntry> getWordsByDifficulty(WordDifficulty difficulty);
    void loadDictionary(String pathDictionary);
}
