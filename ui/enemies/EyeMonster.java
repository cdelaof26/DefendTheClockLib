package ui.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import ui.enums.CubeMonsterFacing;

/**
 *
 * @author cristopher
 */
public class EyeMonster extends CubeMonster {
    private double eyeMonsterConstant0;
    private double eyeMonsterConstant1;
    private double eyeMonsterConstant2;
    private double eyeMonsterConstant3;
    private double eyeMonsterConstant4;
    private double eyeMonsterConstant5;
    private double eyeMonsterConstant6;
    private double eyeMonsterConstant7;
    private double eyeMonsterConstant8;
    
    protected Path2D leftEyeLash;
    protected Path2D leftEye;
    protected Path2D leftIris;
    
    protected Path2D rightEyeLash;
    protected Path2D rightEye;
    protected Path2D rightIris;
    
    private int irisModifier;
    
    
    public EyeMonster(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        int colorModifier0 = (int) (Math.random() * 255);
        int colorModifier1 = (int) (Math.random() * 255);
        int colorModifier2 = (int) (Math.random() * 255);
        
        name = "EyeMonster";
        topFaceColor = new Color(colorModifier0, colorModifier1, colorModifier2);
        leftFaceColor = topFaceColor.darker();
    }

    @Override
    public void createFaces() {
        super.createFaces();
        
        if (facingDirection == CubeMonsterFacing.RIGHT) {
            rightEyeLash = new Path2D.Double();
            
            rightEyeLash.moveTo(pt0.getX() - eyeMonsterConstant0, pt0.getY() + eyeMonsterConstant1);
            rightEyeLash.lineTo(pt1.getX() + eyeMonsterConstant0, pt1.getY() + eyeMonsterConstant0);
            rightEyeLash.lineTo(pt1.getX() + eyeMonsterConstant0, pt1.getY() + eyeMonsterConstant0 + eyeMonsterConstant5);
            rightEyeLash.lineTo(pt0.getX() - eyeMonsterConstant0, pt0.getY() + eyeMonsterConstant1 + eyeMonsterConstant5);
            rightEyeLash.closePath();

            rightEye = new Path2D.Double();

            rightEye.moveTo(pt0.getX() - eyeMonsterConstant2, pt0.getY() + eyeMonsterConstant3);
            rightEye.lineTo(pt1.getX() + eyeMonsterConstant2, pt1.getY() + eyeMonsterConstant2);
            rightEye.lineTo(pt1.getX() + eyeMonsterConstant2, pt1.getY() + eyeMonsterConstant2 + eyeMonsterConstant7);
            rightEye.lineTo(pt0.getX() - eyeMonsterConstant2, pt0.getY() + eyeMonsterConstant3 + eyeMonsterConstant7);
            rightEye.closePath();

            rightIris = new Path2D.Double();

            rightIris.moveTo(pt0.getX() - eyeMonsterConstant4 + irisModifier, pt0.getY() + eyeMonsterConstant6 + irisModifier);
            rightIris.lineTo(pt1.getX() + eyeMonsterConstant4 + irisModifier, pt1.getY() + eyeMonsterConstant8 + irisModifier);
            rightIris.lineTo(pt1.getX() + eyeMonsterConstant4 + irisModifier, pt1.getY() + eyeMonsterConstant8 + eyeMonsterConstant1 + irisModifier);
            rightIris.lineTo(pt0.getX() - eyeMonsterConstant4 + irisModifier, pt0.getY() + eyeMonsterConstant6 + eyeMonsterConstant1 + irisModifier);
            rightIris.closePath();
            return;
        }
        
        if (facingDirection != CubeMonsterFacing.LEFT)
            return;
        
        leftEyeLash = new Path2D.Double();
        
        leftEyeLash.moveTo(pt2.getX() + eyeMonsterConstant0, pt2.getY() + eyeMonsterConstant1);
        leftEyeLash.lineTo(pt1.getX() - eyeMonsterConstant0, pt1.getY() + eyeMonsterConstant0);
        leftEyeLash.lineTo(pt1.getX() - eyeMonsterConstant0, pt1.getY() + eyeMonsterConstant0 + eyeMonsterConstant5);
        leftEyeLash.lineTo(pt2.getX() + eyeMonsterConstant0, pt2.getY() + eyeMonsterConstant1 + eyeMonsterConstant5);
        leftEyeLash.closePath();
        
        leftEye = new Path2D.Double();

        leftEye.moveTo(pt2.getX() + eyeMonsterConstant2, pt2.getY() + eyeMonsterConstant3);
        leftEye.lineTo(pt1.getX() - eyeMonsterConstant2, pt1.getY() + eyeMonsterConstant2);
        leftEye.lineTo(pt1.getX() - eyeMonsterConstant2, pt1.getY() + eyeMonsterConstant2 + eyeMonsterConstant7);
        leftEye.lineTo(pt2.getX() + eyeMonsterConstant2, pt2.getY() + eyeMonsterConstant3 + eyeMonsterConstant7);
        leftEye.closePath();

        leftIris = new Path2D.Double();
        
        leftIris.moveTo(pt2.getX() + eyeMonsterConstant4 + irisModifier, pt2.getY() + eyeMonsterConstant6 + irisModifier);
        leftIris.lineTo(pt1.getX() - eyeMonsterConstant4 + irisModifier, pt1.getY() + eyeMonsterConstant8 + irisModifier);
        leftIris.lineTo(pt1.getX() - eyeMonsterConstant4 + irisModifier, pt1.getY() + eyeMonsterConstant8 + eyeMonsterConstant1 + irisModifier);
        leftIris.lineTo(pt2.getX() + eyeMonsterConstant4 + irisModifier, pt2.getY() + eyeMonsterConstant6 + eyeMonsterConstant1 + irisModifier);
        leftIris.closePath();
    }
    
    @Override
    public void paintBlock(Graphics2D g2D) {
        createFaces();
        
        g2D.setColor(topFaceColor);
        g2D.fill(topFace);
        g2D.fill(rightFace);
        
        g2D.setColor(leftFaceColor);
        g2D.fill(leftFace);
        
        if (facingDirection == CubeMonsterFacing.RIGHT) {
            g2D.setColor(Color.BLACK);
            g2D.fill(rightEyeLash);

            g2D.setColor(Color.WHITE);
            g2D.fill(rightEye);

            g2D.setColor(Color.BLACK);
            g2D.fill(rightIris);
        } else if (facingDirection == CubeMonsterFacing.LEFT) {
            g2D.setColor(Color.BLACK);
            g2D.fill(leftEyeLash);

            g2D.setColor(Color.WHITE);
            g2D.fill(leftEye);

            g2D.setColor(Color.BLACK);
            g2D.fill(leftIris);
        }
    }

    @Override
    public void setDiagonalLength(double diagonalLength) {
        super.setDiagonalLength(diagonalLength);
        
        // if diagonalLength = 120
        eyeMonsterConstant0 = diagonalLength * 0.0416666667; // 5
        eyeMonsterConstant1 = eyeMonsterConstant0 * 2;       // 10
        eyeMonsterConstant2 = diagonalLength * 0.0666666667; // 8
        eyeMonsterConstant3 = eyeMonsterConstant2 * 2;       // 16
        eyeMonsterConstant4 = eyeMonsterConstant1 + eyeMonsterConstant2; // 18
        eyeMonsterConstant5 = diagonalLength * 0.2583333333; // 31
        eyeMonsterConstant6 = eyeMonsterConstant1 * 3; // 30
        eyeMonsterConstant7 = diagonalLength * 0.1916666667; // 23
        eyeMonsterConstant8 = diagonalLength * 0.1083333333; // 13
        
        irisModifier = (int) (Math.random() * eyeMonsterConstant0);
        if ((int) (Math.random() * 2) % 2 == 0)
            irisModifier = (-irisModifier);
    }
}
