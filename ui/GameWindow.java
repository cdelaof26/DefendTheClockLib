package ui;

import ui.gameui.GameUI;
import java.awt.Dimension;
import ui.blocks.Block;
import ui.enums.GameModes;
import ui.enums.UIAlignment;
import ui.gameui.EnemyRenderComponent;

/**
 *
 * @author cristopher
 */
public class GameWindow extends Window {
    public BuildPanel buildPanel;
    public final ColorButton exit = new ColorButton("Quit");
    
    public GameUI gameUI;
    
    public World world;
    public EnemyRenderComponent enemyRenderComponent;
    
    
    public final GameModes gamemode;
    public final String user;
    
    
    private final LoginWindow login;
    
    
    public GameWindow(LoginWindow login, World world, GameModes gamemode, String user) {
        super(1000, 600, true);
        setResizable(false);
        
        this.login = login;
        this.world = world;
        this.gamemode = gamemode;
        this.user = user;
        
        initGameWindow();
    }
    
    private void initGameWindow() {
        if (world == null)
            world = new World(this, 1000, 600, 50);
        else
            world.addListeners();
        
        
        world.setBorderVisible(false);
        world.setGridVisible(gamemode == GameModes.CONSTRUCTION);
        
        for (Block b : world.getBlocks())
            b.setSelectable(gamemode == GameModes.CONSTRUCTION);
        
        
        if (gamemode == GameModes.CONSTRUCTION) {
            world.resizeWorld(770, 462);
            
            buildPanel = new BuildPanel(this);
            buildPanel.loadWorld(world);
            
            world.setEnemyBlocksVisible(true);
            
            exit.setPreferredSize(new Dimension(100, 40));
            exit.addActionListener((Action) -> {
                showLoginWindow();
            });
            
            add(buildPanel, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.NORTH, 0);
            add(exit, UIAlignment.EAST, UIAlignment.EAST, -20, UIAlignment.NORTH, UIAlignment.NORTH, 20);
            add(world, buildPanel, container, UIAlignment.WEST, UIAlignment.EAST, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        } else {
            world.resizeWorld(1000, 600);
            
            gameUI = new GameUI(this);
            enemyRenderComponent = new EnemyRenderComponent(gameUI);
            
            // Debug
            world.verifyWorldPath();
            world.setEnemyBlocksVisible(false);
            
            world.removeInvisibleBlocks();
            enemyRenderComponent.setClockBlock(world.getClockBlock());
            enemyRenderComponent.setClockBlockHealthBarVisible(true);
            world.enableRenderFromImage();
            
            add(gameUI, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
            add(enemyRenderComponent, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
            add(world, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        }
    }

    public void showLoginWindow() {
        hideWindow();
        dispose();
        
        if (enemyRenderComponent != null) {
            enemyRenderComponent.despawnCubeMonsters();
            enemyRenderComponent.disassembleAllTurrets();
        }
        
        world.dispose();
        
        System.gc();
        Runtime.getRuntime().freeMemory();

        login.refreshWorlds();
        login.showWindow();
    }
}
