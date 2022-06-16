package hov;

import java.io.IOException;

import hov.libs.CardReader;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        CardReader reader = new CardReader();
        App.setRoot("secondary");
    }
}
