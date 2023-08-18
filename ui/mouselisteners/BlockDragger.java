package ui.mouselisteners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ui.UIProperties;
import ui.World;
import ui.blocks.Block;

/**
 *
 * @author cristopher
 */
public class BlockDragger extends MouseAdapter {
    private final World container;

    private Block b;
    private double gridLength;
    private double gridHalfLength;
    private boolean reinit = true;
    
    private double widthInSquares;
    private double heightInSquares;
    
    public BlockDragger(World container) {
        this.container = container;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        reinit = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (container.mainWindow == null)
            return;
        
        Point p = e.getPoint();
        
        if (reinit) {
            b = container.blockSelector.selectedBlock;
            
            if (b == null) {
                b = container.selectBlock(p);
                container.blockSelector.setSelectedBlock(b);
            }

            if (b == null)
                return;

            gridLength = container.getGridLength() * UIProperties.getUiScale();
            gridHalfLength = container.getGridHalfLength() * UIProperties.getUiScale();
            
            widthInSquares = container.getWidthInSquares();
            heightInSquares = container.getHeightInSquares();
            
            if (!b.isPointClose(p, (int) gridLength)) {
                container.blockSelector.setSelectedBlock(null);
                container.repaint();
                return;
            }
            
            reinit = false;
        }
        
        double x = (p.x + gridHalfLength) / gridLength;
        double y = (p.y + gridHalfLength) / gridLength;
        
        if (x < 1)
            x = 1;
        if (x > widthInSquares)
            x = widthInSquares;
        
        if (y > heightInSquares)
            y = heightInSquares;
        if (y < 1)
            y = 1;
        
        b.setCoordinates(x, y, true);
        container.mainWindow.buildPanel.updateBlockCoordinates();
        
        container.repaint();
    }
}
