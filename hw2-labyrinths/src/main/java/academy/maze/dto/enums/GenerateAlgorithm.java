package academy.maze.dto.enums;

/** Перечисление алгоритмов генерации лабиринта */
public enum GenerateAlgorithm {
    DFS,
    PRIM;

    /**
     * Преобразование строки в значение перечисления
     *
     * @param value строковое представление алгоритма
     * @return соответствующее значение GenerateAlgorithm
     */
    public static GenerateAlgorithm fromString(String value) {
        String input = value.toLowerCase();
        for (GenerateAlgorithm generates : values()) {
            if (generates.toString().toLowerCase().equals(input)) {
                return generates;
            }
        }
        return null;
    }
}
