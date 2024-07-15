package org.example.my_final_project;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


// Provide layout and user interface for image details and actions.
public class ImageDetailsView extends VBox{
    private ImageView imageView;
    private Label labelInfo, dataDisplay;
    private Label labelHeight, labelWidth, labelLocation;
    private ComboBox<String> formatComboBox;
    private Button btnConvert, btnDownload;

    public ImageDetailsView() {
        super(10);
        imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        labelInfo = new Label("Select an image to view properties and options");
        dataDisplay = new Label("Image data: ");

        labelHeight = new Label("Height: ");
        labelWidth = new Label("Width: ");
        labelLocation = new Label("Location: ");

        formatComboBox = new ComboBox<>();
        formatComboBox.getItems().addAll("png", "jpg", "bmp", "gif");

        btnConvert = new Button("Convert");
        btnDownload = new Button("Download");

        this.getChildren().addAll(imageView, labelInfo, dataDisplay, labelHeight, labelWidth, labelLocation, formatComboBox, btnConvert, btnDownload);
    }


    public void updateImageDetails(Image image, String height, String width, String location) {
        imageView.setImage(image);
        labelHeight.setText("Height: " + height);
        labelWidth.setText("Width: " + width);
        labelLocation.setText("Location: " + location);
    }


    public void updateInfo(String info) {
        labelInfo.setText(info);
    }


    public VBox getLayout() {
        return this;
    }

    public String getSelectedFormat() {
        return formatComboBox.getValue();
    }

    public Button getConvertButton() {
        return btnConvert;
    }

    public Button getDownloadButton() {
        return btnDownload;
    }


}
