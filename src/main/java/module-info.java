module com.evening_programmer.eulerhorsealgorithm {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.eulerhorsealgorithm to javafx.fxml;
    exports main.eulerhorsealgorithm;
}