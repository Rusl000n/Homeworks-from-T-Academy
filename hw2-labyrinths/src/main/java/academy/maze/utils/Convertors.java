package academy.maze.utils;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.dto.enums.CellType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Convertors {

    Utils utils = new Utils();

    /**
     * Преобразование списка строк в объект лабиринта
     *
     * @param list список строк, представляющих лабиринт
     * @return объект лабиринта
     * @throws IOException если формат данных некорректен
     */
    public Maze listToMaze(List<String> list) throws IOException {
        int h = list.size();
        int w = list.getFirst().length();
        Maze maze = utils.createMaze(h, w);
        try {
            for (int i = 0; i < h; ++i) {
                for (int j = 0; j < w; ++j) {
                    CellType cellType = CellType.fromChar(list.get(i).charAt(j));
                    if (cellType == null) {
                        throw new Exception();
                    }
                    maze.cells()[i][j] = cellType;
                }
            }
        } catch (Exception e) {
            throw new IOException("The incoming file contains incorrect characters");
        }
        return maze;
    }

    /**
     * Преобразование лабиринта в список строк
     *
     * @param maze лабиринт для преобразования
     * @return список строк, представляющих лабиринт
     */
    public List<String> mazeToList(Maze maze) {
        int h = maze.getHeight();
        int w = maze.getWidth();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < h; ++i) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < w; ++j) {
                str.append(cellToChar(maze.cells()[i][j]));
            }
            list.add(str.toString());
        }
        return list;
    }

    /**
     * Преобразование строки в формате "x,y" в объект точки
     *
     * @param string строка в формате "x,y"
     * @return объект точки
     * @throws IllegalArgumentException если формат строки некорректен
     */
    public Point stringToPoint(String string) throws IllegalArgumentException {
        try {
            String[] strings = string.split(",");
            return new Point(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
        } catch (Exception e) {
            String error = String.format("Invalid point format: %s, expected format: x,y", string);
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Объединение лабиринта и пути для вывода
     *
     * @param maze исходный лабиринт
     * @param path путь в лабиринте
     * @return лабиринт с отмеченным путем
     * @throws IllegalArgumentException если путь содержит стены
     */
    public Maze concatMazePath(Maze maze, Path path) throws IllegalArgumentException {
        for (int i = 0; i < path.points().length; ++i) {
            Point p = path.points()[i];
            CellType cell = maze.cells()[p.x()][p.y()];
            if (cell == CellType.WALL) {
                throw new IllegalArgumentException("Error, the path contains walls");
            } else {
                if (i == 0) {
                    maze.cells()[p.x()][p.y()] = CellType.START;
                } else if (i == path.points().length - 1) {
                    maze.cells()[p.x()][p.y()] = CellType.END;
                } else {
                    maze.cells()[p.x()][p.y()] = CellType.WAY;
                }
            }
        }
        return maze;
    }

    /**
     * Преобразование типа ячейки в символ
     *
     * @param cell тип ячейки
     * @return символьное представление ячейки
     */
    public char cellToChar(CellType cell) {
        return switch (cell) {
            case PATH -> ' ';
            case WALL -> '#';
            case WAY -> '.';
            case START -> 'O';
            case END -> 'X';
        };
    }

    /**
     * Преобразование типа ячейки в Unicode символ
     *
     * @param cell тип ячейки
     * @return Unicode символ для ячейки
     */
    public char cellToUnicodeChar(CellType cell) {
        return switch (cell) {
            case PATH -> ' ';
            case WALL -> '█';
            case WAY -> '•';
            case START -> 'Ⓢ';
            case END -> 'Ⓔ';
        };
    }
}
