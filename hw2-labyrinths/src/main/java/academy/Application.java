package academy;

import academy.maze.dto.Maze;
import academy.maze.ioModule.CLIModule;
import academy.maze.run.MazeRunner;
import java.io.IOException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "maze-app",
        description = "Maze generator and solver CLI application.",
        version = "1.0",
        mixinStandardHelpOptions = true,
        subcommands = {Application.GenerateCommand.class, Application.SolveCommand.class})
public class Application implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }

    @Command(name = "generate", description = "Generate a maze with specified algorithm and dimensions.")
    static class GenerateCommand implements Runnable {

        @Option(
                names = {"--algorithm", "-a"},
                description = "Choosing a maze generation algorithm",
                required = true)
        private String algorithm;

        @Option(
                names = {"--width", "-w"},
                description = "Maze width",
                required = true)
        private int width;

        @Option(
                names = {"--height", "-h"},
                description = "Maze height",
                required = true)
        private int height;

        @Option(
                names = {"--output", "-o"},
                description = "File to save the maze")
        private String output;

        @Option(
                names = {"--UNICODE", "-u"},
                description = "Use Unicode pseudographics for maze display")
        private boolean useUnicode;

        @Override
        public void run() {
            MazeRunner mazeRunner = new MazeRunner();
            CLIModule cliModule = new CLIModule();
            try {
                Maze maze = mazeRunner.generateMaze(algorithm, height, width);
                mazeRunner.printMaze(maze, output, useUnicode);
            } catch (IllegalArgumentException e) {
                cliModule.printError(e.getMessage());
            }
        }
    }

    @Command(name = "solve", description = "Solve a maze with specified algorithm and points.")
    static class SolveCommand implements Runnable {

        @Option(
                names = {"--algorithm", "-a"},
                description = "Choosing an algorithm for solving the maze",
                required = true)
        private String algorithm;

        @Option(
                names = {"--file", "-f"},
                description = "maze file",
                required = true)
        private String input;

        @Option(
                names = {"--start", "-s"},
                description = "Starting point of the route in the x,y format",
                required = true)
        private String start;

        @Option(
                names = {"--end", "-e"},
                description = "End point of the route in the x,y format",
                required = true)
        private String end;

        @Option(
                names = {"--output", "-o"},
                description = "File to save the maze")
        private String output;

        @Option(
                names = {"--UNICODE", "-u"},
                description = "Use Unicode pseudographics for maze display")
        private boolean useUnicode;

        @Override
        public void run() {
            CLIModule cliModule = new CLIModule();
            MazeRunner mazeRunner = new MazeRunner();
            try {
                Maze maze = mazeRunner.readMaze(input);
                maze = mazeRunner.solveAlgorithm(algorithm, maze, start, end);
                mazeRunner.printMaze(maze, output, useUnicode);
            } catch (IOException | IllegalArgumentException e) {
                cliModule.printError(e.getMessage());
            }
        }
    }
}
