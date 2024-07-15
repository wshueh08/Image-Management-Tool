package org.example.my_final_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Main class that sets up the JavaFX GUI and initiates the application components
public class HelloApplication extends Application {

    private ImageDetailsView imageView;
    private ImageModel model;
    private ImageController controller;

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageDetailsView();
        model = new ImageModel(new ImageConverter());
        controller = new ImageController(model, imageView, primaryStage);

        Button btnUpload = new Button("Upload Image");
        VBox vbox = new VBox(10, btnUpload, imageView.getLayout());
        Scene scene = new Scene(vbox, 800, 600);

        btnUpload.setOnAction(e -> controller.onImageUpload());


        primaryStage.setTitle("Image Converter App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}