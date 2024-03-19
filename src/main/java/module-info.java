module com.example.esm_javastocks {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.esm_javastocks to javafx.fxml;
    exports com.example.esm_javastocks;
}