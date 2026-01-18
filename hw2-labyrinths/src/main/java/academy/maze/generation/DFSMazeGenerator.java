package academy.maze.generation;

import static academy.maze.utils.MazeConstants.*;

import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.dto.enums.CellType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class DFSMazeGenerator extends AbstractMazeGenerator {

    /**
     * Генерация лабиринта заданного размера алгоритмом DFS
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если параметры некорректны
     */
    @Override
    protected Maze generateMazeAlgorithm(Maze maze, Point start, int height, int width) {
        Deque<Point> Deque = new ArrayDeque<>();
        Deque.push(start);
        while (!Deque.isEmpty()) {
            Point local = Deque.peek();
            ArrayList<Point> steps = new ArrayList<>();
            for (Point path : PATHS) {
                Point further = new Point(local.x() + 2 * path.x(), local.y() + 2 * path.y());
                if (utils.checkBoardersPoint(further, height, width) && !utils.isWalkable(maze, further)) {
                    steps.add(further);
                }
            }
            if (!steps.isEmpty()) {
                Point to = steps.get(utils.getRandomInt(steps.size()));
                maze.cells()[utils.average(local.x(), to.x())][utils.average(local.y(), to.y())] = CellType.PATH;
                maze.cells()[to.x()][to.y()] = CellType.PATH;
                Deque.push(to);
            } else {
                Deque.pop();
            }
        }
        return maze;
    }
}
