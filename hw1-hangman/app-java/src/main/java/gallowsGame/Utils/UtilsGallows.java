package src.main.java.gallowsGame.Utils;

import java.util.Map;
import java.util.Random;

public class UtilsGallows {

    public void initializeHiddenWord(Map<Integer, Character> hiddenWord, int size) {
        for (int i = 0; i < size; ++i) {
            hiddenWord.put(i, '_');
        }
    }

    public boolean isWordComplete(Map<Integer, Character> hiddenWord, String word) {
        for (int i = 0; i < word.length(); ++i) {
            if (hiddenWord.get(i) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean processAttempt(String word, String attempt, Map<Integer, Character> hiddenWord) {
        if (attempt.length() == 1) {
            return processLetterAttempt(word, attempt.charAt(0), hiddenWord);
        }
        return false;
    }

    private boolean processLetterAttempt(String word, char letter, Map<Integer, Character> hiddenWord) {
        boolean isCorrect = false;
        for (int i = 0; i < word.length(); ++i) {
            if (word.charAt(i) == letter) {
                hiddenWord.put(i, letter);
                isCorrect = true;
            }
        }
        return isCorrect;
    }


    public boolean isHint(String word) {
        return word.equalsIgnoreCase("hint");
    }

    public int getRandomNumber(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
}
