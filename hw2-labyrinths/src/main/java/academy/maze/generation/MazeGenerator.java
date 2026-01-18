package academy.maze.generation;

import academy.maze.dto.Maze;

/** Генератор лабиринта */
public interface MazeGenerator {

    /**
     * Генерирует лабиринт.
     *
     * @param height высота лабиринта.
     * @param width ширина лабиринта.
     * @return лабиринт
     * @throws IllegalArgumentException если невозможно сгенерировать лабиринт.
     */
    Maze generate(int height, int width);
}
