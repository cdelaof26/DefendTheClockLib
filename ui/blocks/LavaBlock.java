package ui.blocks;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class LavaBlock extends LiquidBlock {
    public LavaBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        int colorModifier = (int) (Math.random() * 50);
        if ((int) (Math.random() * 2) % 2 == 0 && (int) (Math.random() * 2) % 2 == 0)
            colorModifier = (-colorModifier);
        
        name = "Lava";
        filled = true;
        topFaceColor = new Color(colorModifier > 0 ? 255 : 255 + colorModifier, 77 + colorModifier, colorModifier < 0 ? 0 : 0 + colorModifier);
        rightFaceColor = new Color(colorModifier > 0 ? 255 : 255 + colorModifier, 154 + colorModifier, colorModifier < 0 ? 2 : 2 + colorModifier);
        leftFaceColor = new Color(205, 66, 0);
    }
}
