module FINAL_EXAM {
    requires javafx.baseEmpty;
    requires javafx.base;
    requires javafx.fxmlEmpty;
    requires javafx.fxml;
    requires javafx.controlsEmpty;
    requires javafx.controls;
    requires javafx.graphicsEmpty;
    requires javafx.graphics;
    
    //for java sql
    requires java.sql;

    opens FINAL_EXAM to javafx.fxml;
    exports FINAL_EXAM;
}