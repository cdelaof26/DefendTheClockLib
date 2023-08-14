package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import ui.enums.GameModes;
import ui.enums.ImageButtonArrangement;
import ui.enums.LabelType;
import ui.enums.TextAlignment;
import ui.enums.UIAlignment;
import utils.AppUtilities;
import utils.UIPreferences;
import utils.WorldUtilities;

/**
 *
 * @author cristopher
 */
public class LoginWindow extends Window {
    public final UIPreferences uiPreferences = new UIPreferences(this);
    
    private final Label title = new Label(LabelType.BOLD_TITLE, "Defend The Clock");
    
    private final ImageButton backPageButton = new ImageButton(ImageButtonArrangement.ONLY_TINY_IMAGE);
    private final ImageButton nextPageButton = new ImageButton(ImageButtonArrangement.ONLY_TINY_IMAGE);
    
    private final WorldPreview [] previews = new WorldPreview[] {
        new WorldPreview(), new WorldPreview(), new WorldPreview()
    };
    
    private final Label usernameLabel = new Label(LabelType.BODY, "User");
    private final TextField usernameField = new TextField("Guest", true);
    
    private final ComboBox gamemodeSelector = new ComboBox("Gamemode", "Select gamemode", false);
    
    private final ColorButton enterButton = new ColorButton("Enter");
    private final ColorButton newWorldButton = new ColorButton("New world");
    private final ColorButton uiPreferencesButton = new ColorButton("UI Preferences");
    
    
    private File [] worlds;
    private int page = 0;
    private World selectedWorld = null;
    private GameModes mode = null;
    
    
    public LoginWindow() {
        super(700, 380, true);
        setResizable(false);
        
        
        AppUtilities.addBundleImagesToImageButton(backPageButton, "Back", 20);
        AppUtilities.addBundleImagesToImageButton(nextPageButton, "Next", 20);
        
        
        refreshWorlds();
        
        
        backPageButton.addActionListener((Action) -> {
            page--;
            if (page < 0)
                page = (worlds.length - 1) / 3;
            
            loadWorlds();
        });
        nextPageButton.addActionListener((Action) -> {
            page++;
            if (page * 3 >= worlds.length)
                page = 0;
            
            loadWorlds();
        });
        
        
        usernameField.setPreferredSize(new Dimension(265, 22));
        usernameField.setHorizontalAlignment(TextAlignment.CENTER);
        
        gamemodeSelector.setPreferredSize(new Dimension(305, 22));
        gamemodeSelector.addOption("Easy", false, (ActionListener) -> {
            mode = GameModes.EASY;
        });
        gamemodeSelector.addOption("Construction", false, (ActionListener) -> {
            mode = GameModes.CONSTRUCTION;
        });
        
        uiPreferencesButton.addActionListener((Action) -> {
            uiPreferences.toggleVisibility();
        });
        
        newWorldButton.addActionListener((Action) -> {
            String user = usernameField.getText();
            if (user.isEmpty())
                return;
            
            hideWindow();
            new GameWindow(this, null, GameModes.CONSTRUCTION, user).showWindow();
        });
        
        enterButton.setPreferredSize(new Dimension(380, 22));
        enterButton.addActionListener((Action) -> {
            String user = usernameField.getText();
            if (mode == null || selectedWorld == null || user.isEmpty())
                return;
            
            hideWindow();
            new GameWindow(this, selectedWorld, mode, user).showWindow();
        });
        
        
        
        add(title, UIAlignment.WEST, UIAlignment.WEST, 30, UIAlignment.NORTH, UIAlignment.NORTH, 30);
        
        add(backPageButton, previews[0], UIAlignment.EAST, UIAlignment.WEST, -5, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, -15);
        add(nextPageButton, previews[2], UIAlignment.WEST, UIAlignment.EAST, 5, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, -15);
        
        add(previews[0], previews[1], UIAlignment.EAST, UIAlignment.WEST, -10, UIAlignment.NORTH, UIAlignment.NORTH, 0);
        add(previews[1], UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(previews[2], previews[1], UIAlignment.WEST, UIAlignment.EAST, 10, UIAlignment.NORTH, UIAlignment.NORTH, 0);
        
        add(usernameLabel, usernameField, UIAlignment.EAST, UIAlignment.WEST, -10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(usernameField, container, enterButton, UIAlignment.EAST, UIAlignment.HORIZONTAL_CENTER, -10, UIAlignment.SOUTH, UIAlignment.NORTH, -30);
        
        add(gamemodeSelector, container, usernameField, UIAlignment.WEST, UIAlignment.HORIZONTAL_CENTER, 10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        
        add(newWorldButton, enterButton, UIAlignment.EAST, UIAlignment.WEST, -10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(enterButton, UIAlignment.EAST, UIAlignment.EAST, -30, UIAlignment.SOUTH, UIAlignment.SOUTH, -30);
        add(uiPreferencesButton, container, enterButton, UIAlignment.WEST, UIAlignment.WEST, 30, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
    }
    
    private void loadWorlds() {
        int loaded = 0;
        for (int i = page * 3; i < worlds.length && loaded < 3; i++, loaded++)
            previews[loaded].setPreview(WorldUtilities.loadWorld(worlds[i]));
        
        if (loaded != 2) {
            for (; loaded < 3; loaded++)
                previews[loaded].removePreview();
        }
    }
    
    public final void refreshWorlds() {
        worlds = WorldUtilities.WORLDS_DIRECTORY.listFiles((dir, name) -> {
            return name.endsWith(".dtcl");
        });
        
        backPageButton.setVisible(worlds.length > 3);
        nextPageButton.setVisible(worlds.length > 3);
        
        page = 0;
        
        loadWorlds();
    }
    
    private void unselectWorlds() {
        for (WorldPreview wp : previews) {
            if (wp.preview == null)
                continue;
            
            wp.preview.setBorderColor(UIProperties.DIM_TEXT_COLOR);
        }
        
        enterButton.setText("Enter");
    }
    
    private class WorldPreview extends Panel {
        private World preview = null;
        
        private final Label worldName = new Label(LabelType.SUBTITLE);

        public WorldPreview() {
            initWorldPreview();
        }
        
        private void initWorldPreview() {
            setPreferredSize(new Dimension(200, 160));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (preview == null)
                        return;
                    
                    unselectWorlds();
                    
                    preview.setBorderColor(Color.RED);
                    enterButton.setText("Enter to " + preview.getName(false));
                    selectedWorld = preview;
                }
            });
            
            this.add(worldName, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
        }

        public void removePreview() {
            if (this.preview != null)
                this.remove(this.preview);
            
            this.worldName.setText("");
            repaint();
        }
        
        public void setPreview(World preview) {
            if (this.preview != null)
                this.remove(this.preview);
            
            if (preview == null) {
                removePreview();
                this.worldName.setText("Error");
                this.preview = null;
                repaint();
                
                return;
            }
            
            this.preview = preview;
            this.preview.setGridVisible(false);
            this.preview.setBorderVisible(true);
            
            this.add(this.preview, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 0);
            
            this.preview.resizeWorld(200, 120);
            this.worldName.setText(this.preview.getName(false));
            
            repaint();
        }
    }
}
