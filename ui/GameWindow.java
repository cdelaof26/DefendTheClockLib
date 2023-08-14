package ui;

import java.awt.Dimension;
import ui.blocks.Block;
import ui.enums.GameModes;
import ui.enums.UIAlignment;

/**
 *
 * @author cristopher
 */
public class GameWindow extends Window {
    public final BuildPanel buildPanel = new BuildPanel(this);
    
    public final ColorButton exit = new ColorButton("Quit");
    
    public World world;
    
    private final GameModes gamemode;
    private final String user;
    
    
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
            buildPanel.loadWorld(world);
            
            exit.setPreferredSize(new Dimension(100, 40));
            exit.addActionListener((Action) -> {
                hideWindow();
                dispose();
                
                login.refreshWorlds();
                login.showWindow();
            });
            
            add(buildPanel, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.NORTH, 0);
            add(exit, UIAlignment.EAST, UIAlignment.EAST, -20, UIAlignment.NORTH, UIAlignment.NORTH, 20);
            add(world, buildPanel, container, UIAlignment.WEST, UIAlignment.EAST, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        } else {
            world.resizeWorld(1000, 600);
            add(world, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        }
    }
}
