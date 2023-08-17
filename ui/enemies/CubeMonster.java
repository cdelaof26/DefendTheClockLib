package ui.enemies;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import ui.blocks.Block;
import ui.enums.CubeMonsterFacing;

/**
 *
 * @author cristopher
 */
public class CubeMonster extends Block {
    private double cubeMonsterConstant0;
    private double cubeMonsterConstant1;
    
    protected CubeMonsterFacing facingDirection = CubeMonsterFacing.NONE;

    public CubeMonster(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        int colorModifier0 = 100 + (int) (Math.random() * 155);
        int colorModifier1 = 100 + (int) (Math.random() * 155);
        int colorModifier2 = 100 + (int) (Math.random() * 155);
        
        name = "CubeMonster";
        topFaceColor = new Color(colorModifier0, colorModifier1, colorModifier2);
        leftFaceColor = topFaceColor.darker();
    }

    @Override
    public void createFaces() {
        cubeMonsterConstant0 = 0.075 * diagonalLength;
        cubeMonsterConstant1 = 0.0666666667 * diagonalLength;
        
        pt0 = new Point2D.Double(x + diagonalLength - diagonalHHHLength, y + diagonalHHLength + cubeMonsterConstant0);
        pt1 = new Point2D.Double(x + diagonalHalfLength, y + diagonalHalfLength - cubeMonsterConstant1 + cubeMonsterConstant0);
        pt2 = new Point2D.Double(x + diagonalHHHLength, y + diagonalHHLength + cubeMonsterConstant0);
        
        topFace = new Path2D.Double();

        topFace.moveTo(x + diagonalHalfLength, y + cubeMonsterConstant1 + cubeMonsterConstant0);
        topFace.lineTo(pt0.getX(), pt0.getY());
        topFace.lineTo(pt1.getX(), pt1.getY());
        topFace.lineTo(pt2.getX(), pt2.getY());
        topFace.closePath();
        
        rightFace = new Path2D.Double();

        rightFace.moveTo(pt1.getX(), pt1.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY());
        rightFace.lineTo(pt0.getX(), pt0.getY() + diagonalHalfLength - diagonalHHHLength);
        rightFace.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength - diagonalHHHLength);
        rightFace.closePath();
        
        leftFace = new Path2D.Double();

        leftFace.moveTo(pt1.getX(), pt1.getY());
        leftFace.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength - diagonalHHHLength);
        leftFace.lineTo(pt2.getX(), pt2.getY() + diagonalHalfLength - diagonalHHHLength);
        leftFace.lineTo(pt2.getX(), pt2.getY());
        leftFace.closePath();
    }

    public void setFacingDirection(CubeMonsterFacing facingDirection) {
        this.facingDirection = facingDirection;
    }
}
