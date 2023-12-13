package uneverov.evgeny.poe_card_thrower;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import uneverov.evgeny.poe_card_thrower.managers.TestPropManager;
import uneverov.evgeny.poe_card_thrower.utils.JNA;
import uneverov.evgeny.poe_card_thrower.utils.MatchTemplate;
import uneverov.evgeny.poe_card_thrower.utils.RobotThrow;
import java.awt.*;

import java.io.IOException;
import java.util.List;


import static uneverov.evgeny.poe_card_thrower.utils.MatchTemplate.matFromScreen;
import static uneverov.evgeny.poe_card_thrower.utils.PropConst.*;


public class Controller {
    @FXML
    private Label welcomeText;
    private static final TestPropManager props = TestPropManager.getTestPropManager();
    public static final Mat TEMPL_MAT = Imgcodecs.imread(props.getProperty(TMPL_PATH), Imgcodecs.IMREAD_COLOR);
    public static final Mat TEMPL_LIFE_MAT = Imgcodecs.imread(props.getProperty(LIFE_PATH), Imgcodecs.IMREAD_COLOR);
    public static final Mat TEMP_MAT = Imgcodecs.imread(props.getProperty(TEMP_PATH), Imgcodecs.IMREAD_COLOR);
    public static final Mat TEMPL_INVENTORY_MAT = Imgcodecs.imread(props.getProperty(INV_PATH), Imgcodecs.IMREAD_COLOR);

    @FXML
    protected void onStartButtonClick() throws IOException, AWTException {
        JNA.switchWindow("Path of Exile");
        RobotThrow.openInventory(TEMPL_INVENTORY_MAT);
        Mat matScreen = matFromScreen();
        Mat[] stackDecks = {matScreen, TEMPL_MAT}, throwSpace = {matScreen, TEMPL_LIFE_MAT};
        List<Point> matches = MatchTemplate.run(stackDecks), matches2 = MatchTemplate.run(throwSpace);
        if (matches.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Stacked Deck not found", ButtonType.OK);
            alert.showAndWait();
        }
        if (Application.pause) {
            return;
        }
        RobotThrow.startThrow(matches, matches2, TEMPL_LIFE_MAT, TEMP_MAT);
    }

}