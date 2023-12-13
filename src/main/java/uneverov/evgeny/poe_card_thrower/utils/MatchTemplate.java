package uneverov.evgeny.poe_card_thrower.utils;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import uneverov.evgeny.poe_card_thrower.managers.RobotManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchTemplate implements ChangeListener {
    static Mat img = new Mat();
    static Mat templ = new Mat();
    static Mat result = new Mat();
    int match_method;
    static Robot robot;

    static {
        try {
            robot = RobotManager.getRobotManager().getRobot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Point> run(Mat[] args) {
        img = args[0];
        templ = args[1];
        result = new Mat();
        List<Point> matches = new ArrayList<>();
        Imgproc.matchTemplate(img, templ, result, Imgproc.TM_CCORR_NORMED);
        Imgproc.threshold(result, result, 0.1, 1, Imgproc.THRESH_TOZERO);
        double threshold = 0.96;
        double maxval;
        while (true) {
            Core.MinMaxLocResult maxr = Core.minMaxLoc(result);
            Point maxp = maxr.maxLoc;
            maxval = maxr.maxVal;
            if (maxval >= threshold) {
                matches.add(maxp);
                Imgproc.rectangle(img, maxp, new Point(maxp.x + templ.cols(),
                        maxp.y + templ.rows()), new Scalar(0, 255, 0), 1);
                Imgproc.rectangle(result, maxp, new Point(maxp.x + templ.cols(),
                        maxp.y + templ.rows()), new Scalar(0, 255, 0), -1);

            } else {
                break;
            }
        }
    return matches;
    }

    public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
    }

    public static Mat matFromScreen() throws IOException {
        BufferedImage buffImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        return BufferedImage2Mat(buffImage);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            match_method = source.getValue();
        }
    }
}
