package ui.gameui;

import java.awt.Dimension;
import ui.ColorButton;
import ui.Label;
import ui.Panel;
import ui.enums.LabelType;
import ui.enums.UIAlignment;

/**
 *
 * @author cristopher
 */
public class PauseMenu extends Panel {
    private final Label menuTitle = new Label(LabelType.BOLD_TITLE, "Pause");
    private final ColorButton resumeButton = new ColorButton("Resume");
    private final ColorButton restartButton = new ColorButton("Restart");
    private final ColorButton goBackButton = new ColorButton("Main menu");
    private final ColorButton exitButton = new ColorButton("Quit");

    
    private final GameUI container;
    
    
    public PauseMenu(GameUI container) {
        super(200, 190);
        
        this.container = container;
        
        initPauseMenu();
    }

    private void initPauseMenu() {
        setVisible(false);
        
        resumeButton.setPreferredSize(new Dimension(180, 44));
        resumeButton.addActionListener((Action) -> {
            container.togglePause();
        });
        
        restartButton.setPreferredSize(new Dimension(180, 44));
        goBackButton.setPreferredSize(new Dimension(100, 22));
        goBackButton.addActionListener((Action) -> {
            container.mainWindow.showLoginWindow();
        });
        
        exitButton.setPreferredSize(new Dimension(70, 22));
        exitButton.addActionListener((Action) -> {
            System.exit(0);
        });

        
        add(menuTitle, this, this, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.NORTH, 10);
        add(resumeButton, this, menuTitle, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(restartButton, this, resumeButton, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(goBackButton, this, this, UIAlignment.EAST, UIAlignment.EAST, -10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
        add(exitButton, this, this, UIAlignment.WEST, UIAlignment.WEST, 10, UIAlignment.SOUTH, UIAlignment.SOUTH, -10);
    }
    
    public void setGameOverLook(int score) {
        menuTitle.setText("Game over");
        resumeButton.setText("Level " + score);
        resumeButton.setEnabled(false);
    }
    
    public void resetLook() {
        menuTitle.setText("Pause");
        resumeButton.setText("Resume");
        resumeButton.setEnabled(true);
    }
}
