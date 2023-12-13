package uneverov.evgeny.poe_card_thrower.utils;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import uneverov.evgeny.poe_card_thrower.managers.RobotManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import static uneverov.evgeny.poe_card_thrower.utils.MatchTemplate.matFromScreen;

public class RobotThrow implements ChangeListener {
    int match_method;
    static Robot robot;

    static {
        try {
            robot = RobotManager.getRobotManager().getRobot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startThrow(List<Point> args_1,
                                  List<Point> args_2,
                                  Mat templ_life,
                                  Mat temp) throws AWTException, IOException {
        Robot robot = new Robot();
        boolean finished = false;
        for (int i = 0; i < 10 && !finished; ++i) {
            for (Point match : args_1) {
                robot.keyPress(KeyEvent.VK_ALT);
                robot.mouseMove((int) match.x, (int) (match.y));
                robot.delay(250);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(250);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(250);
                robot.mouseMove((int) args_2.get(0).x + templ_life.width() / 2, (int) (args_2.get(0).y) + templ_life.height() / 2);
                robot.delay(250);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(250);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(250);
                if (!MatchTemplate.run(new Mat[]{matFromScreen(), temp}).isEmpty()) {
                    robot.mouseMove(Toolkit.getDefaultToolkit().getScreenSize().width/2-30, Toolkit.getDefaultToolkit().getScreenSize().height/2 - 25);
                    robot.delay(500);
                    robot.keyPress(KeyEvent.VK_E);
                    robot.delay(250);
                    robot.keyRelease(KeyEvent.VK_E);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(250);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(250);
                }
            }
        }
        robot.keyRelease(KeyEvent.VK_ALT);
    }

    public static void openInventory (Mat templ_inventory) throws IOException {
        Mat [] compareArray = {matFromScreen(), templ_inventory};
        if (MatchTemplate.run(compareArray).isEmpty()) {
            robot.keyPress(KeyEvent.VK_I);
            robot.keyRelease(KeyEvent.VK_I);
            robot.delay(300);
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
