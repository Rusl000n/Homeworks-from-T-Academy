package academy.maze.utils;

import static academy.maze.utils.MazeConstants.*;

import academy.maze.dto.Maze;
import academy.maze.dto.NodeData;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.dto.enums.CellType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Utils {

    Random random = new Random();

    /**
     * Проверяет, находится ли точка в пределах границ лабиринта
     *
     * @param point проверяемая точка
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return true если точка находится в пределах границ, иначе false
     */
    public boolean checkBoardersPoint(Point point, int height, int width) {
        return point.x() >= HEIGHT_MIN
                && point.x() <= height - HEIGHT_MAX
                && point.y() >= WIDTH_MIN
                && point.y() <= width - WIDTH_MAX;
    }

    /**
     * Проверяет, является ли ячейка в указанной точке проходимой
     *
     * @param maze лабиринт для проверки
     * @param point точка для проверки
     * @return true если ячейка является путем (PATH), иначе false
     */
    public boolean isWalkable(Maze maze, Point point) {
        return maze.cells()[point.x()][point.y()] == CellType.PATH;
    }

    /**
     * Проверяет корректность размеров лабиринта для генерации
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return true если размеры нечетные и положительные, иначе false
     */
    public boolean checkBoarders(int height, int width) {
        return Math.abs(height % 2) == 1 && Math.abs(width % 2) == 1 && height > 0 && width > 0;
    }

    /**
     * Генерирует случайную точку в пределах лабиринта
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return случайная точка
     */
    private Point getRandomPoint(int height, int width) {
        int xMax = Math.max(HEIGHT_MIN + 1, height - HEIGHT_MAX);
        int yMax = Math.max(WIDTH_MIN + 1, width - WIDTH_MAX);
        if (xMax <= HEIGHT_MIN || yMax <= WIDTH_MIN) {
            return new Point(0, 0);
        }
        return new Point(random.nextInt(HEIGHT_MIN, xMax), random.nextInt(WIDTH_MIN, yMax));
    }

    /**
     * Генерирует случайное целое число в диапазоне [0, num-1]
     *
     * @param num верхняя граница (исключительно)
     * @return случайное число от 0 до num-1
     */
    public int getRandomInt(int num) {
        if (num <= 0) {
            return 0;
        }
        return random.nextInt(num);
    }

    /**
     * Генерирует случайную точку с нечетными координатами
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return случайная точка с нечетными координатами
     */
    public Point getRandomPointOdd(int height, int width) {
        Point point = getRandomPoint(height, width);
        return new Point(Math.min(point.x() | 1, height - 1), Math.min(point.y() | 1, width - 1));
    }

    /**
     * Вычисляет эвристическое расстояние между двумя точками
     *
     * @param a первая точка
     * @param b вторая точка
     * @return расстояние между точками
     */
    public int heuristic(Point a, Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Восстанавливает путь от конечной точки до начальной по данным узлов
     *
     * @param nodeData двумерный массив данных узлов
     * @param end конечная точка пути
     * @return восстановленный путь от начала до конца
     */
    public Path reconstructPath(NodeData[][] nodeData, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;
        while (current != null) {
            path.add(current);
            NodeData currentData = nodeData[current.x()][current.y()];
            current = currentData.getParent();
        }
        Collections.reverse(path);
        return new Path(path.toArray(new Point[0]));
    }

    /**
     * Создает лабиринт заданного размера, заполненный стенами
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return новый лабиринт, заполненный стенами
     */
    public Maze createMaze(int height, int width) {
        CellType[][] cellType = new CellType[height][width];
        Maze maze = new Maze(cellType);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                maze.cells()[i][j] = CellType.WALL;
            }
        }
        return maze;
    }

    /**
     * Инициализирует двумерный массив данных узлов для алгоритмов поиска пути
     *
     * @param height высота массива
     * @param width ширина массива
     * @return инициализированный массив данных узлов
     */
    public NodeData[][] initNodeData(int height, int width) {
        NodeData[][] nodeData = new NodeData[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                nodeData[i][j] = new NodeData();
                nodeData[i][j].initMax();
            }
        }
        return nodeData;
    }

    /**
     * Вычисляет среднее значение двух целых чисел без переполнения
     *
     * @param a первое число
     * @param b второе число
     * @return среднее значение a и b
     */
    public int average(int a, int b) {
        return (a & b) + ((a ^ b) >>> 1);
    }

    public void updateOpenSet(PriorityQueue<Point> openSet, Point neighbor) {
        if (!openSet.contains(neighbor)) {
            openSet.add(neighbor);
        } else {
            openSet.remove(neighbor);
            openSet.add(neighbor);
        }
    }

    public void updateNeighborData(NodeData neighborData, Point current, int tentativePath, int heuristic) {
        neighborData.setParent(current);
        neighborData.setRealPath(tentativePath);
        neighborData.setHeuristicPath(heuristic);
    }
}
