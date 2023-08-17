package ui.blocks;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class SandBlock extends Block {
    public SandBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        
        int colorModifier = (int) (Math.random() * 50);
        if ((int) (Math.random() * 2) % 2 == 0 && (int) (Math.random() * 2) % 2 == 0)
            colorModifier = (-colorModifier);
        
        name = "Sand";
        filled = true;
        topFaceColor = new Color(colorModifier > 23 ? 255 : 232 + colorModifier, colorModifier > 33 ? 255 : 222 + colorModifier, 197 + colorModifier);
        rightFaceColor = topFaceColor;
        leftFaceColor = new Color(209, 200, 177);
    }
}
