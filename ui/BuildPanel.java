package ui;

import java.awt.Color;
import ui.blocks.Block;
import ui.construction.ArrangeBlockPanel;
import ui.construction.BlockCreationPanel;
import ui.construction.BlockFactory;
import ui.construction.WorldPropertiesPanel;
import ui.enums.BlockTypes;
import ui.enums.ImageButtonArrangement;
import ui.enums.UIAlignment;
import utils.AppUtilities;
import utils.WorldUtilities;

/**
 *
 * @author cristopher
 */
public class BuildPanel extends Panel {
    public enum Panels {
        WORLD, CREATE, ARRANGE
    }
    
    private final ImageButton worldPropertiesButton = new ImageButton("World", false, ImageButtonArrangement.ONE_WORD_ICON_BUTTON);
    private final ImageButton blockCreationButton = new ImageButton("Block", false, ImageButtonArrangement.ONE_WORD_ICON_BUTTON);
    private final ImageButton blockArrangeButton = new ImageButton("Order", false, ImageButtonArrangement.ONE_WORD_ICON_BUTTON);
    
    private final WorldPropertiesPanel worldPropertiesPanel = new WorldPropertiesPanel(this);
    private final BlockCreationPanel blockCreationPanel = new BlockCreationPanel(this);
    private final ArrangeBlockPanel arrangeBlockPanel = new ArrangeBlockPanel(this);
    
    
    private boolean updatingBlock = false;
    private boolean updatingWorld = false;
    
    
    private final GameWindow mainWindow;
    
    
    public BuildPanel(GameWindow mainWindow) {
        super(230, 602);
        
        this.mainWindow = mainWindow;
        
        initBuildPanel();
    }
    
    private void initBuildPanel() {
        AppUtilities.addBundleImagesToImageButton(worldPropertiesButton, "World", 20);
        AppUtilities.addBundleImagesToImageButton(blockCreationButton, "Cube", 20);
        AppUtilities.addBundleImagesToImageButton(blockArrangeButton, "BackStack", 20);
        
        worldPropertiesButton.addActionListener((Action) -> {
            loadPanel(Panels.WORLD);
        });
        
        blockCreationButton.addActionListener((Action) -> {
            loadPanel(Panels.CREATE);
        });
        
        blockArrangeButton.addActionListener((Action) -> {
            loadPanel(Panels.ARRANGE);
        });
        
        
        add(worldPropertiesButton, blockCreationButton, this, UIAlignment.EAST, UIAlignment.WEST, -20, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(blockCreationButton, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(blockArrangeButton, blockCreationButton, this, UIAlignment.WEST, UIAlignment.EAST, 20, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        
//        worldPropertiesPanel.setVisible(false);
        blockCreationPanel.setVisible(false);
        arrangeBlockPanel.setVisible(false);
        
        add(worldPropertiesPanel, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.SOUTH, UIAlignment.SOUTH, 0);
        add(blockCreationPanel, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.SOUTH, UIAlignment.SOUTH, 0);
        add(arrangeBlockPanel, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.SOUTH, UIAlignment.SOUTH, 0);
    }
    
    public void loadWorld(World w) {
        updatingWorld = true;
        worldPropertiesPanel.title.setText(w.name, true);
        worldPropertiesPanel.fillColorPicker.setSelectedColor(w.getBackgroundColor(), true);
        worldPropertiesPanel.fillStateSelector.setText(w.bgmode);
        
        w.setMainWindow(mainWindow);        
        
        arrangeBlockPanel.xCoordinateSelector.setMaximumValue((int) w.widthInSquares);
        arrangeBlockPanel.yCoordinateSelector.setMaximumValue((int) w.heightInSquares);
        
        updatingWorld = false;
    }
    
    public void setWorldName(String name) {
        if (updatingWorld)
            return;
        
        mainWindow.world.name = name;
    }
    
    public void setWorldBGMode(String bgmode) {
        if (updatingWorld)
            return;
        
        mainWindow.world.setBGmode(bgmode);
        mainWindow.world.repaint();
    }
    
    public void setWorldBGColor(Color c) {
        if (updatingWorld)
            return;
        
        mainWindow.world.setBackgroundColor(c);
        mainWindow.world.repaint();
    }

    public void setWorldGridVisible(boolean gridVisible) {
        if (updatingWorld)
            return;
        
        mainWindow.world.setGridVisible(gridVisible);
    }
    
    public boolean worldExist() {
        return WorldUtilities.createWorldFile(mainWindow.world).exists();
    }
    
    public boolean saveWorld() {
        return WorldUtilities.saveWorld(mainWindow.world);
    }
    
    public void loadBlockProperties(Block b) {
        updatingBlock = true;
        blockCreationPanel.loadProperties(b);
        arrangeBlockPanel.loadProperties(b);
        updatingBlock = false;
    }
    
    public void updateBlockCoordinates() {
        arrangeBlockPanel.loadProperties(mainWindow.world.blockSelector.getSelectedBlock());
    }
    
    public void updateVisibleFaces(boolean top, boolean right, boolean left) {
        if (updatingBlock)
            return;
        
        Block b = mainWindow.world.blockSelector.getSelectedBlock();
        if (b == null)
            return;
        
        b.setTopFaceVisible(top);
        b.setRightFaceVisible(right);
        b.setLeftFaceVisible(left);
        
        mainWindow.world.repaint();
    }
    
    public void addBlockToWorld(Block preview, BlockTypes type) {
        if (updatingBlock)
            return;
        
        Block b = BlockFactory.createBlock(1, 1, mainWindow.world.getGridLength(), type);
        b.copyProperties(preview);
        
        mainWindow.world.addBlock(b);
    }
    
    public void changeBlockInWorld(BlockTypes type) {
        if (updatingBlock)
            return;
        
        if (mainWindow.world.blockSelector.getSelectedBlock() == null)
            return;
        
        Block b = BlockFactory.createBlock(1, 1, mainWindow.world.getGridLength(), type);
        b.copyProperties(mainWindow.world.blockSelector.getSelectedBlock());
        
        mainWindow.world.setBlock(mainWindow.world.blockSelector.getSelectedBlockIndex(), b);
    }
    
    public void deleteSelectedBlockFromWorld() {
        mainWindow.world.deleteSelectedBlock();
    }
    
    public void moveSelectedBlockALayer(boolean forward) {
        mainWindow.world.moveSelectedBlockALayer(forward);
        mainWindow.world.repaint();
    }
    
    public void moveSelectedBlockTo(boolean front) {
        mainWindow.world.moveSelectedBlockTo(front);
        mainWindow.world.repaint();
    }
    
    public void moveSelectedBlock(double xGrid, double yGrid) {
        if (mainWindow.world.blockSelector.getSelectedBlock() == null)
            return;
        
        mainWindow.world.blockSelector.getSelectedBlock().setCoordinates(xGrid, yGrid, false);
        mainWindow.world.repaint();
    }
    
    public void loadPanel(Panels p) {
        worldPropertiesPanel.setVisible(false);
        blockCreationPanel.setVisible(false);
        arrangeBlockPanel.setVisible(false);
        
        switch(p) {
            case WORLD:
                worldPropertiesPanel.setVisible(true);
            break;
            case CREATE:
                blockCreationPanel.setVisible(true);
            break;
            case ARRANGE:
                arrangeBlockPanel.setVisible(true);
            break;
        }
        
        revalidate();
        repaint();
    }
}
