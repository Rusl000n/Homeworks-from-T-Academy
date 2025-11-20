package src.main.java.gallowsGame.Utils;

public enum GameDifficulty {
    SIMPLE,
    HARD,
    RANDOM,
    NONE;

    public static GameDifficulty stringToGameDifficulty(String string) {
        if (string.equals("")) {
            return RANDOM;
        }
        String input = string.trim().toUpperCase();
        for (GameDifficulty difficulty : GameDifficulty.values()) {
            if (difficulty.name().equals(input)) {
                return difficulty;
            }
        }
        return NONE;
    }
}
