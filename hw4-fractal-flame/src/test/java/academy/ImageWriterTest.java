package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.dto.Field;
import academy.dto.Point;
import academy.ioModule.ImageWriter;
import java.io.File;
import org.junit.jupiter.api.Test;

class ImageWriterTest {

    private final ImageWriter writer = new ImageWriter();

    @Test
    void testWriteFractalImage() {
        try {
            Point[][] points = new Point[2][2];
            points[0][0] = new Point(0, 0, 0xFFFF0000);
            points[0][1] = new Point(1, 0, 0xFF00FF00);
            points[1][0] = new Point(0, 1, 0xFF0000FF);
            points[1][1] = new Point(1, 1, 0xFFFFFFFF);

            Field field = new Field(points);
            File tempFile = File.createTempFile("test_image", ".png");

            writer.writeFractalImage(field, tempFile);

            assertTrue(tempFile.exists());
            assertTrue(tempFile.length() > 0);

            tempFile.delete();

        } catch (Exception e) {
            fail("Exception during test: " + e.getMessage());
        }
    }
}
