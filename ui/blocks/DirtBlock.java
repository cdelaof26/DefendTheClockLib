package ui.blocks;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class DirtBlock extends Block {
    public DirtBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        int colorModifier = (int) (Math.random() * 40);
        
        name = "Dirt";
        filled = true;
        topFaceColor = new Color(88 + colorModifier, 43 + colorModifier, colorModifier);
        rightFaceColor = topFaceColor;
        leftFaceColor = new Color(73, 35, 1);
    }
}
