package ui.blocks;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class WaterBlock extends LiquidBlock {
    public WaterBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        name = "Water";
        filled = true;
        topFaceColor = new Color(30, 163, 236);
        leftFaceColor = new Color(0, 133, 206);
        rightFaceColor = topFaceColor;
    }
}
