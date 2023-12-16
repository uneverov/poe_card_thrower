package uneverov.evgeny.poe_card_thrower;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import uneverov.evgeny.poe_card_thrower.utils.GlobalKeyListenerExample;
import java.io.IOException;


public class Application extends javafx.application.Application implements NativeKeyListener {

    public static Scene scene;
    public static boolean pause;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("POE CARD THROWER");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Loader.load(opencv_java.class);
        launch();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            Application.pause = true;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    }
}