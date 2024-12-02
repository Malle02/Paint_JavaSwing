package MonPackage.View;

import java.awt.*;

public class ThemeManager {
    private static boolean isDarkMode = false;

    public static void toggleDarkMode() {
        isDarkMode = !isDarkMode;
    }

    public static boolean isDarkMode() {
        return isDarkMode;
    }

    public static Color getBackgroundColor() {
        return isDarkMode ? new Color(45, 45, 45) : Color.WHITE;
    }

    public static Color getTextColor() {
        return isDarkMode ? Color.WHITE : Color.BLACK;
    }

    public static Color getButtonColor() {
        return isDarkMode ? new Color(60, 60, 60) : new Color(240, 240, 240);
    }

    public static Color getPanelColor() {
        return isDarkMode ? new Color(30, 30, 30) : new Color(255, 255, 255);
    }
}
