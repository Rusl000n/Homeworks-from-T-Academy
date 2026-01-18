package academy.maze.ioModule;

import academy.maze.dto.Maze;
import academy.maze.dto.enums.CellType;
import academy.maze.dto.enums.TypeOutput;
import academy.maze.utils.Convertors;

public class CLIModule {

    Convertors convertors = new Convertors();

    /**
     * Вывод сообщения об ошибке в консоль
     *
     * @param e сообщение об ошибке
     */
    public void printError(String e) {
        System.out.println(e);
    }

    /**
     * Вывод лабиринта в консоль
     *
     * @param maze лабиринт для вывода
     * @param type тип вывода (стандартный или Unicode)
     */
    public void printMaze(Maze maze, TypeOutput type) {
        for (int i = 0; i < maze.getHeight(); ++i) {
            for (int j = 0; j < maze.getWidth(); ++j) {
                CellType cell = maze.cells()[i][j];
                System.out.print(
                        switch (type) {
                            case STANDARD -> convertors.cellToChar(cell);
                            case UNICODE -> convertors.cellToUnicodeChar(cell);
                        });
            }
            System.out.println();
        }
    }
}
