package uneverov.evgeny.poe_card_thrower;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static uneverov.evgeny.poe_card_thrower.MatchTemplate.BufferedImage2Mat;


public class HelloController {
    static List<Point> matches = new ArrayList<>();
    static List<Point> matches2 = new ArrayList<>();
    boolean finished = false;
    @FXML
    private Label welcomeText;

    public static final String TMPL_PATH = "src/main/resources/uneverov/evgeny/poe_card_thrower/img/tmpl.jpg";
    public static final String LIFE_PATH = "src/main/resources/uneverov/evgeny/poe_card_thrower/img/Life.jpg";
    public static final String TEMP_PATH = "src/main/resources/uneverov/evgeny/poe_card_thrower/img/card_1.jpg";
    public static final String INV_PATH = "src/main/resources/uneverov/evgeny/poe_card_thrower/img/inventory.jpg";
    public static final String EMPTY_PATH = "src/main/resources/uneverov/evgeny/poe_card_thrower/img/empty_slot_2.jpg";
    @FXML
    protected void onStartButtonClick() throws IOException, AWTException {
        Loader.load(opencv_java.class);
        Robot robot = new Robot();
        JNA.switchWindow("Path of Exile");
        Mat templ = Imgcodecs.imread(TMPL_PATH, Imgcodecs.IMREAD_COLOR);
        Mat templ_life = Imgcodecs.imread(LIFE_PATH, Imgcodecs.IMREAD_COLOR);
        Mat temp = Imgcodecs.imread(TEMP_PATH, Imgcodecs.IMREAD_COLOR);
        Mat templ_inventory = Imgcodecs.imread(INV_PATH, Imgcodecs.IMREAD_COLOR);
        Mat templ_empty_slot = Imgcodecs.imread(EMPTY_PATH, Imgcodecs.IMREAD_COLOR);
        RobotThrow.openInventory(templ_inventory);
        BufferedImage image_2 = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        Mat mat_main_2 = BufferedImage2Mat(image_2);
        Mat[] myArray1 = {mat_main_2, templ};
        Mat[] myArray2 = {mat_main_2, templ_life};
        matches = MatchTemplate.run(myArray1);
        System.out.println(matches);
        matches2 = MatchTemplate.run(myArray2);
        RobotThrow.startThrow(matches, matches2, templ_life, temp, templ_inventory, templ_empty_slot);
        robot.mouseMove(image_2.getWidth() / 2 + (int) (image_2.getWidth() * 0.2), image_2.getHeight() / 2);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(150);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(1000);
    }
}