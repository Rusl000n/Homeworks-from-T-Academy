package academy.maze.dto.enums;

/** Перечисление типов вывода лабиринта */
public enum TypeOutput {
    STANDARD,
    UNICODE;

    /**
     * Преобразование строки в значение перечисления
     *
     * @param value строковое представление типа вывода
     * @return соответствующее значение TypeOutput
     */
    public static TypeOutput fromString(String value) {
        String input = value.toLowerCase();
        for (TypeOutput type : values()) {
            if (type.toString().equals(input)) {
                return type;
            }
        }
        return STANDARD;
    }
}
