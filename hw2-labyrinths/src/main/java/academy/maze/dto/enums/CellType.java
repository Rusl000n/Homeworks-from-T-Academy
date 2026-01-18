package academy.maze.dto.enums;

/** Тип ячейки в лабиринте. WALL - стена, PATH - свободная ячейка. START - начало пути. END - конец пути. WAY - путь. */
public enum CellType {
    WALL,
    PATH,
    START,
    END,
    WAY;

    public static CellType fromChar(char a) {
        return switch (a) {
            case '#' -> WALL;
            case ' ', 'O', 'X', 'О', 'Х', '.' -> PATH;
            default -> null;
        };
    }
}
