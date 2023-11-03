package uneverov.evgeny.poe_card_thrower;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
public class JNA {

    public static void swithWindow(String windowName) {
        HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
        if (!(hwnd == null)) {
            User32.INSTANCE.BringWindowToTop(hwnd);
    } else {
            System.out.print("No");
        }
    }
}