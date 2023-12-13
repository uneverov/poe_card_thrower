package uneverov.evgeny.poe_card_thrower.managers;

import java.awt.*;

public class RobotManager {

    private final Robot robot = new Robot();
    private static RobotManager INSTANCE = null;

    public RobotManager() throws AWTException {
    }

    public static RobotManager getRobotManager() throws AWTException {
        if (INSTANCE == null) {
            INSTANCE = new RobotManager();
        }
        return INSTANCE;
    }
    public Robot getRobot() {
        return robot;
    }
}
