package defendtheclocklib;

import java.io.File;
import ui.GameWindow;
import ui.LoginWindow;
import ui.enums.GameModes;
import utils.AppUtilities;
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
        
//        new GameWindow(null, WorldUtilities.loadWorld(FileUtilities.joinPath(WorldUtilities.WORLDS_DIRECTORY, "Remote_Island.dtcl")), GameModes.CONSTRUCTION, "").showWindow();
        
        LoginWindow loginWindow = new LoginWindow();
        LibUtilities.loadPreferences(loginWindow.uiPreferences);
        loginWindow.showWindow();
    }
}
