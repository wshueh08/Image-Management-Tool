package org.example.my_final_project;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Concrete implementation of BaseImage that performs the image conversion operation.

public class ImageConverter extends BaseImage {
    @Override
    public void performOperation(File input, File output, String format) throws IOException {
        try {
            BufferedImage bufferedImage = ImageIO.read(input);
            ImageIO.write(bufferedImage, format, output);
        } catch (IOException e) {
            throw new IOException("Error during conversion: " + e.getMessage(), e);
        }
    }
}