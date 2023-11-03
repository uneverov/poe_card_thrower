package uneverov.evgeny.poe_card_thrower;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onStartButtonClick() throws IOException {
        JNA.swithWindow("Path of Exile");
    }
}