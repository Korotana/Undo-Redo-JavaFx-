module com.example.undoredo2021fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.undoredo2021fx to javafx.fxml;
    exports com.example.undoredo2021fx;
}