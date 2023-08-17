package ui.construction;

import ui.BuildPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import ui.CheckField;
import ui.ColorButton;
import ui.ColorPicker;
import ui.ComboBox;
import ui.Label;
import ui.Panel;
import ui.TextField;
import ui.UIProperties;
import ui.enums.LabelType;
import ui.enums.TextAlignment;
import ui.enums.UIAlignment;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class WorldPropertiesPanel extends Panel {
    public final TextField title = new TextField("Untitled world", true);
    
    private final Label colorSubtitle = new Label(LabelType.SUBTITLE, "Color");
    public final ComboBox fillStateSelector = new ComboBox("Fill", "Auto", false);
    
    private final JComponent fillColorUpdater = new JComponent() {
        @Override
        public void setBackground(Color bg) {
            fillStateSelector.setText("Solid color");
            container.setWorldBGMode("Color");
            container.setWorldBGColor(bg);
        }
    };
    public final ColorPicker fillColorPicker = new ColorPicker(fillColorUpdater);
    
    private final CheckField showGridCheckField = new CheckField("Show grid", false, true);
    
    private final ColorButton saveWorldButton = new ColorButton("Save world");
    
    
    private boolean confirmOverwrite = false;
    
    
    private final BuildPanel container;
    
    public WorldPropertiesPanel(BuildPanel container) {
        super(230, 520);
        
        this.container = container;
        
        initWorldPropertiesPanel();
    }

    private void initWorldPropertiesPanel() {
        title.setPreferredSize(new Dimension(210, LibUtilities.getTextDimensions("Untitled world", UIProperties.APP_BOLD_TITLE_FONT).height));
        title.setHorizontalAlignment(TextAlignment.CENTER);
        title.setVisibleBackground(false);
        title.setFontType(LabelType.BOLD_TITLE);
        title.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                container.setWorldName(title.getText());
            }
        });
        
        fillStateSelector.setPreferredSize(new Dimension(190, 22));
        fillStateSelector.addOption("Auto", false, (Action) -> {
            container.setWorldBGMode("Auto");
            container.setWorldBGColor(UIProperties.APP_BG);
        });
        fillStateSelector.addOption("Solid color", false, (Action) -> {
            container.setWorldBGMode("Color");
            fillColorUpdater.setBackground(fillColorPicker.getSelectedColor());
        });
        
        showGridCheckField.setPreferredSize(new Dimension(190, 22));
        showGridCheckField.addActionListener((Action) -> {
            container.setWorldGridVisible(!showGridCheckField.isChecked());
        });
        
        saveWorldButton.setPreferredSize(new Dimension(170, 22));
        saveWorldButton.addActionListener((Action) -> {
            boolean worldExists = container.worldExist();
            
            if (confirmOverwrite || !worldExists) {
                confirmOverwrite = false;
                
                new Thread(() -> {
                    int time = 2;
                    while (time > -1) {
                        try { Thread.sleep(1000); } catch (InterruptedException ex) { }
                        time--;
                    }
                    
                    SwingUtilities.invokeLater(() -> {
                        saveWorldButton.setText("Save world");
                    });
                }).start();
                
                saveWorldButton.setText(container.saveWorld() ? "World saved!" : "FAILED");
                return;
            }
            
            confirmOverwrite = true;
            
            new Thread(() -> {
                int time = 3;
                while (time > -1) {
                    if (!confirmOverwrite)
                        return;
                    
                    final int t = time;
                    try {
                        SwingUtilities.invokeAndWait(() -> {
                            saveWorldButton.setText("Overwrite? (" + t + "s)");
                        });
                    } catch (InterruptedException | InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                    
                    try { Thread.sleep(1000); } catch (InterruptedException ex) { }
                    time--;
                }
                
                confirmOverwrite = false;
                SwingUtilities.invokeLater(() -> {
                    saveWorldButton.setText("Save world");
                });
            }).start();
        });
        
        
        add(title, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 0);
        
        add(colorSubtitle, this, title, UIAlignment.WEST, UIAlignment.WEST, 20, UIAlignment.NORTH, UIAlignment.SOUTH, 20);
        add(fillStateSelector, this, colorSubtitle, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(fillColorPicker, this, fillStateSelector, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(showGridCheckField, this, fillColorPicker, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(saveWorldButton, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.SOUTH, UIAlignment.SOUTH, -20);
    }
}
