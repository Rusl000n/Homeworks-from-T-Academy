package src.main.java.gallowsGame.iomodule;

import src.main.java.gallowsGame.Utils.GameDifficulty;
import src.main.java.gallowsGame.Utils.UtilsGallows;
import src.main.java.gallowsGame.Utils.WordDifficulty;
import src.main.java.gallowsGame.Utils.ConstantsGallows;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import static src.main.java.gallowsGame.Utils.ConstantsGallows.PART_GAME_DIF;

public class IOModule {

    private final Scanner scanner;
    private final GallowsRenderer gallowsRenderer;
    private final UtilsGallows utilsGallows;

    public IOModule() {
        this.scanner = new Scanner(System.in);
        this.gallowsRenderer = new GallowsRenderer();
        this.utilsGallows = new UtilsGallows();
    }

    public void showWelcomeMessage() {
        System.out.println("Привет, предлагаем тебе сыграть в игру The Gallows game!");
    }

    private void showDifficulty(int module) {
        if (module == ConstantsGallows.PART_WORD_DIF) {
            System.out.println("Выбери сложность слова: simple, middle или hard. Если хочешь случайное - нажми enter");
        } else if (module == ConstantsGallows.PART_GAME_DIF) {
            System.out.println("Выбери сложность игры: simple, hard или нажми клавишу enter для случайного слова");
        }
    }

    private void showErrorDifficulty(int module) {
        if (module == ConstantsGallows.PART_WORD_DIF) {
            System.out.println("Неправильный ввод! Введите simple, middle, hard или нажми enter");
        } else if (module == ConstantsGallows.PART_GAME_DIF) {
            System.out.println("Неправильный ввод! Введите simple, hard или нажми enter");
        }
    }

    public WordDifficulty chooseWordDifficulty() {
        WordDifficulty response = WordDifficulty.NONE;
        showDifficulty(ConstantsGallows.PART_WORD_DIF);
        while (response == WordDifficulty.NONE) {
            String input = scanner.nextLine();
            response = WordDifficulty.stringToWordDifficultly(input);
            if (response == WordDifficulty.NONE) {
                showErrorDifficulty(ConstantsGallows.PART_WORD_DIF);
            }
        }
        if (response == WordDifficulty.RANDOM) {
            WordDifficulty[] wordDifficulties = WordDifficulty.values();
            return wordDifficulties[utilsGallows.getRandomNumber(ConstantsGallows.SIZE_WORD_DIFFICULTY)];
        }
        return response;
    }

    public GameDifficulty chooseGameDifficulty() {
        GameDifficulty response = GameDifficulty.NONE;
        showDifficulty(PART_GAME_DIF);
        while (response == GameDifficulty.NONE) {
            String input = scanner.nextLine();
            response = GameDifficulty.stringToGameDifficulty(input);
            if (response == GameDifficulty.NONE) {
                showErrorDifficulty(PART_GAME_DIF);
            }
        }

        if (response == GameDifficulty.RANDOM) {
            GameDifficulty[] gameDifficulties = GameDifficulty.values();
            return gameDifficulties[utilsGallows.getRandomNumber(ConstantsGallows.SIZE_GAME_DIFFICULTY)];
        }
        return response;
    }

    public void displayGallows(int wrongAttempts, int maxAttempts) {
        int remainingAttempts = maxAttempts - wrongAttempts;
        String outputAttempts = String.format("Оставшиеся попытки: %d из %d", remainingAttempts, maxAttempts);
        System.out.println(outputAttempts);
        gallowsRenderer.renderGallows(wrongAttempts, maxAttempts);
    }

    public void displayWord(Map<Integer, Character> revealedLetters, int wordLength, String hint) {
        System.out.print("Слово: ");
        for (int i = 0; i < wordLength; i++) {
            System.out.print(revealedLetters.get(i) + " ");
        }
        System.out.println();
        System.out.println("Или напишите слово hint для подсказки");
    }

    public void showHint(String hint) {
        String outputHint = String.format("Подсказка: %s", hint);
        System.out.println(outputHint);
    }

    public String getUserAttempt() {
        System.out.print("Введите букву: ");
        return scanner.nextLine().toLowerCase();
    }

    public void showCorrectAnswer() {
        System.out.println("Правильный ответ!");
    }

    public void showIncorrectAnswer() {
        System.out.println("Неправильный ответ!");
    }

    public void showWinMessage(int attempts, int maxAttempts) {
        System.out.println("Поздравляем! Ты победил!");
        showStatistic(attempts, maxAttempts);
    }

    private void showStatistic(int attempts, int maxAttempts) {
        String outputStatistic = String.format("Ошибок: %d из %d возможных", attempts, maxAttempts);
        System.out.println(outputStatistic);
    }

    public void showLoseMessage(int attempts, int maxAttempts, String word) {
        String outputLoseMessage = String.format("К сожалению, ты проиграл!\nЗагаданное слово: %s", word);
        System.out.println(outputLoseMessage);
        showStatistic(attempts, maxAttempts);
    }

    public void showErrorLoadWord() {
        System.out.println("Не удалось загрузить слово для выбранной сложности");
    }

    public void printForBot(String output, boolean isCorrect) {
        System.out.println(output + (isCorrect ? ";POS" : ";NEG"));
    }
}
