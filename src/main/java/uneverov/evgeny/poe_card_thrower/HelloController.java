package uneverov.evgeny.poe_card_thrower;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloController {
    static List<Point> matches = new ArrayList<>();
    static List<Point> matches2 = new ArrayList<>();
    @FXML
    private Label welcomeText;

    public static final String TMPL_PATH = "src/main/resources/uneverov/evgeny/poe_card_thrower/img/tmpl.jpg";
    public static final String LIFE_PATH = "src/main/resources/uneverov/evgeny/poe_card_thrower/img/Life.jpg";

    @FXML
    protected void onStartButtonClick() throws IOException, AWTException {
        Loader.load(opencv_java.class);
        JNA.switchWindow("Path of Exile");
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        Mat mat_main = BufferedImage2Mat(image);
        Mat templ = Imgcodecs.imread(TMPL_PATH, Imgcodecs.IMREAD_COLOR);
        Mat templ_life = Imgcodecs.imread(LIFE_PATH, Imgcodecs.IMREAD_COLOR);
        Mat [] myArray1 = {mat_main, templ};
        Mat [] myArray2 = {mat_main, templ_life};
        matches = MatchTemplate.run(myArray1);
        matches2 = MatchTemplate.run(myArray2);
        for (int i = 0; i < 10; ++i) {
            for (Point match : matches) {
                robot.mouseMove((int) match.x, (int) (match.y));
                robot.delay(1000);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(1000);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(1000);
                robot.mouseMove((int) matches2.get(0).x, (int) (matches2.get(0).y));
                robot.delay(1000);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(1000);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(1000);
            }
        }
    }

    public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
    }

}