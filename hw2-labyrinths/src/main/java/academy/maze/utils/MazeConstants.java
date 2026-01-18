package academy.maze.utils;

import academy.maze.dto.Point;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MazeConstants {

    public static final Point RIGHT = new Point(1, 0);
    public static final Point DOWN = new Point(0, 1);
    public static final Point LEFT = new Point(-1, 0);
    public static final Point UP = new Point(0, -1);

    public static final int PATH_LENGTH_ONE = 1;

    public static final List<Point> PATHS = List.of(RIGHT, DOWN, LEFT, UP);

    public static final int WIDTH_MIN = 1;
    public static final int HEIGHT_MIN = 1;
    public static final int WIDTH_MAX = 2;
    public static final int HEIGHT_MAX = 2;
    public static final int MIN_BOARDER = 2;

    public static final int NONE = 0;
}
