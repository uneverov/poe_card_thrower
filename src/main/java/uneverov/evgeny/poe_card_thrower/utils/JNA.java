package uneverov.evgeny.poe_card_thrower.utils;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class JNA {
    public static void switchWindow(String windowName) {
        HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
        if (!(hwnd == null)) {
            User32.INSTANCE.BringWindowToTop(hwnd);
    } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please, launch Path Of Exile", ButtonType.OK);
            alert.showAndWait();
        }
    }
}