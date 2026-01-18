package academy.maze.generation;

import static academy.maze.utils.MazeConstants.PATHS;

import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.dto.enums.CellType;
import java.util.ArrayList;

public class PrimMazeGenerator extends AbstractMazeGenerator {

    /**
     * Генерация лабиринта заданного размера алгоритмом Прима
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если параметры некорректны
     */
    @Override
    protected Maze generateMazeAlgorithm(Maze maze, Point start, int height, int width) {
        ArrayList<Point> wallsList = new ArrayList<>();
        addPointsNear(start, wallsList, height, width);

        while (!wallsList.isEmpty()) {
            Point wall = wallsList.get(utils.getRandomInt(wallsList.size()));
            int countWalls = 0;
            for (Point to : PATHS) {
                Point further = new Point(wall.x() + to.x(), wall.y() + to.y());
                if (utils.checkBoardersPoint(further, height, width) && utils.isWalkable(maze, further)) {
                    ++countWalls;
                }
            }
            if (countWalls == 1) {
                maze.cells()[wall.x()][wall.y()] = CellType.PATH;
                addPointsNear(wall, wallsList, height, width);
            }
            wallsList.remove(wallsList.indexOf(wall));
        }
        return maze;
    }
}
