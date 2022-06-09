module hov {
    requires javafx.controls;
    requires javafx.fxml;

    opens hov to javafx.fxml;
    requires pt.gov.cartaodecidadao;
    exports hov;
}
