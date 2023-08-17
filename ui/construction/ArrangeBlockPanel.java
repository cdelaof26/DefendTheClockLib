package ui.construction;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.Timer;
import ui.BuildPanel;
import ui.ImageButton;
import ui.Label;
import ui.NumberSelector;
import ui.Panel;
import ui.blocks.Block;
import ui.enums.ImageButtonArrangement;
import ui.enums.LabelType;
import ui.enums.UIAlignment;
import utils.AppUtilities;

/**
 *
 * @author cristopher
 */
public class ArrangeBlockPanel extends Panel {
    private final Label title = new Label(LabelType.BOLD_TITLE, "Arrange");
    
    private final Label coordinatesSubtitle = new Label(LabelType.SUBTITLE, "Coordinates");
    
    private boolean loadingBlock = false;
    private final JLabel coordUpdater = new JLabel() {
        @Override
        public void setText(String text) {
            if (text.isEmpty() || loadingBlock)
                return;
            
            container.moveSelectedBlock(xCoordinateSelector.getValue(), yCoordinateSelector.getValue());
        }
    };
    public final NumberSelector xCoordinateSelector = new NumberSelector("x", 1, 1, 1000, 1, coordUpdater);
    public final NumberSelector yCoordinateSelector = new NumberSelector("y", 1, 1, 1000, 1, coordUpdater);
    
    private final Label layerPositionLabel = new Label(LabelType.SUBTITLE, "Layer");
    private final ImageButton moveForwardButton = new ImageButton("Forward", false, ImageButtonArrangement.UP_IMAGE);
    private final ImageButton moveBackwardButton = new ImageButton("Backward", false, ImageButtonArrangement.UP_IMAGE);
    private final ImageButton sentToFrontButton = new ImageButton("Front", false, ImageButtonArrangement.UP_IMAGE);
    private final ImageButton sentToBackButton = new ImageButton("Back", false, ImageButtonArrangement.UP_IMAGE);
    
    
    private boolean pressed = false;
    private boolean increaseValue;
    private final Timer fastValueModificator = new Timer(100, (Action) -> {
        modValue();
    });
    
    
    
    private final BuildPanel container;
    
    
    public ArrangeBlockPanel(BuildPanel container) {
        super(230, 520);
        
        this.container = container;
        
        initArrangeBlockPanel();
    }

    private void initArrangeBlockPanel() {
        xCoordinateSelector.setPreferredSize_(new Dimension(190, 22));
        yCoordinateSelector.setPreferredSize_(new Dimension(190, 22));
        
        
        
//        moveForwardButton.addActionListener((Action) -> {
//            container.moveSelectedBlockALayer(true);
//        });
//        moveBackwardButton.addActionListener((Action) -> {
//            container.moveSelectedBlockALayer(false);
//        });

        moveForwardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                increaseValue = true;
                
                modValue();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                fastValueModificator.stop();
            }
        });
        
        moveBackwardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                increaseValue = false;
                
                modValue();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                fastValueModificator.stop();
            }
        });
        sentToFrontButton.addActionListener((Action) -> {
            container.moveSelectedBlockTo(true);
        });
        sentToBackButton.addActionListener((Action) -> {
            container.moveSelectedBlockTo(false);
        });
        
        AppUtilities.addBundleImagesToImageButton(moveForwardButton, "ForwardStack", 20);
        AppUtilities.addBundleImagesToImageButton(moveBackwardButton, "BackwardStack", 20);
        AppUtilities.addBundleImagesToImageButton(sentToFrontButton, "FrontStack", 20);
        AppUtilities.addBundleImagesToImageButton(sentToBackButton, "BackStack", 20);
        
        
        add(title, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 0);

        add(coordinatesSubtitle, this, title, UIAlignment.WEST, UIAlignment.WEST, 20, UIAlignment.NORTH, UIAlignment.SOUTH, 20);
        add(xCoordinateSelector, this, coordinatesSubtitle, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(yCoordinateSelector, this, xCoordinateSelector, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(layerPositionLabel, coordinatesSubtitle, yCoordinateSelector, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 20);
        add(moveForwardButton, layerPositionLabel, layerPositionLabel, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(moveBackwardButton, this, moveForwardButton, UIAlignment.EAST, UIAlignment.EAST, -20, UIAlignment.NORTH, UIAlignment.NORTH, 0);
        add(sentToFrontButton, layerPositionLabel, moveForwardButton, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(sentToBackButton, moveBackwardButton, sentToFrontButton, UIAlignment.EAST, UIAlignment.EAST, 0, UIAlignment.NORTH, UIAlignment.NORTH, 0);
    }

    private void modValue() {
        if (!pressed) {
            fastValueModificator.stop();
            return;
        } else if (!fastValueModificator.isRunning()) {
            fastValueModificator.start();
        }
        
        container.moveSelectedBlockALayer(increaseValue);
    }
    
    public void loadProperties(Block b) {
        if (b == null) {
            xCoordinateSelector.setValue(1);
            yCoordinateSelector.setValue(1);
            
            return;
        }
        
        loadingBlock = true;
        xCoordinateSelector.setValue((int) b.getXGrid());
        yCoordinateSelector.setValue((int) b.getYGrid());
        loadingBlock = false;
    }
}
