package ui.blocks;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class RoadBlock extends Block {
    public RoadBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        int colorModifier = (int) (Math.random() * 60);
        if ((int) (Math.random() * 2) % 2 == 0 && (int) (Math.random() * 2) % 2 == 0)
            colorModifier = (-colorModifier);
        
        name = "Road";
        filled = true;
        topFaceColor = new Color(120 + colorModifier, 120 + colorModifier, 120 + colorModifier);
        rightFaceColor = topFaceColor;
        leftFaceColor = new Color(80, 80, 80);
    }
}
