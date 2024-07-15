package org.example.my_final_project;

import java.io.File;
import java.io.IOException;


// Manages the logic of image operations using a strategy pattern.

public class ImageModel {
    private final ImageOperation imageOperation;

    public ImageModel(ImageOperation imageOperation) {
        this.imageOperation = imageOperation;
    }

    public void convertImage(File input, String format, File output) throws IOException {
        imageOperation.performOperation(input, output, format);
    }
}

