package ui.construction;

import ui.BuildPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import ui.CheckField;
import ui.ComboBox;
import ui.ImageButton;
import ui.Label;
import ui.Panel;
import ui.UIProperties;
import ui.blocks.Block;
import ui.enums.BlockTypes;
import ui.enums.ImageButtonArrangement;
import ui.enums.LabelType;
import ui.enums.UIAlignment;
import utils.AppUtilities;

/**
 *
 * @author cristopher
 */
public class BlockCreationPanel extends Panel {
    private final Label title = new Label(LabelType.BOLD_TITLE, "Create block");
    
    private final Label newBlockSubtitle = new Label(LabelType.SUBTITLE, "New");
    private final ComboBox blockTypeSelector = new ComboBox("Block type", "Wire", true);
    
    private Block previewBlock = BlockFactory.createBlock(1, 1, 120, BlockTypes.WIRE);
    private final JComponent blockPreview = new JComponent() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
        
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
            previewBlock.paintBlock(g2D);
        }
    };
    
    private final ImageButton addBlockButton = new ImageButton("Add", false, ImageButtonArrangement.UP_IMAGE);
    private final ImageButton deleteBlockButton = new ImageButton("Delete", false, ImageButtonArrangement.UP_IMAGE);
    
    private final Label blockFacesSubtitle = new Label(LabelType.SUBTITLE, "Block aspect");
    private final CheckField showTopFaceCheckField = new CheckField("Show top face", false, true);
    private final CheckField showRightFaceCheckField = new CheckField("Show right face", false, true);
    private final CheckField showLeftFaceCheckField = new CheckField("Show left face", false, true);
    
    
    private BlockTypes blockType = BlockTypes.WIRE;
    
    
    private final BuildPanel container;

    
    public BlockCreationPanel(BuildPanel container) {
        super(230, 520);
        
        this.container = container;
        
        initBlockCreationPanel();
    }
    
    private void initBlockCreationPanel() {
        blockTypeSelector.setPreferredSize(new Dimension(190, 22));
        
        ActionListener action = (Action) -> {
            updateSelection(false);
        };
        
        for (BlockTypes t : BlockTypes.values()) {
            String name = t.name().charAt(0) + t.name().substring(1).toLowerCase();
            blockTypeSelector.addOption(name, false, action);
        }
        
        blockPreview.setPreferredSize(new Dimension(121, 120));
        
        AppUtilities.addBundleImagesToImageButton(addBlockButton, "Add", 20);
        AppUtilities.addBundleImagesToImageButton(deleteBlockButton, "Delete", 20);
        
        addBlockButton.addActionListener((Action) -> {
            container.addBlockToWorld(previewBlock, blockType);
        });
        
        deleteBlockButton.addActionListener((Action) -> {
            container.deleteSelectedBlockFromWorld();
        });
        
        showTopFaceCheckField.setPreferredSize(new Dimension(210, 22));
        showTopFaceCheckField.addActionListener((Action) -> {
            previewBlock.setTopFaceVisible(!showTopFaceCheckField.isChecked());
            container.updateVisibleFaces(!showTopFaceCheckField.isChecked(), showRightFaceCheckField.isChecked(), showLeftFaceCheckField.isChecked());
            blockPreview.repaint();
        });
        
        showRightFaceCheckField.setPreferredSize(new Dimension(210, 22));
        showRightFaceCheckField.addActionListener((Action) -> {
            previewBlock.setRightFaceVisible(!showRightFaceCheckField.isChecked());
            container.updateVisibleFaces(showTopFaceCheckField.isChecked(), !showRightFaceCheckField.isChecked(), showLeftFaceCheckField.isChecked());
            blockPreview.repaint();
        });
        
        showLeftFaceCheckField.setPreferredSize(new Dimension(210, 22));
        showLeftFaceCheckField.addActionListener((Action) -> {
            previewBlock.setLeftFaceVisible(!showLeftFaceCheckField.isChecked());
            container.updateVisibleFaces(showTopFaceCheckField.isChecked(), showRightFaceCheckField.isChecked(), !showLeftFaceCheckField.isChecked());
            blockPreview.repaint();
        });
        
        
        add(title, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 0);
        
        add(newBlockSubtitle, this, title, UIAlignment.WEST, UIAlignment.WEST, 20, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(blockTypeSelector, this, newBlockSubtitle, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(blockPreview, this, blockTypeSelector, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(addBlockButton, this, blockPreview, UIAlignment.WEST, UIAlignment.WEST, 20, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(deleteBlockButton, this, blockPreview, UIAlignment.EAST, UIAlignment.EAST, -20, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(blockFacesSubtitle, newBlockSubtitle, addBlockButton, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 20);
        add(showTopFaceCheckField, blockPreview, blockFacesSubtitle, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(showRightFaceCheckField, blockPreview, showTopFaceCheckField, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(showLeftFaceCheckField, blockPreview, showRightFaceCheckField, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
    }

    @Override
    public void updateUISize() {
        if (blockPreview != null) {
            int l = (int) (120 * UIProperties.getUiScale());
            blockPreview.setPreferredSize(new Dimension(l + 1, l));
        }
        super.updateUISize();
    }
    
    public void updateSelection(boolean loadingBlock) {
        blockType = BlockTypes.valueOf(blockTypeSelector.getText_().toUpperCase());
        
        previewBlock = BlockFactory.createBlock(1, 1, 120, blockType);
        
        if (!loadingBlock) {
            showTopFaceCheckField.setChecked(true);
            showRightFaceCheckField.setChecked(true);
            showLeftFaceCheckField.setChecked(true);
            
            container.changeBlockInWorld(blockType);
        }
        
//        toggleFacesToggles();
        blockPreview.repaint();
        
        
        
//        if (selectedBlock == null)
//            return;
//        
//        selectedBlock.setType(getBlockType());
//        container.map.repaint();
    }

    public void loadProperties(Block b) {
        if (b == null) {
            updateSelection(false);
            
            return;
        }
        
        blockTypeSelector.setText(b.getName());
        
        showTopFaceCheckField.setChecked(b.isTopFaceVisible());
        showRightFaceCheckField.setChecked(b.isRightFaceVisible());
        showLeftFaceCheckField.setChecked(b.isLeftFaceVisible());
        
        updateSelection(true);
        
        previewBlock.setTopFaceVisible(showTopFaceCheckField.isChecked());
        previewBlock.setRightFaceVisible(showRightFaceCheckField.isChecked());
        previewBlock.setLeftFaceVisible(showLeftFaceCheckField.isChecked());
        
        blockPreview.repaint();
    }
}
