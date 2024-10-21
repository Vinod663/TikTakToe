module org.example.tiktak {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens org.example.tiktak.Controller to javafx.fxml;
    exports org.example.tiktak;



}