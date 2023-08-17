package ui.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 *
 * @author cristopher
 */
public class GrassBlock extends Block {
    private static final Color LEFT_GREEN_COLOR = new Color(0, 92, 83);
    
    public GrassBlock(double x, double y, double diagonalLength) {
        super(x, y, diagonalLength);
        
        name = "Grass";
        filled = true;
        topFaceColor = new Color(38, 132, 123);
        rightFaceColor = new Color(118, 73, 30);
        leftFaceColor = new Color(73, 35, 1);
    }
    
    private void paintMissingMaterial(Graphics2D g2D) {
        Path2D rightMaterial = new Path2D.Double();
        rightMaterial.moveTo(pt1.getX(), pt1.getY());
        rightMaterial.lineTo(pt0.getX(), pt0.getY());
        rightMaterial.lineTo(pt0.getX(), pt0.getY() + diagonalHHHLength);
        rightMaterial.lineTo(pt1.getX(), pt1.getY() + diagonalHHHLength);
        rightMaterial.closePath();

        if (rightFaceVisible) {
            g2D.setColor(topFaceColor);
            g2D.fill(rightMaterial);
        }
        
        
        Path2D leftMaterial = new Path2D.Double();
        leftMaterial.moveTo(pt1.getX(), pt1.getY());
        leftMaterial.lineTo(pt1.getX(), pt1.getY() + diagonalHHHLength);
        leftMaterial.lineTo(pt2.getX(), pt2.getY() + diagonalHHHLength);
        leftMaterial.lineTo(pt2.getX(), pt2.getY());
        leftMaterial.closePath();

        if (leftFaceVisible) {
            g2D.setColor(LEFT_GREEN_COLOR);
            g2D.fill(leftMaterial);
        }
        
        if (!selectable || topFaceVisible || rightFaceVisible || leftFaceVisible)
            return;
        
        g2D.setColor(topFaceColor);
        g2D.draw(rightMaterial);
        
        g2D.setColor(topFaceColor);
        g2D.draw(leftMaterial);
    }
    
    @Override
    public void paintBlock(Graphics2D g2D) {
        super.paintBlock(g2D);
        paintMissingMaterial(g2D);
    }
}
