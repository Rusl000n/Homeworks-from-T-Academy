package academy.ioModule;

import academy.dto.Field;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ImageWriter {

    public void writeFractalImage(Field field, File output) throws IOException {
        BufferedImage bufferedImage =
                new BufferedImage(field.points()[0].length, field.points().length, BufferedImage.TYPE_INT_ARGB);
        Arrays.stream(field.points())
                .forEach(pointList -> Arrays.stream(pointList).forEach(point -> {
                    bufferedImage.setRGB(point.getX(), point.getY(), (int) point.getColor());
                }));
        ImageIO.write(bufferedImage, "png", output);
    }
}
