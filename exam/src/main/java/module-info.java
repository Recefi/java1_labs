module org.recefi.exam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.google.gson;


    exports org.recefi.exam.client;
    opens org.recefi.exam.client to javafx.fxml;
    opens org.recefi.exam to com.google.gson;
}