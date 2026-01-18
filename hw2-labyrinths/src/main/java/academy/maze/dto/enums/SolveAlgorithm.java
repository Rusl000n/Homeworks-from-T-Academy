package academy.maze.dto.enums;

/** Перечисление алгоритмов решения лабиринта */
public enum SolveAlgorithm {
    ASTAR,
    DIJKSTRA;

    /**
     * Преобразование строки в значение перечисления
     *
     * @param value строковое представление алгоритма
     * @return соответствующее значение SolveAlgorithm
     */
    public static SolveAlgorithm fromString(String value) {
        String input = value.toLowerCase();
        for (SolveAlgorithm solves : values()) {
            if (solves.toString().toLowerCase().equals(input)) {
                return solves;
            }
        }
        return null;
    }
}
