package ui.blocks;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class EnemyPathBlock extends Tile {
    public EnemyPathBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        name = "Path";
        filled = true;
        topFaceColor = new Color(248, 175, 255, 200);
        rightFaceColor = topFaceColor;
        leftFaceColor = topFaceColor;
    }

    @Override
    public void setTopFaceVisible(boolean topFaceVisible) { }

    @Override
    public void setRightFaceVisible(boolean rightFaceVisible) { }

    @Override
    public void setLeftFaceVisible(boolean leftFaceVisible) { }
}
