package uneverov.evgeny.poe_card_thrower;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static uneverov.evgeny.poe_card_thrower.MatchTemplate.BufferedImage2Mat;

public class RobotThrow implements ChangeListener {
    int match_method;
    static List<Point> matches3 = new ArrayList<>();
    static List<Point> matches = new ArrayList<>();
    public static void startThrow(List<Point> args_1,
                                  List<Point> args_2,
                                  Mat templ_life,
                                  Mat temp,
                                  Mat templ_inventory,
                                  Mat templ_empty_slot) throws AWTException, IOException {
        Robot robot = new Robot();
        boolean finished = false;
        for (int i = 0; i < 10 && !finished; ++i) {
            for (Point match : args_1) {
                robot.keyPress(KeyEvent.VK_ALT);
                robot.mouseMove((int) match.x, (int) (match.y));
                robot.delay(150);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(150);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(150);
                robot.mouseMove((int) args_2.get(0).x + templ_life.width() / 2, (int) (args_2.get(0).y) + templ_life.height() / 2);
                robot.delay(150);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(150);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(150);
                BufferedImage temp_image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                Mat temp_mat = BufferedImage2Mat(temp_image);
                Mat[] myArray3 = {temp_mat, temp};
                matches3 = MatchTemplate.run(myArray3);
                if (!matches3.isEmpty()) {
                    robot.keyRelease(KeyEvent.VK_ALT);
                    System.out.println("Поле заполнено");
                    BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    Mat image_mat = BufferedImage2Mat(image);
                    Mat[] myArray2 = {image_mat, templ_empty_slot};
                    matches = MatchTemplate.run(myArray2);
                    robot.mouseMove((int)matches.get(0).x, (int)matches.get(0).y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(150);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(150);
                    finished = true;
                    //RobotThrow.closeInventory(templ_inventory);
                    break;
                }
            }
        }
    }

    public static void openInventory (Mat templ_inventory) throws AWTException, IOException {
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        Mat mat_main = BufferedImage2Mat(image);
        Mat [] myArray4 = {mat_main, templ_inventory};
        if (MatchTemplate.run(myArray4).isEmpty()) {
            robot.keyPress(KeyEvent.VK_I);
            robot.keyRelease(KeyEvent.VK_I);
            robot.delay(200);
        }
    }

    public static void closeInventory (Mat templ_inventory) throws AWTException, IOException {
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        Mat mat_main = BufferedImage2Mat(image);
        Mat [] myArray4 = {mat_main, templ_inventory};
        if (!MatchTemplate.run(myArray4).isEmpty()) {
            robot.keyPress(KeyEvent.VK_I);
            robot.keyRelease(KeyEvent.VK_I);
            robot.delay(200);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            match_method = source.getValue();
        }
    }
}
