package academy.maze.ioModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileModule {

    /**
     * Чтение файла в список строк
     *
     * @param input путь к входному файлу
     * @return список строк содержимого файла
     * @throws IOException если произошла ошибка чтения
     */
    public List<String> readToList(String input) throws IOException {
        Path path = Path.of(input);
        return Files.readAllLines(path);
    }

    /**
     * Запись списка строк в файл
     *
     * @param output путь к выходному файлу
     * @param text список строк для записи
     * @throws IOException если произошла ошибка записи
     */
    public void writeFile(String output, List<String> text) throws IOException {
        Path path = Path.of(output);
        Files.write(path, text);
    }
}
