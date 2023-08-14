package utils;

import ui.ImageButton;

/**
 *
 * @author cristopher
 */
public class AppUtilities {
    public static void addBundleImagesToImageButton(ImageButton ib, String name, int width) {
        ib.setLightThemedImage("ui/assets/l" + name + ".png", true, width, width);
        ib.setDarkThemedImage("ui/assets/d" + name + ".png", true, width, width);
        ib.setHoverImage("ui/assets/d" + name + ".png", true);
    }
}
