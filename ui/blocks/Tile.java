package ui.blocks;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import ui.UIProperties;

/**
 *
 * @author cristopher
 */
public class Tile extends Block {
    public Tile(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
    }
    
    @Override
    public void createFaces() {
        pt0 = new Point2D.Double((x + diagonalLength) * UIProperties.getUiScale(), (y + diagonalHHLength + diagonalHHHLength * 3) * UIProperties.getUiScale());
        pt1 = new Point2D.Double((x + diagonalHalfLength) * UIProperties.getUiScale(), (y + diagonalHalfLength + diagonalHHHLength * 3) * UIProperties.getUiScale());
        pt2 = new Point2D.Double(x * UIProperties.getUiScale(), (y + diagonalHHLength + diagonalHHHLength * 3) * UIProperties.getUiScale());
        
        
        topFace = new Path2D.Double();

        topFace.moveTo((x + diagonalHalfLength) * UIProperties.getUiScale(), (y + diagonalHHHLength * 3) * UIProperties.getUiScale());
        topFace.lineTo(pt0.getX(), pt0.getY());
        topFace.lineTo(pt1.getX(), pt1.getY());
        topFace.lineTo(pt2.getX(), pt2.getY());
        topFace.closePath();
        
        
        rightFace = new Path2D.Double();

        rightFace.moveTo(pt1.getX(), pt1.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY() + diagonalHHHLength * UIProperties.getUiScale());
        rightFace.lineTo(pt1.getX(), pt1.getY() + diagonalHHHLength * UIProperties.getUiScale());
        rightFace.closePath();
        
        
        leftFace = new Path2D.Double();

        leftFace.moveTo(pt1.getX(), pt1.getY());
        leftFace.lineTo(pt1.getX(), pt1.getY() + diagonalHHHLength * UIProperties.getUiScale());
        leftFace.lineTo(pt2.getX(), pt2.getY() + diagonalHHHLength * UIProperties.getUiScale());
        leftFace.lineTo(pt2.getX(), pt2.getY());
        leftFace.closePath();
    }
    
    @Override
    public void paintSelectedIndicator(Graphics2D g2D) {
        if (!selected || !selectable)
            return;
        
        Path2D selectorIndicator = new Path2D.Double();
        
        selectorIndicator.moveTo(pt2.getX(), pt2.getY() + diagonalHHHLength * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt1.getX(), pt1.getY() + diagonalHHHLength * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt0.getX(), pt0.getY() + diagonalHHHLength * UIProperties.getUiScale());
        
        selectorIndicator.lineTo(pt0.getX() + diagonalHHLength * UIProperties.getUiScale(), pt0.getY() + diagonalHHLength * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt1.getX(), pt1.getY() + (diagonalHHLength + diagonalHHHLength) * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt2.getX() - diagonalHHLength * UIProperties.getUiScale(), pt2.getY() + diagonalHHLength * UIProperties.getUiScale());
        
        selectorIndicator.closePath();
        
        g2D.setColor(UIProperties.APP_BG_COLOR);
        g2D.fill(selectorIndicator);
    }
}