package src.main.java.gallowsGame;

public class StartGallowsGame {
    public static void main(String[] args) {
        if (args.length == 2) {
            GameEngine gameEngine = new GameEngine();
            gameEngine.startGame(args[0], args[1]);
        } else if (args.length == 1) {
            GameEngine gameEngine = new GameEngine(args[0]);
            gameEngine.startGame();
        } else {
            GameEngine gameEngine = new GameEngine();
            gameEngine.startGame();
        }
    }
}
