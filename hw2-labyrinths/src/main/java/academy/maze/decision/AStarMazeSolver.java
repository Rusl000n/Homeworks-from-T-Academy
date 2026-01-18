package academy.maze.decision;

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

public class AStarMazeSolver implements MazeSolver {

    Utils utils = new Utils();

    /**
     * Решение лабиринта с использованием алгоритма A*. Если путь не найден, возвращается путь с длиной 0.
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
        Set<Point> closedSet = new HashSet<>();
        NodeData[][] nodeData = utils.initNodeData(maze.getHeight(), maze.getWidth());
        nodeData[start.x()][start.y()] = new NodeData(null, 0, utils.heuristic(start, end), true);
        PriorityQueue<Point> openSet = new PriorityQueue<>(Comparator.comparingInt(
                p -> nodeData[p.x()][p.y()].getRealPath() + nodeData[p.x()][p.y()].getHeuristicPath()));
        openSet.add(start);

        return runAStarAlgorithm(maze, end, closedSet, nodeData, openSet);
    }

    /**
     * Основной цикл алгоритма A*
     *
     * @param maze лабиринт
     * @param end конечная точка
     * @param closedSet множество обработанных точек
     * @param nodeData данные узлов
     * @param openSet очередь приоритета для обработки
     * @return найденный путь или пустой путь
     */
    private Path runAStarAlgorithm(
            Maze maze, Point end, Set<Point> closedSet, NodeData[][] nodeData, PriorityQueue<Point> openSet) {

        while (!openSet.isEmpty()) {
            Point current = openSet.poll();
            if (closedSet.contains(current)) {
                continue;
            }
            NodeData currentData = nodeData[current.x()][current.y()];
            if (current.equals(end)) {
                return utils.reconstructPath(nodeData, end);
            }
            closedSet.add(current);

            currentData.setInOpenSet(false);
            for (Point to : PATHS) {
                Point neighbor = new Point(current.x() + to.x(), current.y() + to.y());
                if (!utils.checkBoardersPoint(neighbor, maze.getHeight(), maze.getWidth())
                        || !utils.isWalkable(maze, neighbor)
                        || closedSet.contains(neighbor)) {
                    continue;
                }
                int tentativePath = currentData.getRealPath() + PATH_LENGTH_ONE;
                NodeData neighborData = nodeData[neighbor.x()][neighbor.y()];
                if (tentativePath < neighborData.getRealPath()) {
                    utils.updateNeighborData(neighborData, current, tentativePath, utils.heuristic(neighbor, end));
                    utils.updateOpenSet(openSet, neighbor);
                }
            }
        }
        return new Path(new Point[0]);
    }
}
