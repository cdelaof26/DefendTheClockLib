package ui.blocks;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class EnemySpawnBlock extends LiquidBlock {
    public EnemySpawnBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);

        name = "Spawn";
        filled = true;
        topFaceColor = new Color(198, 125, 255, 200);
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
