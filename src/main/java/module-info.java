module hov {
    requires javafx.controls;
    requires javafx.fxml;
    requires pteidlibj;

    opens hov to javafx.fxml;
    exports hov;
}
