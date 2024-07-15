module org.example.my_final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;  //add one


    requires org.controlsfx.controls;

    //add Apache Commons Imaging
    requires org.apache.commons.imaging;

    opens org.example.my_final_project to javafx.fxml;
    exports org.example.my_final_project;
}