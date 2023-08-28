package defendtheclocklib;

import ui.GameWindow;
import ui.LoginWindow;
import ui.enums.GameModes;
import utils.FileUtilities;
import utils.LibUtilities;
import utils.WorldUtilities;

/**
 *
 * @author cristopher
 */
public class DefendTheClockLib {
    public static void main(String[] args) {
        LibUtilities.loadDefaultPreferences();
        
        // idk if this is helpful at all
        System.setProperty("sun.java2d.opengl", "true");
        
//        new GameWindow(null, WorldUtilities.loadWorld(FileUtilities.joinPath(WorldUtilities.WORLDS_DIRECTORY, "Remote_Island.dtcl")), GameModes.CONSTRUCTION, "").showWindow();
        
        LoginWindow loginWindow = new LoginWindow();
        LibUtilities.loadPreferences(loginWindow.uiPreferences);
        loginWindow.showWindow();
    }
}
