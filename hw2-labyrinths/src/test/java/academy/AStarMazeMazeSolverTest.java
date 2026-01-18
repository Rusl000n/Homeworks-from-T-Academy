package academy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import academy.maze.decision.AStarMazeSolver;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.dto.enums.CellType;
import academy.maze.run.MazeRunner;
import academy.maze.utils.Convertors;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class AStarMazeMazeSolverTest {

    AStarMazeSolver solver = new AStarMazeSolver();
    MazeRunner mazeRunner = new MazeRunner();
    Convertors convertors = new Convertors();

    @Test
    void test1() {
        Maze maze;
        try {
            maze = mazeRunner.readMaze("src/test/resources/test_cases/2.txt");
            maze = convertors.concatMazePath(maze, solver.solve(maze, new Point(1, 1), new Point(9, 15)));
            assertThat(maze.cells()[9][15]).isEqualTo(CellType.END);
        } catch (IOException e) {
            System.out.print("ERROR: " + e.getMessage());
            assertThat(true).isEqualTo(false);
        }
    }

    @Test
    void test2() {
        Maze maze;
        try {
            maze = mazeRunner.readMaze("src/test/resources/test_cases/1.txt");
            maze = convertors.concatMazePath(maze, solver.solve(maze, new Point(1, 1), new Point(7, 9)));
            assertThat(maze.cells()[7][9]).isEqualTo(CellType.END);
        } catch (IOException e) {
            System.out.print("ERROR: " + e.getMessage());
            assertThat(true).isEqualTo(false);
        }
    }

    @Test
    void test3() {
        Maze maze;
        try {
            maze = mazeRunner.readMaze("src/test/resources/test_cases/3.txt");
            maze = convertors.concatMazePath(maze, solver.solve(maze, new Point(1, 1), new Point(99, 299)));
            assertThat(maze.cells()[99][299]).isEqualTo(CellType.END);
        } catch (IOException e) {
            System.out.print("ERROR: " + e.getMessage());
            assertThat(true).isEqualTo(false);
        }
    }
}
