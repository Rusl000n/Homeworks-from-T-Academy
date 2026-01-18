package academy.maze.run;

import academy.maze.decision.AStarMazeSolver;
import academy.maze.decision.DijkstraMazeSolver;
import academy.maze.decision.MazeSolver;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.dto.enums.GenerateAlgorithm;
import academy.maze.dto.enums.SolveAlgorithm;
import academy.maze.dto.enums.TypeOutput;
import academy.maze.generation.DFSMazeGenerator;
import academy.maze.generation.MazeGenerator;
import academy.maze.generation.PrimMazeGenerator;
import academy.maze.ioModule.CLIModule;
import academy.maze.ioModule.FileModule;
import academy.maze.utils.Convertors;
import academy.maze.utils.MazeConstants;
import java.io.IOException;
import java.util.List;

public class MazeRunner {

    CLIModule cliModule = new CLIModule();
    FileModule fileModule = new FileModule();
    Convertors convertors = new Convertors();

    MazeGenerator mazeGenerator;
    MazeSolver mazeSolver;

    private Maze generateMaze(GenerateAlgorithm generateAlgorithm, int height, int width)
            throws IllegalArgumentException {
        height += MazeConstants.MIN_BOARDER;
        width += MazeConstants.MIN_BOARDER;
        try {
            return switch (generateAlgorithm) {
                case PRIM -> {
                    mazeGenerator = new PrimMazeGenerator();
                    yield mazeGenerator.generate(height, width);
                }
                case DFS -> {
                    mazeGenerator = new DFSMazeGenerator();
                    yield mazeGenerator.generate(height, width);
                }
            };
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    private Path solveAlgorithm(SolveAlgorithm solveAlgorithm, Maze maze, Point start, Point end)
            throws IllegalArgumentException {
        return switch (solveAlgorithm) {
            case ASTAR -> {
                mazeSolver = new AStarMazeSolver();
                yield mazeSolver.solve(maze, start, end);
            }
            case DIJKSTRA -> {
                mazeSolver = new DijkstraMazeSolver();
                yield mazeSolver.solve(maze, start, end);
            }
        };
    }

    /**
     * Генерация лабиринта с использованием указанного алгоритма
     *
     * @param algorithm алгоритм генерации
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если алгоритм не поддерживается или параметры некорректны
     */
    public Maze generateMaze(String algorithm, int height, int width) throws IllegalArgumentException {
        try {
            GenerateAlgorithm generateAlgorithm = GenerateAlgorithm.fromString(algorithm);
            if (generateAlgorithm == null) {
                return null;
            }
            return generateMaze(generateAlgorithm, height, width);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Решение лабиринта с использованием указанного алгоритма
     *
     * @param algorithm алгоритм решения
     * @param maze лабиринт для решения
     * @param start начальная точка в формате "x,y"
     * @param end конечная точка в формате "x,y"
     * @return лабиринт с отмеченным путем
     * @throws IllegalArgumentException если алгоритм не поддерживается или точки некорректны
     */
    public Maze solveAlgorithm(String algorithm, Maze maze, String start, String end) throws IllegalArgumentException {
        try {
            SolveAlgorithm solveAlgorithm = SolveAlgorithm.fromString(algorithm);
            if (solveAlgorithm == null) {
                return null;
            }
            Point startP = convertors.stringToPoint(start);
            Point endP = convertors.stringToPoint(end);
            Path path = solveAlgorithm(solveAlgorithm, maze, startP, endP);
            return convertors.concatMazePath(maze, path);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Вывод лабиринта в консоль или файл
     *
     * @param maze лабиринт для вывода
     * @param output путь к файлу (null для вывода в консоль)
     * @param unicode использовать Unicode символы
     */
    public void printMaze(Maze maze, String output, boolean unicode) {
        if (output == null) {
            cliModule.printMaze(maze, unicode ? TypeOutput.UNICODE : TypeOutput.STANDARD);
        } else {
            try {
                fileModule.writeFile(output, convertors.mazeToList(maze));
            } catch (IOException e) {
                cliModule.printError(e.getMessage());
            }
        }
    }

    /**
     * Чтение лабиринта из файла
     *
     * @param input путь к файлу с лабиринтом
     * @return прочитанный лабиринт
     * @throws IOException если произошла ошибка чтения
     */
    public Maze readMaze(String input) throws IOException {
        try {
            List<String> list = fileModule.readToList(input);
            Maze maze = convertors.listToMaze(list);
            return maze;
        } catch (IOException e) {
            throw e;
        }
    }
}
