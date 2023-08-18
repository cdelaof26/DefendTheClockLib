package ui.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import ui.blocks.Block;
import ui.enums.CubeMonsterFacing;

/**
 *
 * @author cristopher
 */
public class CubeMonster extends Block {
    private final Color SHADOW_COLOR = new Color(0, 0, 0, 100);
    protected Path2D monsterShadow;
    
    private Point2D topFaceTop;
    private double height;
    
    private double cubeMonsterConstant0;
    private double cubeMonsterConstant1;
    
    protected Point2D nextTilePosition;
    protected double xDifference;
    protected double yDifference;
    protected double xStep;
    protected double yStep;
    protected int lastCoordinate = 0;
    
    
    protected int steps = 0;
    protected int numberOfSteps;
    protected HealthBar healthBar = new HealthBar(1);
    
    protected CubeMonsterFacing facingDirection = CubeMonsterFacing.NONE;

    public CubeMonster(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        int colorModifier0 = 100 + (int) (Math.random() * 155);
        int colorModifier1 = 100 + (int) (Math.random() * 155);
        int colorModifier2 = 100 + (int) (Math.random() * 155);
        
        name = "CubeMonster";
        topFaceColor = new Color(colorModifier0, colorModifier1, colorModifier2);
        rightFaceColor = topFaceColor;
        leftFaceColor = topFaceColor.darker();
        
        healthBar.makeSmall();
    }

    @Override
    public void createFaces() {
        // if diagonalLength = 120
        cubeMonsterConstant0 = 0.075 * diagonalLength;        // 9
        cubeMonsterConstant1 = 0.0666666667 * diagonalLength; // 8
        
        topFaceTop = new Point2D.Double(x + diagonalHalfLength, y + cubeMonsterConstant1 + cubeMonsterConstant0);
        pt0 = new Point2D.Double(x + diagonalLength - diagonalHHHLength, y + diagonalHHLength + cubeMonsterConstant0);
        pt1 = new Point2D.Double(x + diagonalHalfLength, y + diagonalHalfLength - cubeMonsterConstant1 + cubeMonsterConstant0);
        pt2 = new Point2D.Double(x + diagonalHHHLength, y + diagonalHHLength + cubeMonsterConstant0);
        
        height = diagonalHalfLength - diagonalHHHLength;
        
        topFace = new Path2D.Double();

        topFace.moveTo(topFaceTop.getX(), topFaceTop.getY());
        topFace.lineTo(pt0.getX(), pt0.getY());
        topFace.lineTo(pt1.getX(), pt1.getY());
        topFace.lineTo(pt2.getX(), pt2.getY());
        topFace.closePath();
        
        rightFace = new Path2D.Double();

        rightFace.moveTo(pt1.getX(), pt1.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY() + height);
        rightFace.lineTo(pt1.getX(), pt1.getY() + height);
        rightFace.closePath();
        
        leftFace = new Path2D.Double();

        leftFace.moveTo(pt1.getX(), pt1.getY());
        leftFace.lineTo(pt1.getX(), pt1.getY() + height);
        leftFace.lineTo(pt2.getX(), pt2.getY() + height);
        leftFace.lineTo(pt2.getX(), pt2.getY());
        leftFace.closePath();
        
        
        // Bigger shadow
//        Point2D spt0 = new Point2D.Double((x + diagonalLength) * UIProperties.getUiScale(), (y + diagonalHHLength) * UIProperties.getUiScale());
//        Point2D spt1 = new Point2D.Double((x + diagonalHalfLength) * UIProperties.getUiScale(), (y + diagonalHalfLength) * UIProperties.getUiScale());
//        Point2D spt2 = new Point2D.Double(x * UIProperties.getUiScale(), (y + diagonalHHLength) * UIProperties.getUiScale());
//        
//        
//        monsterShadow = new Path2D.Double();
//
//        monsterShadow.moveTo(x + diagonalHalfLength, y + height + cubeMonsterConstant1);
//        monsterShadow.lineTo(spt0.getX(), spt0.getY() + height + cubeMonsterConstant1);
//        monsterShadow.lineTo(spt1.getX(), spt1.getY() + height + cubeMonsterConstant1);
//        monsterShadow.lineTo(spt2.getX(), spt2.getY() + height + cubeMonsterConstant1);
//        monsterShadow.closePath();
        
        
        monsterShadow = new Path2D.Double();
        
        monsterShadow.moveTo(topFaceTop.getX(), topFaceTop.getY() + height + cubeMonsterConstant1);
        monsterShadow.lineTo(pt0.getX(), pt0.getY() + height + cubeMonsterConstant1);
        monsterShadow.lineTo(pt1.getX(), pt1.getY() + height + cubeMonsterConstant1);
        monsterShadow.lineTo(pt2.getX(), pt2.getY() + height + cubeMonsterConstant1);
        monsterShadow.closePath();
    }

    @Override
    public void paintBlock(Graphics2D g2D) {
        createFaces();
        
        g2D.setColor(SHADOW_COLOR);
        g2D.fill(monsterShadow);
        
        g2D.setColor(topFaceColor);
        g2D.fill(topFace);
        
        g2D.setColor(rightFaceColor);
        g2D.fill(rightFace);
        
        g2D.setColor(leftFaceColor);
        g2D.fill(leftFace);
        
        healthBar.paintBar(g2D, topFaceTop.getX(), topFaceTop.getY());
    }
    
    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }
    
    public int getLastCoordinate() {
        return lastCoordinate;
    }
    
    public boolean setNextTilePosition(Point2D nextTilePosition) {
        this.steps = 0;
//        this.steppingAdded = false;
        this.nextTilePosition = nextTilePosition;
        
        return this.nextTilePosition == null;
    }
    
    public void defineFacingDirection() {
        if (nextTilePosition == null)
            return;
        
        xDifference = nextTilePosition.getX() - getXGrid();
        yDifference = nextTilePosition.getY() - getYGrid();
        
        xStep = xDifference / numberOfSteps;
        yStep = yDifference / numberOfSteps;
        
        if (xDifference < 0 && yDifference > 0) {
            facingDirection = CubeMonsterFacing.LEFT;
            return;
        }
        
        if (xDifference > 0 && yDifference > 0) {
            facingDirection = CubeMonsterFacing.RIGHT;
            return;
        }
        
        facingDirection = CubeMonsterFacing.NONE;
    }

    public boolean movementEnded() {
        return steps >= numberOfSteps;
    }
    
    public void increaseLastCoordinate() {
        lastCoordinate++;
    }
    
    public void moveToNextLocation() {
        if (steps >= numberOfSteps)
            return;
        
        moveBlockCoordinates(xStep, yStep, false);
        steps++;
    }
    
    public void setMaximumHealth(int value) {
        healthBar.setMaximumValue(value);
        healthBar.setValue(value);
    }

    public Rectangle getPaintArea() {
        double totalHeight = 3 + healthBar.getHeight() + diagonalLength;
        return new Rectangle((int) healthBar.getX(), (int) healthBar.getY(), healthBar.getWidth() + 1, (int) totalHeight);
    }
}
