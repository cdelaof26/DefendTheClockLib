package ui.gameui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import javax.swing.Timer;
import ui.GameWindow;
import ui.enemies.CubeMonster;
import ui.enemies.EyeMonster;
import ui.enemies.FaceMonster;
import ui.enums.CubeMonsterFacing;

/**
 *
 * @author cristopher
 */
public class EnemyRenderComponent extends JComponent {
    private CubeMonster monster;
    private int lastCoordinate = 0;
    
    
    private final Timer renderRefresh;
    
    
    private final GameWindow mainWindow;
    
    
    public EnemyRenderComponent(GameWindow mainWindow) {
        this.mainWindow = mainWindow;
        renderRefresh = new Timer(100, (Action) -> {
            if (mainWindow.isPaused())
                return;

            moveEnemies();
            repaint();
        });
        
        initEnemyRenderComponent();
    }

    private void initEnemyRenderComponent() {
        renderRefresh.start();
        setPreferredSize(new Dimension(1000, 600));
//        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (monster != null)
            monster.paintBlock((Graphics2D) g);
        
//        g.setColor(Color.RED);
//        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
    }
    
    public void createNewCubeMonster(Point2D spawnCoordinates, double diagonalLength) {
        if ((int) (Math.random() * 10) % 2 == 0)
            monster = new EyeMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), diagonalLength);
        else
            monster = new FaceMonster(spawnCoordinates.getX(), spawnCoordinates.getY(), diagonalLength);
        
        lastCoordinate = 0;
    }
    
    private CubeMonsterFacing getFacingDirection(Point2D oldCoordinates, Point2D newCoordinates) {
        double xDifference = newCoordinates.getX() - oldCoordinates.getX();
        double yDifference = newCoordinates.getY() - oldCoordinates.getY();
        
        if (xDifference < 0 && yDifference > 0)
            return CubeMonsterFacing.LEFT;
        
        if (xDifference > 0 && yDifference > 0)
            return CubeMonsterFacing.RIGHT;
        
        return CubeMonsterFacing.NONE;
    }
    
    private void moveEnemies() {
        if (monster == null)
            return;
        
        Point2D p = mainWindow.world.getEnemyPathCoordinate(lastCoordinate);
        if (p == null) {
            monster = null;
            return;
        }
        
        Point2D lastCoordinates = monster.getCoordinates();
        
        monster.setCoordinates(p.getX(), p.getY(), false);
        monster.setFacingDirection(getFacingDirection(lastCoordinates, p));
        
        lastCoordinate++;
    }
}
