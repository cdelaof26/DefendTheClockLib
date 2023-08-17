package ui.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import ui.UIProperties;

/**
 *
 * @author cristopher
 */
public class Block {
    public enum BlockProperties {
        NAME, XGRID, YGRID, TOP_FACE_VISIBLE, RIGHT_FACE_VISIBLE, LEFT_FACE_VISIBLE
    }
    
    protected String name = "Wire";
    protected double x, y;
    protected double xGrid, yGrid;
    
    protected double diagonalLength;
    protected double diagonalHalfLength;
    protected double diagonalHHLength;
    protected double diagonalHHHLength;

    protected boolean selected = false;
    protected boolean selectable = true;
    
    protected boolean filled = false;
    
    protected boolean topFaceVisible = true;
    protected boolean rightFaceVisible = true;
    protected boolean leftFaceVisible = true;
    
    protected Color topFaceColor = UIProperties.DIM_TEXT_COLOR;
    protected Color rightFaceColor = UIProperties.DIM_TEXT_COLOR;
    protected Color leftFaceColor = UIProperties.DIM_TEXT_COLOR;
    
    
    /**
     * Right point shared by top and right faces
     */
    protected Point2D pt0 = null;
    
    /**
     * Bottom point shared by all faces
     */
    protected Point2D pt1 = null;
    
    /**
     * Left point shared by top and left faces
     */
    protected Point2D pt2 = null;
    
    protected Path2D topFace = null;
    protected Path2D rightFace = null;
    protected Path2D leftFace = null;
    
    
    public Block(double xGrid, double yGrid, double diagonalLength) {
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        this.x = (xGrid - 1) * diagonalLength;
        this.y = (yGrid - 1) * diagonalLength;
        
        setDiagonalLength(diagonalLength);
    }
    
    public void createFaces() {
        pt0 = new Point2D.Double((x + diagonalLength) * UIProperties.getUiScale(), (y + diagonalHHLength) * UIProperties.getUiScale());
        pt1 = new Point2D.Double((x + diagonalHalfLength) * UIProperties.getUiScale(), (y + diagonalHalfLength) * UIProperties.getUiScale());
        pt2 = new Point2D.Double(x * UIProperties.getUiScale(), (y + diagonalHHLength) * UIProperties.getUiScale());
        
        
        topFace = new Path2D.Double();

        topFace.moveTo((x + diagonalHalfLength) * UIProperties.getUiScale(), y * UIProperties.getUiScale());
        topFace.lineTo(pt0.getX(), pt0.getY());
        topFace.lineTo(pt1.getX(), pt1.getY());
        topFace.lineTo(pt2.getX(), pt2.getY());
        topFace.closePath();
        
        
        rightFace = new Path2D.Double();

        rightFace.moveTo(pt1.getX(), pt1.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY() + diagonalHalfLength * UIProperties.getUiScale());
        rightFace.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength * UIProperties.getUiScale());
        rightFace.closePath();
        
        
        leftFace = new Path2D.Double();

        leftFace.moveTo(pt1.getX(), pt1.getY());
        leftFace.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength * UIProperties.getUiScale());
        leftFace.lineTo(pt2.getX(), pt2.getY() + diagonalHalfLength * UIProperties.getUiScale());
        leftFace.lineTo(pt2.getX(), pt2.getY());
        leftFace.closePath();
    }
    
    public void paintSelectedIndicator(Graphics2D g2D) {
        if (!selected || !selectable)
            return;
        
        Path2D selectorIndicator = new Path2D.Double();
        
        selectorIndicator.moveTo(pt2.getX(), pt2.getY() + diagonalHalfLength * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt0.getX(), pt0.getY() + diagonalHalfLength * UIProperties.getUiScale());
        
        selectorIndicator.lineTo(pt0.getX() + diagonalHHLength * UIProperties.getUiScale(), pt0.getY() + (diagonalHalfLength + diagonalHHHLength) * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt1.getX(), pt1.getY() + (diagonalHalfLength + diagonalHHLength) * UIProperties.getUiScale());
        selectorIndicator.lineTo(pt2.getX() - diagonalHHLength * UIProperties.getUiScale(), pt2.getY() + (diagonalHalfLength + diagonalHHHLength) * UIProperties.getUiScale());
        
        selectorIndicator.closePath();
        
        g2D.setColor(UIProperties.APP_BG_COLOR);
        g2D.fill(selectorIndicator);
    }
    
    public void paintBox(Graphics2D g2D) {
        if (!selectable || topFaceVisible || rightFaceVisible || leftFaceVisible)
            return;
        
        g2D.setColor(topFaceColor);
        g2D.draw(topFace);
        
        g2D.setColor(rightFaceColor);
        g2D.draw(rightFace);
        
        g2D.setColor(leftFaceColor);
        g2D.draw(leftFace);
    }
    
    public void paintBlock(Graphics2D g2D) {
        createFaces();
        
        if (topFaceVisible) {
            g2D.setColor(topFaceColor);
            
            if (filled)
                g2D.fill(topFace);
            else
                g2D.draw(topFace);
        }
        
        if (rightFaceVisible) {
            g2D.setColor(rightFaceColor);
            
            if (filled)
                g2D.fill(rightFace);
            else
                g2D.draw(rightFace);
        }
        
        if (leftFaceVisible) {
            g2D.setColor(leftFaceColor);
            
            if (filled)
                g2D.fill(leftFace);
            else
                g2D.draw(leftFace);
        }
        
        paintSelectedIndicator(g2D);
        paintBox(g2D);
    }

    public String getName() {
        return name;
    }
    
    private void calculateCoordinates(boolean fixCoordinates) {
        if (fixCoordinates) {
            int xMin = (int) xGrid;
            int xMax = xMin + 1;

            double x1Quart = xMin + 0.25;
            double x2Quart = x1Quart + 0.25;
            double x3Quart = x2Quart + 0.25;

            int yMin = (int) yGrid;
            int yMax = yMin + 1;

            double y1Quart = yMin + 0.25;
            double y2Quart = y1Quart + 0.25;
            double y3Quart = y2Quart + 0.25;

            if (xGrid <= xMin)
                xGrid = xMin;
            else if (xGrid > xMin && xGrid <= x1Quart)
                xGrid = x1Quart;
            else if (xGrid > x1Quart && xGrid <= x2Quart)
                xGrid = x2Quart;
            else if (xGrid > x2Quart && xGrid <= x3Quart)
                xGrid = x3Quart;
            else if (xGrid > x3Quart && xGrid <= xMax)
                xGrid = xMax;

            if (yGrid <= yMin)
                yGrid = yMin;
            else if (yGrid >= yMin && yGrid <= y1Quart)
                yGrid = y1Quart;
            else if (yGrid > y1Quart && yGrid <= y2Quart)
                yGrid = y2Quart;
            else if (yGrid > y2Quart && yGrid <= y3Quart)
                yGrid = y3Quart;
            else if (yGrid > y3Quart && yGrid <= yMax)
                yGrid = yMax;
        }
        
        this.x = (xGrid - 1) * diagonalLength;
        this.y = (yGrid - 1) * diagonalLength;
    }

    public double getXGrid() {
        return xGrid;
    }

    public void setXGrid(double xGrid, boolean fixCoordinates) {
        this.xGrid = xGrid;
        
        calculateCoordinates(fixCoordinates);
    }

    public double getYGrid() {
        return yGrid;
    }

    public void setYGrid(double yGrid, boolean fixCoordinates) {
        this.yGrid = yGrid;
        
        calculateCoordinates(fixCoordinates);
    }
    
    public void setCoordinates(double xGrid, double yGrid, boolean fixCoordinates) {
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        
        calculateCoordinates(fixCoordinates);
    }
    
    public Point2D getCoordinates() {
        return new Point2D.Double(xGrid, yGrid);
    }

    public double getDiagonalLength() {
        return diagonalLength;
    }

    public void setDiagonalLength(double diagonalLength) {
        this.diagonalLength = diagonalLength;
        this.diagonalHalfLength = diagonalLength / 2d;
        this.diagonalHHLength = diagonalHalfLength / 2d;
        this.diagonalHHHLength = diagonalHHLength / 2d;
        
        this.x = (xGrid - 1) * diagonalLength;
        this.y = (yGrid - 1) * diagonalLength;
    }
    
    public boolean isPointInside(Point2D p) {
        boolean onTopFace = topFace.contains(p);
        boolean onRightFace = rightFace.contains(p);
        boolean onLeftFace = leftFace.contains(p);
        
        if (topFaceVisible && onTopFace)
            return true;
        
        if (rightFaceVisible && onRightFace)
            return true;
        
        if (leftFaceVisible && onLeftFace)
            return true;
        
        if (!topFaceVisible && !rightFaceVisible && !leftFaceVisible)
            return onTopFace || onRightFace || onLeftFace;
        
        return false;
    }
    
    public boolean isPointClose(Point2D p, int radius) {
        return pt1.distance(p) <= radius;
    }
    
    public boolean doCoordinatesEqual(Point2D p) {
        return this.xGrid == p.getX() && this.yGrid == p.getY();
    }
    
    public Point2D [] getAdjacentPoints() {
        return new Point2D[] {
            new Point2D.Double(xGrid + 0.5, yGrid - 0.25), // Upper right
            new Point2D.Double(xGrid + 0.5, yGrid + 0.25), // Bottom right
            new Point2D.Double(xGrid - 0.5, yGrid - 0.25), // Upper left
            new Point2D.Double(xGrid - 0.5, yGrid + 0.25)  // Bottom left
        };
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (!selectable)
            return;
        
        this.selected = selected;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isTopFaceVisible() {
        return topFaceVisible;
    }

    public void setTopFaceVisible(boolean topFaceVisible) {
        this.topFaceVisible = topFaceVisible;
    }

    public boolean isRightFaceVisible() {
        return rightFaceVisible;
    }

    public void setRightFaceVisible(boolean rightFaceVisible) {
        this.rightFaceVisible = rightFaceVisible;
    }

    public boolean isLeftFaceVisible() {
        return leftFaceVisible;
    }

    public void setLeftFaceVisible(boolean leftFaceVisible) {
        this.leftFaceVisible = leftFaceVisible;
    }

    public Point2D getPt0() {
        return pt0;
    }

    public Point2D getPt1() {
        return pt1;
    }

    public Point2D getPt2() {
        return pt2;
    }
    
    public void copyProperties(Block old) {
        setCoordinates(old.xGrid, old.yGrid, false);
        
        this.topFaceVisible = old.topFaceVisible;
        this.rightFaceVisible = old.rightFaceVisible;
        this.leftFaceVisible = old.leftFaceVisible;
    }
    
    public HashMap<String, String> collectProperties() {
        HashMap<String, String> properties = new HashMap<>();
        
        properties.put(BlockProperties.NAME.name(), name);
        properties.put(BlockProperties.XGRID.name(), "" + xGrid);
        properties.put(BlockProperties.YGRID.name(), "" + yGrid);
        properties.put(BlockProperties.TOP_FACE_VISIBLE.name(), "" + topFaceVisible);
        properties.put(BlockProperties.RIGHT_FACE_VISIBLE.name(), "" + rightFaceVisible);
        properties.put(BlockProperties.LEFT_FACE_VISIBLE.name(), "" + leftFaceVisible);
        
        return properties;
    }
}
