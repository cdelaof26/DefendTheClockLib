package ui.gameui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import ui.enemies.CubeMonster;
import ui.enemies.EyeMonster;
import ui.enemies.FaceMonster;

/**
 *
 * @author cristopher
 */
public class EnemyRenderComponent extends JComponent {
    private Timer renderRefresh;
    
    private int steps = 0;
    
    private boolean creatingEnemies = false;
    
    private final ArrayList<CubeMonster> cubeMonsters = new ArrayList<>();
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
        renderRefresh.start();
        setPreferredSize(new Dimension(1000, 600));
    }
    
    public void updateNumberOfSteps(int stepsGain) {
        gameUI.stats.updateNumberOfSteps(stepsGain);
        
        renderRefresh = new Timer(gameUI.stats.getNumberOfSteps() / 100, (Action) -> {
            if (gameUI.isPaused())
                return;

            moveEnemies();
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
//        for (int i = cubeMonsters.size() - 1; i >= 0; i--)
//            cubeMonsters.get(i).paintBlock((Graphics2D) g);
        for (CubeMonster cm : cubeMonsters)
            cm.paintBlock((Graphics2D) g);
    }
    
    public void generateMonsterHorde(int monsterAmount, int health, Point2D spawnCoordinates) {
        creatingEnemies = true;
        allEnemiesDead = false;
        
        new Thread(() -> {
            for (int i = 0; i < monsterAmount; i++) {
                if (gameUI.isPaused()) {
                    i--;
                    try { Thread.sleep(1500); } catch (InterruptedException ex) { }
                }
                
                SwingUtilities.invokeLater(() -> {
                    createNewCubeMonster(health, spawnCoordinates);
                });
                
                try { Thread.sleep(1500); } catch (InterruptedException ex) { }
            }
        }).start();
    }
    
    public void createNewCubeMonster(int health, Point2D spawnCoordinates) {
        CubeMonster cm;
        
        if ((int) (Math.random() * 10) % 2 == 0)
            cm = new EyeMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), cubeMonsterWidth, health);
        else
            cm = new FaceMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), cubeMonsterWidth, health);
        
        cm.setNumberOfSteps(gameUI.stats.getNumberOfSteps());
        cm.setNextTilePosition(gameUI.mainWindow.world.getEnemyPathCoordinate(cm.getLastCoordinate()));
        cm.defineFacingDirection();
        cubeMonsters.add(cm);
        
        creatingEnemies = false;
    }
    
    private void moveCubeMonsters() {
        for (CubeMonster cm : cubeMonsters) {
            cm.moveToNextLocation();
            repaint(cm.getPaintArea());
        }
    }

    public boolean areAllEnemiesDead() {
        return allEnemiesDead;
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
