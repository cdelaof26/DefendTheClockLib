package ui.gameui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import ui.ColorButton;
import ui.GameWindow;
import ui.ImageButton;
import ui.Label;
import ui.Panel;
import ui.enums.ImageButtonArrangement;
import ui.enums.LabelType;
import ui.enums.UIAlignment;
import utils.AppUtilities;

/**
 *
 * @author cristopher
 */
public class GameUI extends Panel {
    private final ImageButton pauseButton = new ImageButton(ImageButtonArrangement.ONLY_IMAGE);
    private final ImageButton budgetLabel = new ImageButton("1000 $", false, ImageButtonArrangement.F_RIGHT_TEXT_LEFT_IMAGE);
    private final ImageButton starsLabel = new ImageButton("0 stars", false, ImageButtonArrangement.F_RIGHT_TEXT_LEFT_IMAGE);
    private final ImageButton nextRoundButton = new ImageButton("Next round", false, ImageButtonArrangement.F_LEFT_TEXT_LEFT_IMAGE);
    
    private final Panel menu = new Panel(200, 190);
    private final Label menuTitle = new Label(LabelType.BOLD_TITLE, "Pause");
    private final ColorButton resumeButton = new ColorButton("Resume");
    private final ColorButton restartButton = new ColorButton("Restart");
    private final ColorButton goBackButton = new ColorButton("Main menu");
    private final ColorButton exitButton = new ColorButton("Quit");
    
    
    private boolean paused = false;
    
    
    private final GameWindow mainWindow;
    
    
    public GameUI(GameWindow mainWindow) {
        super(1000, 600);
        
        this.mainWindow = mainWindow;
        
        initPausePanel();
    }

    private void initPausePanel() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        
        pauseButton.addActionListener((Action) -> {
            togglePause();
        });
        
        budgetLabel.setPreferredSize(new Dimension(180, 70));
        budgetLabel.setLabelType(LabelType.BOLD_TITLE);
        budgetLabel.setEnabled(false);
        budgetLabel.setLightThemedImage("ui/assets/Budget.png", true, 50, 50);
        
        starsLabel.setPreferredSize(new Dimension(180, 70));
        starsLabel.setLabelType(LabelType.BOLD_TITLE);
        starsLabel.setLightThemedImage("ui/assets/Star.png", true, 50, 50);

        nextRoundButton.setPreferredSize(new Dimension(180, 70));
        nextRoundButton.setLabelType(LabelType.SUBTITLE);
        nextRoundButton.addActionListener((Action) -> {
            mainWindow.enemyRenderComponent.createNewCubeMonster(mainWindow.world.getEnemySpawnPoint(), mainWindow.world.getGridLength());
            
        });
//        nextRoundButton.setVisible(false);


        
        resumeButton.setPreferredSize(new Dimension(180, 44));
        resumeButton.addActionListener((Action) -> {
            togglePause();
        });
        
        restartButton.setPreferredSize(new Dimension(180, 44));
        goBackButton.setPreferredSize(new Dimension(100, 22));
        goBackButton.addActionListener((Action) -> {
            mainWindow.showLoginWindow();
        });
        
        exitButton.setPreferredSize(new Dimension(70, 22));
        exitButton.addActionListener((Action) -> {
            System.exit(0);
        });

        menu.setVisible(false);
        menu.add(menuTitle, menu, menu, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        menu.add(resumeButton, menu, menuTitle, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        menu.add(restartButton, menu, resumeButton, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        menu.add(goBackButton, menu, menu, UIAlignment.EAST, UIAlignment.EAST, -10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
        menu.add(exitButton, menu, menu, UIAlignment.WEST, UIAlignment.WEST, 10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);


        AppUtilities.addBundleImagesToImageButton(pauseButton, "Pause", 35);
        AppUtilities.addBundleImagesToImageButton(nextRoundButton, "Next", 40);


        add(pauseButton, this, this, UIAlignment.WEST, UIAlignment.WEST, 10, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(starsLabel, this, this, UIAlignment.EAST, UIAlignment.EAST, -10, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(budgetLabel, starsLabel, this, UIAlignment.EAST, UIAlignment.WEST, -10, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(nextRoundButton, this, this, UIAlignment.EAST, UIAlignment.EAST, -10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
        
        add(menu, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        
        super.paintComponent(g);
    }
    
    private void togglePause() {
        paused = !paused;
            
        menu.setVisible(paused);
        budgetLabel.setVisible(!paused);
        starsLabel.setVisible(!paused);
        pauseButton.setVisible(!paused);
        nextRoundButton.setVisible(!paused);
        setBackground(new Color(0, 0, 0, paused ? 120 : 0));
        repaint();
    }

    public boolean isPaused() {
        return paused;
    }
}
