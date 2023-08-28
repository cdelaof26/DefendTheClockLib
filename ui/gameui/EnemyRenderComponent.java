package ui.gameui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import ui.UIProperties;
import ui.blocks.Block;
import ui.blocks.ClockBlock;
import ui.enemies.CubeMonster;
import ui.enemies.EyeMonster;
import ui.enemies.FaceMonster;
import ui.mouselisteners.CoordBlockSelector;
import ui.turrets.BasicTurret;
import ui.turrets.Turrets;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class EnemyRenderComponent extends JComponent {
    private Timer renderRefresh;
    
    private final int wthreads = 6;
    private final Thread [] winThreads = new Thread[wthreads];
    private int activeID = -1;
    
    
    private boolean creatingEnemies = false;
    
    private ClockBlock clockBlock;
    
    private ArrayList<CubeMonster> cubeMonsters = new ArrayList<>();
    private CubeMonster lastMonster;
    
    private ArrayList<Block> turrets = new ArrayList<>();
    
    private final int gridWidth;
    private boolean allEnemiesDead = true;
    
    
    public final GameUI gameUI;
    
    
    public EnemyRenderComponent(GameUI container) {
        this.gameUI = container;
        
        gridWidth = (int) this.gameUI.mainWindow.world.getGridLength();
        
        initEnemyRenderComponent();
    }

    private void initEnemyRenderComponent() {
        addMouseListener(new CoordBlockSelector(this));
        
        updateNumberOfSteps(0);
        setPreferredSize(new Dimension(1000, 600));
    }
    
    @Override
    public final void setPreferredSize(Dimension preferredSize) {
        preferredSize.width = (int) (preferredSize.width * UIProperties.getUiScale());
        preferredSize.height = (int) (preferredSize.height * UIProperties.getUiScale());
        
        super.setPreferredSize(preferredSize);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
//        for (int i = cubeMonsters.size() - 1; i >= 0; i--)
//            cubeMonsters.get(i).paintBlock((Graphics2D) g);

        Graphics2D g2D = (Graphics2D) g;
//        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        for (CubeMonster cm : cubeMonsters)
            cm.paintBlock(g2D);
        
        for (Block t : turrets)
            t.paintBlock(g2D);
        
        for (CubeMonster cm : cubeMonsters)
            cm.paintHealthBar(g2D);
        
        clockBlock.paintHealthBar(g2D);
    }
    
    public void updateNumberOfSteps(int stepsGain) {
        gameUI.stats.updateNumberOfSteps(stepsGain);
        
        int wait = gameUI.stats.getNumberOfSteps() / 100;
        
        // idk why is windows always giving me weird bugs,
        // opening six threads will fix (kinda) the performance speed
        if (!LibUtilities.IS_UNIX_LIKE) {
            activeID++;
//            System.out.println("threads started");
            int id = activeID;
            for (int i = 0; i < wthreads; i++) {
//                int j = i;
                winThreads[i] = new Thread(() -> {
                    try { Thread.sleep(1000); } catch (InterruptedException ex) { }
                    while (id == activeID)
                        try { Thread.sleep(wait); } catch (InterruptedException ex) { }
                    
//                    System.out.println("thread" + j + " ended");
                });
                winThreads[i].start();
            }
            
//            System.out.println("settled activeID=" + activeID);
        }
        
        if (renderRefresh != null)
            renderRefresh.stop();
        
        renderRefresh = new Timer(wait, (Action) -> {
            if (gameUI.isPaused())
                return;
            
            moveEnemies();
        });
        
        renderRefresh.start();
    }
    
    public boolean areAllEnemiesDead() {
        return allEnemiesDead;
    }

    public void setClockBlock(ClockBlock clockBlock) {
        this.clockBlock = clockBlock;
    }
    
    public void setClockBlockHealthBarVisible(boolean b) {
        clockBlock.setHealthBarVisible(b);
    }
    
    public void setClockHealth(int value) {
        clockBlock.setHealth(value);
        repaint(clockBlock.getHealthPaintArea());
    }
    
    public void generateMonsterHorde(int monsterAmount, int health, Point2D spawnCoordinates) {
        creatingEnemies = true;
        allEnemiesDead = false;
        
        new Thread(() -> {
            for (Block t : turrets) {
                if (t instanceof BasicTurret)
                    ((BasicTurret) t).startShootMechanism();
            }
            
            int i = 0;
            while (i < monsterAmount) {
                if (gameUI.isPaused())
                    try { Thread.sleep(100); } catch (InterruptedException ex) { }
                
                while (lastMonster != null && lastMonster.getLastCoordinate() < 4)
                    try { Thread.sleep(100); } catch (InterruptedException ex) { }
                
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        createNewCubeMonster(health, spawnCoordinates);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
                
                i++;
            }
            
            creatingEnemies = false;
        }).start();
    }
    
    public void createNewCubeMonster(int health, Point2D spawnCoordinates) {
        if ((int) (Math.random() * 10) % 2 == 0)
            lastMonster = new EyeMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), gridWidth, health);
        else
            lastMonster = new FaceMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), gridWidth, health);
        
        lastMonster.setNumberOfSteps(gameUI.stats.getNumberOfSteps());
        lastMonster.setNextTilePosition(gameUI.mainWindow.world.getEnemyPathCoordinate(0));
        lastMonster.defineFacingDirection();
        cubeMonsters.add(lastMonster);
    }
    
    public void createTurret(Turrets type, double xGrid, double yGrid) {
        Block t = null;
        
        switch(type) {
            case BASIC_TURRET:
                t = new BasicTurret(xGrid, yGrid, gridWidth);
                t.createFaces();
                turrets.add(t);
                repaint(((BasicTurret) t).getPaintArea());
            break;
        }
    }
    
    public void despawnCubeMonsters() {
        for (CubeMonster cm : cubeMonsters)
            cm.dispose();
        
        cubeMonsters = new ArrayList<>();
        lastMonster = null;
    }
    
    public void disassembleAllTurrets() {
        turrets = new ArrayList<>();
    }
    
    public boolean isPlaceOccupied(Point2D p) {
        for (Block t : turrets)
            if (t.doCoordinatesEqual(p))
                return true;
        
        return false;
    }
    
    private void moveCubeMonsters() {
        for (CubeMonster cm : cubeMonsters) {
            cm.moveToNextLocation();
            repaint(cm.getPaintArea());
        }
    }
    
    private void moveEnemies() {
        if (cubeMonsters.isEmpty() && !creatingEnemies) {
            lastMonster = null;
            allEnemiesDead = true;
            for (Block t : turrets) {
                if (t instanceof BasicTurret)
                    ((BasicTurret) t).stopShootMechanism();
            }
            
            return;
        }
        
        int i = 0;
        while (i < cubeMonsters.size()) {
            CubeMonster cm = cubeMonsters.get(i);

            if (!cm.didMovementEnd()) {
                i++;
                continue;
            } else {
                cm.increaseLastCoordinate();
            }

            boolean remove = cm.setNextTilePosition(gameUI.mainWindow.world.getEnemyPathCoordinate(cm.getLastCoordinate()));
            if (remove) {
                gameUI.decreaseClockHealth();
                cubeMonsters.remove(i).dispose();
                repaint(cm.getPaintArea());
                continue;
            } else if (cm.isDead()) {
                cubeMonsters.remove(i).dispose();
                repaint(cm.getPaintArea());
                if (cm == lastMonster)
                    lastMonster = null;
                continue;
            }

            cm.defineFacingDirection();

            i++;
        }

        if (cubeMonsters.isEmpty())
            return;
        
        moveCubeMonsters();
        
        for (Block t : turrets) {
            if (t instanceof BasicTurret) {
                BasicTurret bt = (BasicTurret) t;
                bt.setTarget(cubeMonsters.get(0));
                bt.lookAt(cubeMonsters.get(0).getX(), cubeMonsters.get(0).getY());
                repaint(bt.getPaintArea());
            }
        }
    }
}
