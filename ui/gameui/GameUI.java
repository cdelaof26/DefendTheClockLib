package ui.gameui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Timer;
import ui.GameWindow;
import ui.ImageButton;
import ui.Panel;
import ui.enums.ImageButtonArrangement;
import ui.enums.LabelType;
import ui.enums.UIAlignment;
import utils.AppUtilities;
import utils.GameState;

/**
 *
 * @author cristopher
 */
public class GameUI extends Panel {
    private final ImageButton pauseButton = new ImageButton(ImageButtonArrangement.ONLY_IMAGE);
    private final ImageButton budgetLabel = new ImageButton("$ 1000", false, ImageButtonArrangement.F_RIGHT_TEXT_LEFT_IMAGE);
    private final ImageButton starsLabel = new ImageButton("0 stars", false, ImageButtonArrangement.F_RIGHT_TEXT_LEFT_IMAGE);
    private final ImageButton levelLabel = new ImageButton("Round 1", false, ImageButtonArrangement.F_CENTER_TEXT_LEFT_IMAGE);
    
    private final ImageButton nextRoundButton = new ImageButton("Next round", false, ImageButtonArrangement.F_LEFT_TEXT_LEFT_IMAGE);
    
    private final PauseMenu pauseMenu = new PauseMenu(this);
    public final StarsMenu starsMenu = new StarsMenu(this);
    public final NewTurretMenu turretsMenu = new NewTurretMenu(this);
    
    
    private boolean nextRoundButtonIsVisible = true;
    
    
    public GameState stats = new GameState(this);
    private boolean paused = false;
    
    
    private final Timer nextRoundButtonUpdater;
    
    
    public final GameWindow mainWindow;
    
    
    public GameUI(GameWindow mainWindow) {
        super(1000, 600);
        
        this.mainWindow = mainWindow;
        this.nextRoundButtonUpdater = new Timer(3000, (Action) -> {
            if (mainWindow.enemyRenderComponent.areAllEnemiesDead())
                levelUp();
        });
        
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
        budgetLabel.setLightThemedImage("ui/assets/Budget.png", true, 50, 50);
        budgetLabel.addActionListener((Action) -> {
            toggleStarsMenuVisibility();
        });
        
        starsLabel.setPreferredSize(new Dimension(180, 70));
        starsLabel.setLabelType(LabelType.BOLD_TITLE);
        starsLabel.setLightThemedImage("ui/assets/Star.png", true, 50, 50);
        starsLabel.addActionListener((Action) -> {
            toggleStarsMenuVisibility();
        });
        
        levelLabel.setPreferredSize(new Dimension(150, 70));
        levelLabel.setLabelType(LabelType.BOLD_TITLE);
        levelLabel.setEnabled(false);

        nextRoundButton.setPreferredSize(new Dimension(180, 70));
        nextRoundButton.setLabelType(LabelType.SUBTITLE);
        nextRoundButton.addActionListener((Action) -> {
            nextRoundButton.setVisible(false);
            nextRoundButtonIsVisible = false;
            
            mainWindow.enemyRenderComponent.generateMonsterHorde(stats.getMonsterAmount(), stats.getMonsterHealth(), mainWindow.world.getEnemySpawnPoint());
            nextRoundButtonUpdater.start();
        });
        
        turretsMenu.updatePrices();


        AppUtilities.addBundleImagesToImageButton(pauseButton, "Pause", 35);
        AppUtilities.addBundleImagesToImageButton(nextRoundButton, "Next", 40);


        add(pauseMenu, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(starsMenu, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(turretsMenu, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        
        add(pauseButton, this, this, UIAlignment.WEST, UIAlignment.WEST, 10, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(starsLabel, this, this, UIAlignment.EAST, UIAlignment.EAST, -10, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(budgetLabel, starsLabel, this, UIAlignment.EAST, UIAlignment.WEST, -10, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(levelLabel, this, this, UIAlignment.WEST, UIAlignment.WEST, 10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
        add(nextRoundButton, this, this, UIAlignment.EAST, UIAlignment.EAST, -10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        
        super.paintComponent(g);
    }
    
    private void toggleUIVisibility() {
        budgetLabel.setVisible(!paused);
        starsLabel.setVisible(!paused);
        levelLabel.setVisible(!paused);
        pauseButton.setVisible(!paused);
        turretsMenu.setVisible(false);
        nextRoundButton.setVisible(nextRoundButtonIsVisible ? !paused : false);
        
        setBackground(new Color(0, 0, 0, paused ? 120 : 0));
        repaint();
    }
    
    public void togglePause() {
        paused = !paused;
        pauseMenu.setVisible(paused);
        
        toggleUIVisibility();
    }
    
    public void toggleStarsMenuVisibility() {
        paused = !paused;
        starsMenu.setVisible(paused);
        
        toggleUIVisibility();
    }

    public boolean isPaused() {
        return paused;
    }

    public void resetProgress() {
        stats = new GameState(this);
        
        levelLabel.setText("Round " + stats.getLevel());
        updateGoldenDollarsAmount();
        
        nextRoundButton.setVisible(true);
        nextRoundButtonIsVisible = true;
        
        nextRoundButtonUpdater.stop();
        
        mainWindow.enemyRenderComponent.setClockHealth(stats.getClockHealth());
        mainWindow.enemyRenderComponent.updateNumberOfSteps(0);
        mainWindow.enemyRenderComponent.despawnCubeMonsters();
        mainWindow.enemyRenderComponent.disassembleAllTurrets();
        
        togglePause();
    }
    
    private void levelUp() {
        stats.levelUp();
        
        levelLabel.setText("Round " + stats.getLevel());
        updateGoldenDollarsAmount();
        
        nextRoundButton.setVisible(true);
        nextRoundButtonIsVisible = true;
        
        nextRoundButtonUpdater.stop();
    }
    
    public void showLabels() {
        budgetLabel.setVisible(true);
        starsLabel.setVisible(true);
    }
    
    public void hideLabels() {
        budgetLabel.setVisible(false);
        starsLabel.setVisible(false);
    }
    
    public void showTurretsMenu() {
        hideLabels();
        turretsMenu.setVisible(true);
    }
    
    public void updateGoldenDollarsAmount() {
        budgetLabel.setText("$ " + stats.getGoldenDollars());
        starsMenu.setBudgetLabelText("$ " + stats.getGoldenDollars());
        turretsMenu.setBudgetLabelText("$ " + stats.getGoldenDollars());
    }
    
    public void decreaseClockHealth() {
        mainWindow.enemyRenderComponent.setClockHealth(stats.decreaseClockHealth());
        
        if (stats.getClockHealth() == 0) {
            paused = true;
            
            pauseMenu.setGameOverLook(stats.getLevel());
            pauseMenu.setVisible(paused);
            
            toggleUIVisibility();
        }
    }
}
