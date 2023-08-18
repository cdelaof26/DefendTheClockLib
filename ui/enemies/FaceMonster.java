package ui.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import ui.enums.CubeMonsterFacing;

/**
 *
 * @author cristopher
 */
public class FaceMonster extends CubeMonster {
    private double faceMonsterConstant0;
    private double faceMonsterConstant1;
    private double faceMonsterConstant2;
    private double faceMonsterConstant3;
    private double faceMonsterConstant4;
    private double faceMonsterConstant5;
    private double faceMonsterConstant6;
    private double faceMonsterConstant7;
    private double faceMonsterConstant8;
    private double faceMonsterConstant9;
    private double faceMonsterConstant10;
    private double faceMonsterConstant11;
    private double faceMonsterConstant12;
    private double faceMonsterConstant13;
    private double faceMonsterConstant14;
    private double faceMonsterConstant15;
    
    protected Path2D rightFaceMouth;
    protected Path2D rightFaceLeftEye;
    protected Path2D rightFaceRightEye;
    
    protected Path2D leftFaceMouth;
    protected Path2D leftFaceLeftEye;
    protected Path2D leftFaceRightEye;
    
    
    public FaceMonster(double xGrid, double yGrid, double diagonalLength, int health) {
        super(xGrid, yGrid, diagonalLength);
        
        name = "FaceMonster";
        
        setMaximumHealth(health);
    }

    @Override
    public void createFaces() {
        super.createFaces();
        
        if (facingDirection == CubeMonsterFacing.RIGHT) {
            rightFaceRightEye = new Path2D.Double();

            rightFaceRightEye.moveTo(pt0.getX() - faceMonsterConstant0, pt0.getY() + faceMonsterConstant1);
            rightFaceRightEye.lineTo(pt0.getX() - faceMonsterConstant2, pt0.getY() + faceMonsterConstant2);
            rightFaceRightEye.lineTo(pt0.getX() - faceMonsterConstant2, pt0.getY() + faceMonsterConstant3);
            rightFaceRightEye.lineTo(pt0.getX() - faceMonsterConstant0, pt0.getY() + faceMonsterConstant4);
            rightFaceRightEye.closePath();

            rightFaceLeftEye = new Path2D.Double();

            rightFaceLeftEye.moveTo(pt0.getX() - faceMonsterConstant8, pt0.getY() + faceMonsterConstant5);
            rightFaceLeftEye.lineTo(pt0.getX() - faceMonsterConstant3, pt0.getY() + faceMonsterConstant6);
            rightFaceLeftEye.lineTo(pt0.getX() - faceMonsterConstant3, pt0.getY() + faceMonsterConstant11);
            rightFaceLeftEye.lineTo(pt0.getX() - faceMonsterConstant8, pt0.getY() + faceMonsterConstant9);
            rightFaceLeftEye.closePath();

            rightFaceMouth = new Path2D.Double();

            rightFaceMouth.moveTo(pt0.getX() - faceMonsterConstant12, pt0.getY() + faceMonsterConstant7);
            rightFaceMouth.lineTo(pt0.getX() - faceMonsterConstant13, pt0.getY() + faceMonsterConstant10);
            rightFaceMouth.lineTo(pt0.getX() - faceMonsterConstant13, pt0.getY() + faceMonsterConstant14);
            rightFaceMouth.lineTo(pt0.getX() - faceMonsterConstant12, pt0.getY() + faceMonsterConstant15);
            rightFaceMouth.closePath();
            return;
        }
        
        if (facingDirection != CubeMonsterFacing.LEFT)
            return;

        leftFaceLeftEye = new Path2D.Double();

        leftFaceLeftEye.moveTo(pt2.getX() + faceMonsterConstant0, pt2.getY() + faceMonsterConstant1);
        leftFaceLeftEye.lineTo(pt2.getX() + faceMonsterConstant2, pt2.getY() + faceMonsterConstant2);
        leftFaceLeftEye.lineTo(pt2.getX() + faceMonsterConstant2, pt2.getY() + faceMonsterConstant3);
        leftFaceLeftEye.lineTo(pt2.getX() + faceMonsterConstant0, pt2.getY() + faceMonsterConstant4);
        leftFaceLeftEye.closePath();

        leftFaceRightEye = new Path2D.Double();

        leftFaceRightEye.moveTo(pt2.getX() + faceMonsterConstant8, pt2.getY() + faceMonsterConstant5);
        leftFaceRightEye.lineTo(pt2.getX() + faceMonsterConstant3, pt2.getY() + faceMonsterConstant6);
        leftFaceRightEye.lineTo(pt2.getX() + faceMonsterConstant3, pt2.getY() + faceMonsterConstant11);
        leftFaceRightEye.lineTo(pt2.getX() + faceMonsterConstant8, pt2.getY() + faceMonsterConstant9);
        leftFaceRightEye.closePath();

        leftFaceMouth = new Path2D.Double();

        leftFaceMouth.moveTo(pt2.getX() + faceMonsterConstant12, pt2.getY() + faceMonsterConstant7);
        leftFaceMouth.lineTo(pt2.getX() + faceMonsterConstant13, pt2.getY() + faceMonsterConstant10);
        leftFaceMouth.lineTo(pt2.getX() + faceMonsterConstant13, pt2.getY() + faceMonsterConstant14);
        leftFaceMouth.lineTo(pt2.getX() + faceMonsterConstant12, pt2.getY() + faceMonsterConstant15);
        leftFaceMouth.closePath();
    }

    @Override
    public void paintBlock(Graphics2D g2D) {
        super.paintBlock(g2D);
        
        createFaces();

        g2D.setColor(Color.BLACK);
        if (facingDirection == CubeMonsterFacing.RIGHT) {
            g2D.fill(rightFaceRightEye);
            g2D.fill(rightFaceLeftEye);
            g2D.fill(rightFaceMouth);
        } else if (facingDirection == CubeMonsterFacing.LEFT) {
            g2D.fill(leftFaceLeftEye);
            g2D.fill(leftFaceRightEye);
            g2D.fill(leftFaceMouth);
        }
    }

    @Override
    public void setDiagonalLength(double diagonalLength) {
        super.setDiagonalLength(diagonalLength);
        
        // if diagonalLength = 120
        faceMonsterConstant0 = diagonalLength * 0.0916666667; // 11
        faceMonsterConstant1 = diagonalLength * 0.125;        // 15
        faceMonsterConstant2 = diagonalLength * 0.15;         // 18
        faceMonsterConstant3 = diagonalLength * 0.2833333333; // 34
        faceMonsterConstant4 = diagonalLength * 0.2583333333; // 31
        faceMonsterConstant5 = faceMonsterConstant3 - faceMonsterConstant0; // 23
        faceMonsterConstant6 = faceMonsterConstant0 + faceMonsterConstant1; // 26
        faceMonsterConstant7 = faceMonsterConstant1 + faceMonsterConstant2; // 33
        faceMonsterConstant8 = diagonalLength * 0.225;                      // 27
        faceMonsterConstant9 = faceMonsterConstant0  + faceMonsterConstant8;  // 38
        faceMonsterConstant10 = faceMonsterConstant5  + faceMonsterConstant6; // 49
        faceMonsterConstant11 = diagonalLength * 0.3416666667;                // 41
        faceMonsterConstant12 = diagonalLength * 0.05;                        // 6
        faceMonsterConstant13 = diagonalLength * 0.325;                       // 39
        faceMonsterConstant14 = diagonalLength * 0.4666666667;                // 56
        faceMonsterConstant15 = diagonalLength * 0.3333333333;                // 40
    }
}
