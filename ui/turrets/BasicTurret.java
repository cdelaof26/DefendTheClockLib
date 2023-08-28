package ui.turrets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import javax.swing.Timer;
import ui.blocks.Block;
import ui.enemies.CubeMonster;

/**
 *
 * @author cristopher
 */
public class BasicTurret extends Block {
    private Point2D turretBaseRP, turretBaseMP, turretBaseLP;
    private Path2D turretRightBase, turretLeftBase, turretBaseLid;
    
    private Point2D turretPoleRP, turretPoleBP, turretPoleLP, turretPoleTP;
    private Point2D turretPoleLidRP, turretPoleLidBP, turretPoleLidLP, turretPoleLidTP;
    
    private Path2D turretPoleRFaceBack, turretPoleRFaceFront, turretPoleLFaceBack, turretPoleLFaceFront;
    
    private Point2D turretBodyRP, turretBodyBP, turretBodyLP, turretBodyTP;
    private Point2D turretBodyLidRP, turretBodyLidBP, turretBodyLidLP, turretBodyLidTP;
    
    private Point2D turretBodyMuzzleP0, turretBodyMuzzleP1;
    
    private Path2D turretBodyRFaceBack, turretBodyRFaceFront, turretBodyLFaceBack, turretBodyLFaceFront;
    private Path2D turretBodyMuzzleRFaceFront;
    private Path2D turretBodyLid;
    
    
    private double angle = 0;

    private Point2D turretBaseLidCenter;

    private final double [] poleXCoordinates = new double[4];
    private final double [] poleYCoordinates = new double[4];
    private final double [] polePointsAngles = {0, 90, 180, 270};
    private double a1 = diagonalHHLength;
    private double b1 = diagonalHHHLength;



    private Point2D turretPoleLidCenter;
    private final double [] turretBodyXCoordinates = new double[4];
    private final double [] turretBodyYCoordinates = new double[4];
    private final double [] bodyPointsAngles = {10, 80, 190, 260};
    private double a2 = diagonalHalfLength;
    private double b2 = diagonalHHLength;


    private Point2D turretBodyCenter;
    private final double [] turretMuzzleXCoordinates = new double[2];
    private final double [] turretMuzzleYCoordinates = new double[2];
    private final double [] muzzlePointsAngles = {25, 65};
    private double a3 = 0.8 * diagonalHalfLength;
    private double b3 = 0.8 * diagonalHHLength;
    
    
    private final Color turretBaseLeftColor = new Color(20, 20, 20);
    private final Color turretBaseColor = new Color(120, 120, 120);
    private final Color turretPoleLeftColor = new Color(0, 0, 0);
    private final Color turretPoleColor = new Color(100, 100, 100);
    private final Color turretBodyLeftColor = new Color(201, 200, 197);
    private final Color turretBodyColor = new Color(241, 240, 237);
    
    
    private int shootFrequency = 1000;
    private int shootPower = 80;
    
    private CubeMonster target;
    private Timer shootMechanism = new Timer(shootFrequency, (Action) -> {
        damageTarget();
    });
    
    
    public BasicTurret(double xGrid, double yGrid, double diagonalLength) {
        super(xGrid, yGrid, diagonalLength);
    }
    
    @Override
    public void createFaces() {
        pt0 = new Point2D.Double(x + diagonalLength, y + diagonalHHLength);
        pt1 = new Point2D.Double(x + diagonalHalfLength, y + diagonalHalfLength);
        pt2 = new Point2D.Double(x, y + diagonalHHLength);
        
        turretBaseLidCenter = new Point2D.Double(pt1.getX(), pt1.getY() + diagonalHHHLength);
        turretPoleLidCenter = new Point2D.Double(pt1.getX(), pt1.getY() - diagonalHHHLength);
        turretBodyCenter = new Point2D.Double(turretPoleLidCenter.getX(), turretPoleLidCenter.getY() - diagonalHHHLength);
        
        a1 = diagonalHHLength;
        b1 = diagonalHHHLength;
        a2 = diagonalHalfLength;
        b2 = diagonalHHLength;
        a3 = 0.8 * diagonalHalfLength;
        b3 = 0.8 * diagonalHHLength;

        for (int i = 0; i < 4; i++) {
            poleXCoordinates[i] = turretBaseLidCenter.getX() + a1 * Math.cos(polePointsAngles[i] * Math.PI / 180d);
            poleYCoordinates[i] = turretBaseLidCenter.getY() + b1 * Math.sin(polePointsAngles[i] * Math.PI / 180d);

            turretBodyXCoordinates[i] = turretPoleLidCenter.getX() + a2 * Math.cos(bodyPointsAngles[i] * Math.PI / 180d);
            turretBodyYCoordinates[i] = turretPoleLidCenter.getY() + b2 * Math.sin(bodyPointsAngles[i] * Math.PI / 180d);
        }

        for (int i = 0; i < 2; i++) {
            turretMuzzleXCoordinates[i] = turretBodyCenter.getX() + a3 * Math.cos(muzzlePointsAngles[i] * Math.PI / 180d);
            turretMuzzleYCoordinates[i] = turretBodyCenter.getY() + b3 * Math.sin(muzzlePointsAngles[i] * Math.PI / 180d);
        }
        
//        topFace = new Path2D.Double();
//        topFace.moveTo(x + diagonalHalfLength, y);
//        topFace.lineTo(pt0.getX(), pt0.getY());
//        topFace.lineTo(pt1.getX(), pt1.getY());
//        topFace.lineTo(pt2.getX(), pt2.getY());
//        topFace.closePath();

//        rightFace = new Path2D.Double();
//        rightFace.moveTo(pt1.getX(), pt1.getY());
//        rightFace.lineTo(pt0.getX(), pt0.getY());
//        rightFace.lineTo(pt0.getX(), pt0.getY() + diagonalHalfLength);
//        rightFace.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength);
//        rightFace.closePath();

//        leftFace = new Path2D.Double();
//        leftFace.moveTo(pt1.getX(), pt1.getY());
//        leftFace.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength);
//        leftFace.lineTo(pt2.getX(), pt2.getY() + diagonalHalfLength);
//        leftFace.lineTo(pt2.getX(), pt2.getY());
//        leftFace.closePath();


        turretBaseRP = new Point2D.Double(pt0.getX(), pt0.getY() + diagonalHalfLength - diagonalHHHLength);
        turretBaseMP = new Point2D.Double(pt1.getX(), pt1.getY() + diagonalHalfLength - diagonalHHHLength);
        turretBaseLP = new Point2D.Double(pt2.getX(), pt2.getY() + diagonalHalfLength - diagonalHHHLength);


        turretRightBase = new Path2D.Double();
        turretRightBase.moveTo(turretBaseRP.getX(), turretBaseRP.getY());
        turretRightBase.lineTo(turretBaseMP.getX(), turretBaseMP.getY());
        turretRightBase.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength);
        turretRightBase.lineTo(pt0.getX(), pt0.getY() + diagonalHalfLength);
        turretRightBase.closePath();

        
        turretLeftBase = new Path2D.Double();
        turretLeftBase.moveTo(turretBaseLP.getX(), turretBaseLP.getY());
        turretLeftBase.lineTo(turretBaseMP.getX(), turretBaseMP.getY());
        turretLeftBase.lineTo(pt1.getX(), pt1.getY() + diagonalHalfLength);
        turretLeftBase.lineTo(pt2.getX(), pt2.getY() + diagonalHalfLength);
        turretLeftBase.closePath();
        
        
        turretBaseLid = new Path2D.Double();
        turretBaseLid.moveTo(turretBaseRP.getX(), turretBaseRP.getY());
        turretBaseLid.lineTo(turretBaseMP.getX(), turretBaseMP.getY());
        turretBaseLid.lineTo(turretBaseLP.getX(), turretBaseLP.getY());
        turretBaseLid.lineTo(pt1.getX(), pt1.getY() - diagonalHHHLength);
        turretBaseLid.closePath();
        
        
        
        
        turretPoleRP = new Point2D.Double(poleXCoordinates[0], poleYCoordinates[0]);
        turretPoleBP = new Point2D.Double(poleXCoordinates[1], poleYCoordinates[1]);
        turretPoleLP = new Point2D.Double(poleXCoordinates[2], poleYCoordinates[2]);
        turretPoleTP = new Point2D.Double(poleXCoordinates[3], poleYCoordinates[3]);

        turretPoleLidRP = new Point2D.Double(turretPoleRP.getX(), turretPoleRP.getY() - diagonalHHLength);
        turretPoleLidBP = new Point2D.Double(turretPoleBP.getX(), turretPoleBP.getY() - diagonalHHLength);
        turretPoleLidLP = new Point2D.Double(turretPoleLP.getX(), turretPoleLP.getY() - diagonalHHLength);
        turretPoleLidTP = new Point2D.Double(turretPoleTP.getX(), turretPoleTP.getY() - 2 * diagonalHHHLength);



        turretPoleRFaceBack = new Path2D.Double();
        turretPoleRFaceBack.moveTo(turretPoleLidRP.getX(), turretPoleLidRP.getY());
        turretPoleRFaceBack.lineTo(turretPoleLidTP.getX(), turretPoleLidTP.getY());
        turretPoleRFaceBack.lineTo(turretPoleTP.getX(), turretPoleTP.getY());
        turretPoleRFaceBack.lineTo(turretPoleRP.getX(), turretPoleRP.getY());


        turretPoleRFaceFront = new Path2D.Double();
        turretPoleRFaceFront.moveTo(turretPoleLidRP.getX(), turretPoleLidRP.getY());
        turretPoleRFaceFront.lineTo(turretPoleLidBP.getX(), turretPoleLidBP.getY());
        turretPoleRFaceFront.lineTo(turretPoleBP.getX(), turretPoleBP.getY());
        turretPoleRFaceFront.lineTo(turretPoleRP.getX(), turretPoleRP.getY());


        turretPoleLFaceBack = new Path2D.Double();
        turretPoleLFaceBack.moveTo(turretPoleLidLP.getX(), turretPoleLidLP.getY());
        turretPoleLFaceBack.lineTo(turretPoleLidTP.getX(), turretPoleLidTP.getY());
        turretPoleLFaceBack.lineTo(turretPoleTP.getX(), turretPoleTP.getY());
        turretPoleLFaceBack.lineTo(turretPoleLP.getX(), turretPoleLP.getY());


        turretPoleLFaceFront = new Path2D.Double();
        turretPoleLFaceFront.moveTo(turretPoleLidLP.getX(), turretPoleLidLP.getY());
        turretPoleLFaceFront.lineTo(turretPoleLidBP.getX(), turretPoleLidBP.getY());
        turretPoleLFaceFront.lineTo(turretPoleBP.getX(), turretPoleBP.getY());
        turretPoleLFaceFront.lineTo(turretPoleLP.getX(), turretPoleLP.getY());


        turretBodyRP = new Point2D.Double(turretBodyXCoordinates[0], turretBodyYCoordinates[0]);
        turretBodyBP = new Point2D.Double(turretBodyXCoordinates[1], turretBodyYCoordinates[1]);
        turretBodyLP = new Point2D.Double(turretBodyXCoordinates[2], turretBodyYCoordinates[2]);
        turretBodyTP = new Point2D.Double(turretBodyXCoordinates[3], turretBodyYCoordinates[3]);

        turretBodyLidRP = new Point2D.Double(turretBodyRP.getX(), turretBodyRP.getY() - 3.5 * diagonalHHHLength);
        turretBodyLidBP = new Point2D.Double(turretBodyBP.getX(), turretBodyBP.getY() - 3.5 * diagonalHHHLength);
        turretBodyLidLP = new Point2D.Double(turretBodyLP.getX(), turretBodyLP.getY() - 3.5 * diagonalHHHLength);
        turretBodyLidTP = new Point2D.Double(turretBodyTP.getX(), turretBodyTP.getY() - 3.5 * diagonalHHHLength);



        turretBodyRFaceBack = new Path2D.Double();
        turretBodyRFaceBack.moveTo(turretBodyLidRP.getX(), turretBodyLidRP.getY());
        turretBodyRFaceBack.lineTo(turretBodyLidTP.getX(), turretBodyLidTP.getY());
        turretBodyRFaceBack.lineTo(turretBodyTP.getX(), turretBodyTP.getY());
        turretBodyRFaceBack.lineTo(turretBodyRP.getX(), turretBodyRP.getY());


        turretBodyRFaceFront = new Path2D.Double();
        turretBodyRFaceFront.moveTo(turretBodyLidRP.getX(), turretBodyLidRP.getY());
        turretBodyRFaceFront.lineTo(turretBodyLidBP.getX(), turretBodyLidBP.getY());
        turretBodyRFaceFront.lineTo(turretBodyBP.getX(), turretBodyBP.getY());
        turretBodyRFaceFront.lineTo(turretBodyRP.getX(), turretBodyRP.getY());


        turretBodyMuzzleP0 = new Point2D.Double(turretMuzzleXCoordinates[0], turretMuzzleYCoordinates[0]);
        turretBodyMuzzleP1 = new Point2D.Double(turretMuzzleXCoordinates[1], turretMuzzleYCoordinates[1]);

        turretBodyMuzzleRFaceFront = new Path2D.Double();
        turretBodyMuzzleRFaceFront.moveTo(turretBodyMuzzleP0.getX(), turretBodyMuzzleP0.getY());
        turretBodyMuzzleRFaceFront.lineTo(turretBodyMuzzleP1.getX(), turretBodyMuzzleP1.getY());
        turretBodyMuzzleRFaceFront.lineTo(turretBodyMuzzleP1.getX(), turretBodyMuzzleP1.getY() - 1.9 * diagonalHHHLength);
        turretBodyMuzzleRFaceFront.lineTo(turretBodyMuzzleP0.getX(), turretBodyMuzzleP0.getY() - 1.9 * diagonalHHHLength);

        turretBodyMuzzleRFaceFront.moveTo(turretBodyMuzzleP0.getX(), turretBodyMuzzleP0.getY());
        turretBodyMuzzleRFaceFront.lineTo(turretBodyRP.getX(), turretBodyRP.getY());

        turretBodyMuzzleRFaceFront.moveTo(turretBodyMuzzleP1.getX(), turretBodyMuzzleP1.getY());
        turretBodyMuzzleRFaceFront.lineTo(turretBodyBP.getX(), turretBodyBP.getY());

        turretBodyMuzzleRFaceFront.moveTo(turretBodyMuzzleP1.getX(), turretBodyMuzzleP1.getY() - 1.9 * diagonalHHHLength);
        turretBodyMuzzleRFaceFront.lineTo(turretBodyLidBP.getX(), turretBodyLidBP.getY());

        turretBodyMuzzleRFaceFront.moveTo(turretBodyMuzzleP0.getX(), turretBodyMuzzleP0.getY() - 1.9 * diagonalHHHLength);
        turretBodyMuzzleRFaceFront.lineTo(turretBodyLidRP.getX(), turretBodyLidRP.getY());
        
        
        turretBodyLFaceBack = new Path2D.Double();
        turretBodyLFaceBack.moveTo(turretBodyLidLP.getX(), turretBodyLidLP.getY());
        turretBodyLFaceBack.lineTo(turretBodyLidTP.getX(), turretBodyLidTP.getY());
        turretBodyLFaceBack.lineTo(turretBodyTP.getX(), turretBodyTP.getY());
        turretBodyLFaceBack.lineTo(turretBodyLP.getX(), turretBodyLP.getY());


        turretBodyLFaceFront = new Path2D.Double();
        turretBodyLFaceFront.moveTo(turretBodyLidLP.getX(), turretBodyLidLP.getY());
        turretBodyLFaceFront.lineTo(turretBodyLidBP.getX(), turretBodyLidBP.getY());
        turretBodyLFaceFront.lineTo(turretBodyBP.getX(), turretBodyBP.getY());
        turretBodyLFaceFront.lineTo(turretBodyLP.getX(), turretBodyLP.getY());


        turretBodyLid = new Path2D.Double();
        turretBodyLid.moveTo(turretBodyLidRP.getX(), turretBodyLidRP.getY());
        turretBodyLid.lineTo(turretBodyLidBP.getX(), turretBodyLidBP.getY());
        turretBodyLid.lineTo(turretBodyLidLP.getX(), turretBodyLidLP.getY());
        turretBodyLid.lineTo(turretBodyLidTP.getX(), turretBodyLidTP.getY());
    }
    
    @Override
    public void paintBlock(Graphics2D g2D) {
        createFaces();
//        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);


        g2D.setColor(turretBaseColor);
        g2D.fill(turretRightBase);

        g2D.setColor(turretBaseLeftColor);
        g2D.fill(turretLeftBase);

        g2D.setColor(turretBaseColor);
        g2D.fill(turretBaseLid);


        if (polePointsAngles[1] > 45 && polePointsAngles[1] <= 135) {
            g2D.setColor(turretPoleColor);
            g2D.fill(turretPoleRFaceFront);
            g2D.setColor(turretPoleLeftColor);
            g2D.fill(turretPoleLFaceFront);
        } else if (polePointsAngles[1] > 135 && polePointsAngles[1] <= 225) {
            g2D.setColor(turretPoleLeftColor);
            g2D.fill(turretPoleRFaceFront);
            g2D.setColor(turretPoleColor);
            g2D.fill(turretPoleRFaceBack);
        } else if (polePointsAngles[1] > 225 && polePointsAngles[1] <= 315){
            g2D.setColor(turretPoleLeftColor);
            g2D.fill(turretPoleRFaceBack);
            g2D.setColor(turretPoleColor);
            g2D.fill(turretPoleLFaceBack);
        } else {
            g2D.setColor(turretPoleColor);
            g2D.fill(turretPoleLFaceFront);
            g2D.setColor(turretPoleLeftColor);
            g2D.fill(turretPoleLFaceBack);
        }

        if (bodyPointsAngles[1] > 35 && bodyPointsAngles[1] <= 125) {
            g2D.setColor(turretBodyColor);
            g2D.fill(turretBodyRFaceFront);
            
            if (!shootMechanism.isRunning())
                g2D.setColor(Color.BLACK);
            else
                g2D.setColor(Color.RED);
            g2D.fill(turretBodyMuzzleRFaceFront);
            
            g2D.setColor(Color.BLACK);
            g2D.draw(turretBodyMuzzleRFaceFront);


            g2D.setColor(turretBodyLeftColor);
            g2D.fill(turretBodyLFaceFront);
        } else if (bodyPointsAngles[1] > 125 && bodyPointsAngles[1] <= 215) {
            g2D.setColor(turretBodyLeftColor);
            g2D.fill(turretBodyRFaceFront);
            
            if (!shootMechanism.isRunning())
                g2D.setColor(Color.BLACK);
            else
                g2D.setColor(Color.RED);
            g2D.fill(turretBodyMuzzleRFaceFront);
            
            g2D.setColor(Color.BLACK);
            g2D.draw(turretBodyMuzzleRFaceFront);


            g2D.setColor(turretBodyColor);
            g2D.fill(turretBodyRFaceBack);
        } else if (bodyPointsAngles[1] > 215 && bodyPointsAngles[1] <= 305){
            g2D.setColor(turretBodyLeftColor);
            g2D.fill(turretBodyRFaceBack);

            g2D.setColor(turretBodyColor);
            g2D.fill(turretBodyLFaceBack);
        } else {
            g2D.setColor(turretBodyColor);
            g2D.fill(turretBodyLFaceFront);
            g2D.setColor(turretBodyLeftColor);
            g2D.fill(turretBodyLFaceBack);
        }
        
        g2D.setColor(turretBodyColor);
        g2D.fill(turretBodyLid);
    }

    public void lookAt(double x, double y) {
        double rx = x - this.x;
        double ry = y - this.y;
//        double mx = p.getX() - turretBaseLidCenter.getX();
//        double my = p.getY() - turretBaseLidCenter.getY();
//        double mx = p.getX() - (turretBaseLidCenter.getX() + 140);
//        double my = p.getY() - (turretBaseLidCenter.getY() + 80);

        angle = Math.atan2(ry, rx);
        angle *= 180d / Math.PI;
        angle -= 50;

//        System.out.println("p (" + x + ", " + y + ")");
//        System.out.println("p " + p);
//        System.out.println("cen (" + (turretBaseLidCenter.getX() + 140) + ", " + (turretBaseLidCenter.getY() + 80) + ")");
//        System.out.println("cen (" + turretBaseLidCenter.getX() + ", " + turretBaseLidCenter.getY() + ")");
//        System.out.println("cen (" + this.x + ", " + this.y + ")");
//        System.out.println("angle = " + angle);

        polePointsAngles[0] = angle;
        polePointsAngles[1] = 90 + angle;
        polePointsAngles[2] = 180 + angle;
        polePointsAngles[3] = 270 + angle;

        bodyPointsAngles[0] = 10 + angle;
        bodyPointsAngles[1] = 80 + angle;
        bodyPointsAngles[2] = 190 + angle;
        bodyPointsAngles[3] = 260 + angle;
        
        for (int i = 0; i < 4; i++) {
            if (polePointsAngles[i] < 0)
                polePointsAngles[i] += 360;

            if (bodyPointsAngles[i] < 0)
                bodyPointsAngles[i] += 360;
        }

        muzzlePointsAngles[0] = 25 + angle;
        muzzlePointsAngles[1] = 65 + angle;

        for (int i = 0; i < 2; i++)
            if (muzzlePointsAngles[i] < 0)
                muzzlePointsAngles[i] += 360;
    }

    public void setShootPower(int shootPower) {
        this.shootPower = shootPower;
    }

    public void setShootFrequency(int shootFrequency) {
        this.shootFrequency = shootFrequency;
        shootMechanism = new Timer(shootFrequency, (Action) -> {
            damageTarget();
        });
    }
    
    public void stopShootMechanism() {
        shootMechanism.stop();
    }
    
    public void startShootMechanism() {
        shootMechanism.start();
    }
    
    public void setTarget(CubeMonster target) {
        this.target = target;
        
        if (!shootMechanism.isRunning())
            shootMechanism.start();
    }
    
    public Rectangle getPaintArea() {
        return new Rectangle((int) pt2.getX(), (int) (pt2.getY() - diagonalLength), (int) diagonalLength, (int) diagonalLength * 2);
    }

    private void damageTarget() {
        if (target == null)
            return;
        
        if (target.isDead()) {
            target = null;
            return;
        }
        
        target.reduceHealth(shootPower);
    }
}
