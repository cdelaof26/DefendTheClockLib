package utils;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import ui.ImageButton;

/**
 *
 * @author cristopher
 */
public class AppUtilities {
    public static final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    
    public static void addBundleImagesToImageButton(ImageButton ib, String name, int width) {
        ib.setLightThemedImage("ui/assets/l" + name + ".png", true, width, width);
        ib.setDarkThemedImage("ui/assets/d" + name + ".png", true, width, width);
        ib.setHoverImage("ui/assets/d" + name + ".png", true);
    }
}
