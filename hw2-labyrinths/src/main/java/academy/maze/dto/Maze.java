package academy.maze.dto;

import academy.maze.dto.enums.CellType;

/**
 * Лабиринт.
 *
 * @param cells Массив ячеек лабиринта.
 */
public record Maze(CellType[][] cells) {
    public int getWidth() {
        return cells()[0].length;
    }

    public int getHeight() {
        return cells().length;
    }
}
