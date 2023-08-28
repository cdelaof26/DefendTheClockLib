package ui.mouselisteners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import ui.gameui.EnemyRenderComponent;

/**
 *
 * @author cristopher
 */
public class CoordBlockSelector extends MouseAdapter {
    private final EnemyRenderComponent container;

    public CoordBlockSelector(EnemyRenderComponent container) {
        this.container = container;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (container.gameUI.isPaused())
            return;
        
        Point2D selectedPoint = container.gameUI.mainWindow.world.selectTopBlock(e.getPoint());
        if (selectedPoint == null)
            return;
        
        if (!container.isPlaceOccupied(selectedPoint)) {
            container.gameUI.turretsMenu.setxGrid(selectedPoint.getX());
            container.gameUI.turretsMenu.setyGrid(selectedPoint.getY());
            container.gameUI.showTurretsMenu();
        }
    }
}
