package defendtheclocklib;

import java.io.File;
import ui.GameWindow;
import ui.LoginWindow;
import ui.enums.GameModes;
import utils.LibUtilities;
import utils.WorldUtilities;

/**
 *
 * @author cristopher
 */
public class DefendTheClockLib {
    public static void main(String[] args) {
        LibUtilities.loadDefaultPreferences();
        
//        new GameWindow(null, WorldUtilities.loadWorld(new File("/Users/cristopher/.dtc_worlds/Meadow_Island.dtcl")), GameModes.EASY, "").showWindow();
        
        LoginWindow loginWindow = new LoginWindow();
        LibUtilities.loadPreferences(loginWindow.uiPreferences);
        loginWindow.showWindow();
    }
}
