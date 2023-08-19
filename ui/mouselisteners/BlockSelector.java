package ui.mouselisteners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ui.World;
import ui.blocks.Block;

/**
 *
 * @author cristopher
 */
public class BlockSelector extends MouseAdapter {
    private final World container;
    
    protected Block selectedBlock;
    protected int selectedBlockIndex = 0;

    public BlockSelector(World container) {
        this.container = container;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (container.mainWindow == null)
            return;
        
        if (selectedBlock != null)
            selectedBlock.setSelected(false);
        
        selectedBlock = container.selectBlock(e.getPoint());
        container.mainWindow.buildPanel.loadBlockProperties(selectedBlock);
        
        container.repaint();
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }
    
    public void setSelectedBlock(Block selectedBlock) {
        if (this.selectedBlock != null)
            this.selectedBlock.setSelected(false);
        
        this.selectedBlock = selectedBlock;
        
        if (this.selectedBlock != null)
            this.selectedBlock.setSelected(true);
        
        container.mainWindow.buildPanel.loadBlockProperties(selectedBlock);
    }

    public void setSelectedBlockIndex(int selectedBlockIndex) {
        this.selectedBlockIndex = selectedBlockIndex;
    }

    public int getSelectedBlockIndex() {
        return selectedBlockIndex;
    }
    
    public void unselectBlock() {
        selectedBlock.setSelected(false);
        selectedBlock = null;
    }
}
