package academy.maze.decision;

import static academy.maze.utils.MazeConstants.NONE;
import static academy.maze.utils.MazeConstants.PATHS;
import static academy.maze.utils.MazeConstants.PATH_LENGTH_ONE;

import academy.maze.dto.Maze;
import academy.maze.dto.NodeData;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.utils.Utils;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraMazeSolver implements MazeSolver {

    Utils utils = new Utils();

    /**
     * Решение лабиринта с использованием алгоритма Дейкстры. Если путь не найден, возвращается путь с длиной 0.
     *
     * @param maze лабиринт.
     * @param start начальная точка.
     * @param end конечная точка.
     * @return путь в лабиринте.
     */
    @Override
    public Path solve(Maze maze, Point start, Point end) {
        if (!utils.isWalkable(maze, start) || !utils.isWalkable(maze, end)) {
            return new Path(new Point[0]);
        }
        NodeData[][] nodeData = utils.initNodeData(maze.getHeight(), maze.getWidth());
        nodeData[start.x()][start.y()] = new NodeData(null, 0, 0, true);
        PriorityQueue<Point> queue =
                new PriorityQueue<>(Comparator.comparingInt(p -> nodeData[p.x()][p.y()].getRealPath()));
        queue.add(new Point(start.x(), start.y()));
        Set<Point> visited = new HashSet<>();

        return runDijkstraAlgorithm(maze, end, nodeData, queue, visited);
    }

    /**
     * Основной цикл алгоритма Дейкстры
     *
     * @param maze лабиринт
     * @param end конечная точка
     * @param nodeData данные узлов
     * @param queue очередь приоритета
     * @param visited множество посещенных точек
     * @return найденный путь или пустой путь
     */
    private Path runDijkstraAlgorithm(
            Maze maze, Point end, NodeData[][] nodeData, PriorityQueue<Point> queue, Set<Point> visited) {

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            NodeData currentData = nodeData[current.x()][current.y()];

            if (visited.contains(current)) {
                continue;
            }

            if (current.equals(end)) {
                return utils.reconstructPath(nodeData, end);
            }

            visited.add(current);
            currentData.setInOpenSet(false);

            for (Point to : PATHS) {
                Point further = new Point(current.x() + to.x(), current.y() + to.y());
                if (!utils.checkBoardersPoint(further, maze.getHeight(), maze.getWidth())
                        || !utils.isWalkable(maze, further)
                        || visited.contains(further)) {
                    continue;
                }

                int newDistance = currentData.getRealPath() + PATH_LENGTH_ONE;
                NodeData neighborData = nodeData[further.x()][further.y()];

                if (newDistance < neighborData.getRealPath()) {
                    utils.updateNeighborData(neighborData, current, newDistance, NONE);
                    if (!neighborData.isInOpenSet()) {
                        neighborData.setInOpenSet(true);
                        queue.add(further);
                    } else {
                        queue.remove(further);
                        queue.add(further);
                    }
                }
            }
        }
        return new Path(new Point[0]);
    }
}
