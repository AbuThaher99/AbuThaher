module com.example.projecttwo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projecttwo to javafx.fxml;
    exports com.example.projecttwo;
}