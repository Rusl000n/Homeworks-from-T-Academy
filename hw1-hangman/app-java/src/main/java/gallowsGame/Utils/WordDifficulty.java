package src.main.java.gallowsGame.Utils;

public enum WordDifficulty {
    SIMPLE,
    MIDDLE,
    HARD,
    RANDOM,
    NONE;

    public static WordDifficulty stringToWordDifficultly(String string) {
        if (string.isEmpty()) {
            return RANDOM;
        }
        String input = string.trim().toUpperCase();
        for (WordDifficulty difficulty : WordDifficulty.values()) {
            if (difficulty.name().equals(input)) {
                return difficulty;
            }
        }
        return NONE;
    }
}
