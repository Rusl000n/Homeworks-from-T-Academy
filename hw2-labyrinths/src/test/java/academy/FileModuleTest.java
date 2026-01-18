package academy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import academy.maze.dto.Maze;
import academy.maze.dto.enums.CellType;
import academy.maze.ioModule.FileModule;
import academy.maze.utils.Convertors;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class FileModuleTest {

    FileModule fileModule = new FileModule();
    Convertors convertors = new Convertors();

    @Test
    void test1() {
        try {
            Maze maze = convertors.listToMaze(fileModule.readToList("src/test/resources/test_cases/2.txt"));
            assertThat(maze.cells()[0][0]).isEqualTo(CellType.WALL);
            assertThat(maze.cells()[1][1]).isEqualTo(CellType.PATH);
        } catch (IOException e) {
            assertThat(false).isEqualTo(true);
        }
    }

    @Test
    void test2() {
        try {
            Maze maze = convertors.listToMaze(fileModule.readToList("src/test/resources/test_cases/WrongTest.txt"));
            assertThat(false).isEqualTo(true);
        } catch (IOException e) {
            assertThat(true).isEqualTo(true);
        }
    }
}
