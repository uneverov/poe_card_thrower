package uneverov.evgeny.poe_card_thrower;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static Scene scene;
    public static boolean pause;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("POE CARD THROWER");
        stage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {
                pause = true;
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        Loader.load(opencv_java.class);
        launch();
    }
}