package ui.gameui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import ui.blocks.ClockBlock;
import ui.enemies.CubeMonster;
import ui.enemies.EyeMonster;
import ui.enemies.FaceMonster;
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
    
    
    private int steps = 0;
    
    private boolean creatingEnemies = false;
    
    private ClockBlock clockBlock;
    
    private final ArrayList<CubeMonster> cubeMonsters = new ArrayList<>();
    private CubeMonster lastMonster;
    
    private final int cubeMonsterWidth;
    private boolean allEnemiesDead = true;
    
    
    private final GameUI gameUI;
    
    
    public EnemyRenderComponent(GameUI container) {
        this.gameUI = container;
        
        cubeMonsterWidth = (int) this.gameUI.mainWindow.world.getGridLength();
        
        initEnemyRenderComponent();
    }

    private void initEnemyRenderComponent() {
        updateNumberOfSteps(0);
        setPreferredSize(new Dimension(1000, 600));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
//        for (int i = cubeMonsters.size() - 1; i >= 0; i--)
//            cubeMonsters.get(i).paintBlock((Graphics2D) g);

        Graphics2D g2D = (Graphics2D) g;

        clockBlock.paintHealthBar(g2D);
        
        for (CubeMonster cm : cubeMonsters)
            cm.paintBlock(g2D);
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
            int i = 0;
            while (i < monsterAmount) {
                if (gameUI.isPaused())
                    try { Thread.sleep(100); } catch (InterruptedException ex) { }
                
                if (lastMonster != null)
                    while (lastMonster.getLastCoordinate() < 4)
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
        }).start();
    }
    
    public void createNewCubeMonster(int health, Point2D spawnCoordinates) {
        if ((int) (Math.random() * 10) % 2 == 0)
            lastMonster = new EyeMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), cubeMonsterWidth, health);
        else
            lastMonster = new FaceMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), cubeMonsterWidth, health);
        
        lastMonster.setNumberOfSteps(gameUI.stats.getNumberOfSteps());
        lastMonster.setNextTilePosition(gameUI.mainWindow.world.getEnemyPathCoordinate(0));
        lastMonster.defineFacingDirection();
        cubeMonsters.add(lastMonster);
        
        creatingEnemies = false;
    }
    
    private void moveCubeMonsters() {
        for (CubeMonster cm : cubeMonsters) {
            cm.moveToNextLocation();
            repaint(cm.getPaintArea());
        }
    }

    private void moveEnemies() {
        if (creatingEnemies)
            return;
        
        if (cubeMonsters.isEmpty()) {
            allEnemiesDead = true;
            return;
        }
        
        int i = 0;
        while (i < cubeMonsters.size()) {
            CubeMonster cm = cubeMonsters.get(i);

            if (!cm.movementEnded()) {
                i++;
                continue;
            } else {
                cm.increaseLastCoordinate();
            }

            boolean remove = cm.setNextTilePosition(gameUI.mainWindow.world.getEnemyPathCoordinate(cm.getLastCoordinate()));
            if (remove) {
                gameUI.decreaseClockHealth();
                cubeMonsters.remove(i);
                repaint(cm.getPaintArea());
                continue;
            }

            cm.defineFacingDirection();

            i++;
        }

        if (cubeMonsters.isEmpty()) {
            steps = 0;
            return;
        }
        
        moveCubeMonsters();
        
        steps++;
    }
}
