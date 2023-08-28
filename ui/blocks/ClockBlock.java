package ui.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import ui.UIProperties;
import ui.enemies.HealthBar;

/**
 *
 * @author cristopher
 */
public class ClockBlock extends Block {
    private static final Color FOOT_COLOR = new Color(130, 130, 130);
    private static final Color LEFT_FOOT_COLOR = new Color(80, 80, 80);
    
    private static final Color BODY_COLOR = new Color(90, 90, 90);
    private static final Color LEFT_BODY_COLOR = new Color(40, 40, 40);

    private double clockConstant0;
    private double clockConstant1;
    private double clockConstant3;
    private double clockConstant2;
    
    private Point2D footLRP;
    private Point2D footLMP;
    private Point2D footLLP;
    
    private Point2D headMP;
    private Point2D headLidP4;
    
    protected boolean healthBarVisible = false;
    protected HealthBar healthBar = new HealthBar(30);
    
    
    public ClockBlock(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
        
        name = "Clock";
    }

    @Override
    public void createFaces() {
        clockConstant0 = 0.0416666667 * diagonalLength;
        clockConstant1 = 0.025 * diagonalLength * 2;
        clockConstant3 = 0.025 * diagonalLength * 3;
        clockConstant2 = diagonalHHHLength * 3.2;
        
        pt1 = new Point2D.Double((x + diagonalHalfLength), (y + diagonalHalfLength));
    }
    
    @Override
    public void paintSelectedIndicator(Graphics2D g2D) {
        if (!selected || !selectable)
            return;
        
        Path2D selectorIndicator = new Path2D.Double();
        
        selectorIndicator.moveTo(footLLP.getX(), footLLP.getY());
        selectorIndicator.lineTo(footLMP.getX(), footLMP.getY());
        selectorIndicator.lineTo(footLRP.getX(), footLRP.getY());
        
        selectorIndicator.lineTo(footLRP.getX() + diagonalHHLength, footLRP.getY() + diagonalHHHLength);
        selectorIndicator.lineTo(footLMP.getX(), footLMP.getY() + diagonalHHLength);
        selectorIndicator.lineTo(footLLP.getX() - diagonalHHLength, footLLP.getY() + diagonalHHHLength);
        
        selectorIndicator.closePath();
        
        g2D.setColor(UIProperties.APP_BG_COLOR);
        g2D.fill(selectorIndicator);
    }
    
    @Override
    public void paintBox(Graphics2D g2D) { }
    
    @Override
    public void paintBlock(Graphics2D g2D) {
        createFaces();
        
        footLRP = new Point2D.Double(x + diagonalLength, y + diagonalLength - diagonalHHLength);
        footLMP = new Point2D.Double(x + diagonalHalfLength, y + diagonalLength);
        footLLP = new Point2D.Double(x, y + diagonalLength - diagonalHHLength);

        Point2D footLP = new Point2D.Double(x + diagonalHHHLength, y + diagonalLength - diagonalHHLength - diagonalHHHLength);
        Point2D footMP = new Point2D.Double(x + diagonalHalfLength, y + diagonalLength - diagonalHHHLength - clockConstant1);
        Point2D footRP = new Point2D.Double(x + diagonalLength - diagonalHHHLength, y + diagonalLength - diagonalHHLength - diagonalHHHLength);

        Path2D rFoot = new Path2D.Double();
        rFoot.moveTo(footLRP.getX(), footLRP.getY());
        rFoot.lineTo(footLMP.getX(), footLMP.getY());
        rFoot.lineTo(footMP.getX(), footMP.getY());
        rFoot.lineTo(footRP.getX(), footRP.getY());
        rFoot.closePath();

        g2D.setColor(FOOT_COLOR);
        g2D.fill(rFoot);

        Path2D lFoot = new Path2D.Double();
        lFoot.moveTo(footLLP.getX(), footLLP.getY());
        lFoot.lineTo(footLMP.getX(), footLMP.getY());
        lFoot.lineTo(footMP.getX(), footMP.getY());
        lFoot.lineTo(footLP.getX(), footLP.getY());
        lFoot.closePath();

        g2D.setColor(LEFT_FOOT_COLOR);
        g2D.fill(lFoot);



        Point2D bodyLP = new Point2D.Double(footLP.getX(), footLP.getY() - diagonalLength * 0.9);
        Point2D bodyMP = new Point2D.Double(footMP.getX(), footMP.getY() - diagonalLength * 0.9);
        Point2D bodyRP = new Point2D.Double(footRP.getX(), footRP.getY() - diagonalLength * 0.9);

        Path2D rbody = new Path2D.Double();
        rbody.moveTo(footRP.getX(), footRP.getY());
        rbody.lineTo(footMP.getX(), footMP.getY());
        rbody.lineTo(bodyMP.getX(), bodyMP.getY());
        rbody.lineTo(bodyRP.getX(), bodyRP.getY());
        rbody.closePath();

        g2D.setColor(BODY_COLOR);
        g2D.fill(rbody);
        
        Path2D lBody = new Path2D.Double();
        lBody.moveTo(footLP.getX(), footLP.getY());
        lBody.lineTo(footMP.getX(), footMP.getY());
        lBody.lineTo(bodyMP.getX(), bodyMP.getY());
        lBody.lineTo(bodyLP.getX(), bodyLP.getY());
        lBody.closePath();

        g2D.setColor(LEFT_BODY_COLOR);
        g2D.fill(lBody);



        Point2D headLP = new Point2D.Double(bodyLP.getX() - diagonalHHHLength + clockConstant0, bodyLP.getY() - clockConstant2);
        headMP = new Point2D.Double(bodyMP.getX(), bodyMP.getY() - clockConstant2);
        Point2D headRP = new Point2D.Double(bodyRP.getX() + diagonalHHHLength - clockConstant0, bodyRP.getY() - clockConstant2);

        Path2D rHead = new Path2D.Double();
        rHead.moveTo(bodyRP.getX() + diagonalHHHLength - clockConstant0, bodyRP.getY() + diagonalHHHLength);
        rHead.lineTo(bodyMP.getX(), bodyMP.getY() + diagonalHHHLength);
        rHead.lineTo(headMP.getX(), headMP.getY());
        rHead.lineTo(headRP.getX(), headRP.getY());
        rHead.closePath();

        g2D.setColor(FOOT_COLOR);
        g2D.fill(rHead);
        
        Path2D lHead = new Path2D.Double();
        lHead.moveTo(bodyLP.getX() - diagonalHHHLength + clockConstant0, bodyLP.getY() + diagonalHHHLength);
        lHead.lineTo(bodyMP.getX(), bodyMP.getY() + diagonalHHHLength);
        lHead.lineTo(headMP.getX(), headMP.getY());
        lHead.lineTo(headLP.getX(), headLP.getY());
        lHead.closePath();

        g2D.setColor(LEFT_FOOT_COLOR);
        g2D.fill(lHead);


//        g2D.setColor(Color.BLACK);
//        g2D.draw(new Line2D.Double(footLMP.getX(), footLMP.getY(), headMP.getX(), headMP.getY()));


        Point2D headLidMP = new Point2D.Double(headMP.getX(), headMP.getY() + clockConstant0);
        Point2D headLidTMP = new Point2D.Double(headMP.getX() + (diagonalHHLength), headMP.getY() - diagonalHHLength - clockConstant3);
        headLidP4 = new Point2D.Double(headLP.getX() + diagonalHHLength - clockConstant0, headLP.getY() - diagonalHHLength - clockConstant3);


        Path2D rHeadLid = new Path2D.Double();
        rHeadLid.moveTo(headRP.getX() + clockConstant0, headRP.getY() + clockConstant0);
        rHeadLid.lineTo(headLidMP.getX(), headLidMP.getY());
        rHeadLid.lineTo(headLidTMP.getX(), headLidTMP.getY());
        rHeadLid.closePath();

        g2D.setColor(BODY_COLOR);
        g2D.fill(rHeadLid);
        
        Path2D headLid = new Path2D.Double();
        headLid.moveTo(headLP.getX() - clockConstant0, headLP.getY() + clockConstant0);
        headLid.lineTo(headLidMP.getX(), headLidMP.getY());
        headLid.lineTo(headLidTMP.getX(), headLidTMP.getY());
        headLid.lineTo(headLidP4.getX(), headLidP4.getY());
        headLid.closePath();

        g2D.setColor(LEFT_BODY_COLOR);
        g2D.fill(headLid);

//        g2D.setColor(Color.BLACK);
//        g2D.draw(new Line2D.Double(headLidMP.getX(), headLidMP.getY(), headLidTMP.getX(), headLidTMP.getY()));
        
        paintSelectedIndicator(g2D);
    }
    
    public void paintHealthBar(Graphics2D g2D) {
        healthBar.paintBar(g2D, headMP.getX(), headLidP4.getY());
    }
    
    @Override
    public boolean isPointInside(Point2D p) {
        return (p.getX() >= x && p.getX() <= x + diagonalLength) && (p.getY() >= y - diagonalLength && p.getY() <= y + diagonalLength);
    }
    
    @Override
    public boolean isPointClose(Point2D p, int radius) {
        return pt1.distance(p) <= radius;
    }

    public void setHealthBarVisible(boolean healthBarVisible) {
        this.healthBarVisible = healthBarVisible;
    }
    
    public void setHealth(int value) {
        healthBar.setValue(value);
    }
    
    public Rectangle getHealthPaintArea() {
        return healthBar.getPaintArea();
    }
}
