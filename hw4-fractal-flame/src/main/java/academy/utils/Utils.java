package academy.utils;

import static academy.utils.Constants.LEFT_BOARDER_DOUBLE;
import static academy.utils.Constants.RIGHT_BOARDER_DOUBLE;
import static academy.utils.Constants.WHITE;

import academy.dto.DoublePoint;
import academy.dto.DoublePoints;
import academy.dto.Field;
import academy.dto.Point;
import academy.dto.inputs.AffineParams;
import academy.dto.inputs.TransformFunctions;
import java.util.List;
import java.util.Random;

public class Utils {

    public AffineParams getRandomAffine(List<AffineParams> affineParamsList, Random random) {
        return affineParamsList.get(random.nextInt(affineParamsList.size()));
    }

    public Field convertToImageParams(List<DoublePoints> doublePointsList, int height, int width) {
        Point[][] points = initializeMultidimensionalPoints(height, width);
        doublePointsList.forEach(
                doublePoints -> doublePoints.getDoublePointList().forEach(doublePoint -> {
                    Point pixel = normalizePoint(doublePoint, height, width);
                    if (pixel.getY() >= 0 && pixel.getY() < height && pixel.getX() >= 0 && pixel.getX() < width) {
                        points[pixel.getY()][pixel.getX()] = new Point(pixel.getX(), pixel.getY(), doublePoint.color());
                    }
                }));
        return new Field(points);
    }

    private Point[][] initializeMultidimensionalPoints(int n, int m) {
        Point[][] points = new Point[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                points[i][j] = new Point(j, i, WHITE);
            }
        }
        return points;
    }

    private Point normalizePoint(DoublePoint point, int height, int width) {
        double clampedX = Math.max(LEFT_BOARDER_DOUBLE, Math.min(RIGHT_BOARDER_DOUBLE, point.x()));
        double clampedY = Math.max(LEFT_BOARDER_DOUBLE, Math.min(RIGHT_BOARDER_DOUBLE, point.y()));
        int X = (int) ((clampedX - LEFT_BOARDER_DOUBLE) / (RIGHT_BOARDER_DOUBLE - LEFT_BOARDER_DOUBLE) * (width - 1));
        int Y = (int) ((clampedY - LEFT_BOARDER_DOUBLE) / (RIGHT_BOARDER_DOUBLE - LEFT_BOARDER_DOUBLE) * (height - 1));
        X = Math.max(0, Math.min(width - 1, X));
        Y = Math.max(0, Math.min(height - 1, Y));
        Y = height - 1 - Y;
        return new Point(X, Y, point.color());
    }

    public TransformFunctions getRandomFunction(List<TransformFunctions> functions, Random random) {
        double totalWeight = 0.0;
        for (TransformFunctions func : functions) {
            totalWeight += func.getWeight();
        }
        double randomValue = random.nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;
        for (TransformFunctions func : functions) {
            cumulativeWeight += func.getWeight();
            if (randomValue <= cumulativeWeight) {
                return func;
            }
        }
        return functions.getLast();
    }
}
