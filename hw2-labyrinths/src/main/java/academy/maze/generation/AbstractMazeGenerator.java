package academy.maze.generation;

import static academy.maze.utils.MazeConstants.PATHS;

import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.dto.enums.CellType;
import academy.maze.utils.Utils;
import java.util.ArrayList;

public abstract class AbstractMazeGenerator implements MazeGenerator {

    protected final Utils utils = new Utils();

    /** Общая часть генерации лабиринта */
    @Override
    public Maze generate(int height, int width) throws IllegalArgumentException {
        if (!utils.checkBoarders(height, width)) {
            throw new IllegalArgumentException("It is impossible to build a maze with the passed parameters");
        }
        Maze maze = utils.createMaze(height, width);
        Point start = utils.getRandomPointOdd(height, width);
        maze.cells()[start.x()][start.y()] = CellType.PATH;
        return generateMazeAlgorithm(maze, start, height, width);
    }

    /** Абстрактный метод для реализации конкретного алгоритма генерации */
    protected abstract Maze generateMazeAlgorithm(Maze maze, Point start, int height, int width);

    /** Метод для добавления соседних точек (используется в PrimMazeGenerator) */
    protected void addPointsNear(Point point, ArrayList<Point> arrayList, int height, int width) {
        for (Point to : PATHS) {
            Point near = new Point(point.x() + to.x(), point.y() + to.y());
            if (utils.checkBoardersPoint(near, height, width)) {
                arrayList.add(near);
            }
        }
    }
}
