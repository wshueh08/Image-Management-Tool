package org.example.my_final_project;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;


// User interactions and coordinates the model and view
public class ImageController {
    private ImageModel model;
    private ImageDetailsView view;
    private Stage primaryStage;
    private File lastConvertedFile;
    private File uploadedFile;

    public ImageController(ImageModel model, ImageDetailsView view, Stage primaryStage) {
        this.model = model;
        this.view = view;
        this.primaryStage = primaryStage;
        setupHandlers();
    }

    //Handles setup of action handlers for buttons in the view
    private void setupHandlers() {
        view.getConvertButton().setOnAction(e -> {
            String format = view.getSelectedFormat();
            if (format != null && !format.isEmpty()) {
                if (uploadedFile != null) {
                    try {
                        Path outputPath = Paths.get(uploadedFile.getParent(), "converted_image." + format);
                        File outputFile = outputPath.toFile();
                        convertImage(uploadedFile, outputFile, format);
                        lastConvertedFile = outputFile;
                    } catch (Exception ex) {
                        view.updateInfo("Error during file preparation: " + ex.getMessage());
                    }
                } else {
                    view.updateInfo("No file uploaded for conversion.");
                }
            } else {
                view.updateInfo("Please select a format to convert.");
            }
        });

        view.getDownloadButton().setOnAction(e -> downloadImage());
    }

    //To upload an image using a file chooser dialog
    public void onImageUpload() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            uploadedFile = file;
            updateImageView(file);
        }
    }

    //Updates the view with details of the uploaded image file
    private void updateImageView(File imageFile) {
        try {
            if (imageFile.exists()) {
                ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
                Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    reader.setInput(iis, true);
                    BufferedImage bImage = reader.read(0);
                    Image image = new Image(imageFile.toURI().toString());
                    String width = Integer.toString(bImage.getWidth());
                    String height = Integer.toString(bImage.getHeight());
                    String filePath = imageFile.getAbsolutePath();

                    view.updateImageDetails(image, height, width, "File path: " + filePath);
                }
                iis.close();
            } else {
                view.updateInfo("Image file does not exist.");
            }
        } catch (Exception e) {
            view.updateInfo("Failed to load image: " + e.getMessage());
        }
    }

    //Converts the uploaded image file to a selected format and updates the view
    public void convertImage(File inputFile, File outputFile, String format) {
        try {
            model.convertImage(inputFile, format, outputFile);
            if (outputFile.exists()) {
                System.out.println("File created successfully: " + outputFile.getAbsolutePath());
                lastConvertedFile = outputFile;
                view.updateInfo("Conversion successful. Ready to download.");
            } else {
                System.out.println("File creation failed, file not found after supposed creation.");
                view.updateInfo("Conversion failed, file not found.");
            }
        } catch (IOException e) {
            System.out.println("Error during conversion: " + e.getMessage());
            view.updateInfo("Error during conversion: " + e.getMessage());
        }
    }

    // Downloads the converted image to the user's desktop and provides feedback
    private void downloadImage() {
        if (lastConvertedFile != null && lastConvertedFile.exists()) {
            try {
                String targetPath = System.getProperty("user.home") + "/Desktop";
                File targetDir = new File(targetPath);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }

                File fileToSave = new File(targetDir, lastConvertedFile.getName());
                Files.copy(Paths.get(lastConvertedFile.toURI()), Paths.get(fileToSave.toURI()), StandardCopyOption.REPLACE_EXISTING);
                view.updateInfo("File successfully saved to: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                view.updateInfo("Failed to save file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            view.updateInfo("No file to download or file does not exist.");
        }
    }


}
