package academy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import academy.maze.dto.Maze;
import academy.maze.generation.PrimMazeGenerator;
import org.junit.jupiter.api.Test;

public class PrimMazeGeneratorTest {

    PrimMazeGenerator primGenerator = new PrimMazeGenerator();

    @Test
    public void test1() {
        Maze maze = primGenerator.generate(51, 11);
        assertThat(maze.getHeight()).isEqualTo(51);
        assertThat(maze.getWidth()).isEqualTo(11);
    }

    @Test
    public void test2() {
        try {
            primGenerator.generate(51, 10);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().charAt(0)).isEqualTo('I');
        }
    }

    @Test
    public void test3() {
        try {
            primGenerator.generate(-11, 11);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage().charAt(0)).isEqualTo('I');
        }
    }
}
