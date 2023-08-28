package ui.gameui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ui.ColorButton;
import ui.ImageButton;
import ui.Label;
import ui.Panel;
import ui.UIProperties;
import ui.enums.ImageButtonArrangement;
import ui.enums.LabelType;
import ui.enums.UIAlignment;
import ui.turrets.BasicTurret;
import ui.turrets.Turrets;
import utils.AppUtilities;

/**
 *
 * @author cristopher
 */
public class NewTurretMenu extends Panel {
    private final Label title = new Label(LabelType.BOLD_TITLE, "New turret");
    
    private final ImageButton budgetLabel = new ImageButton("$ 1000", false, ImageButtonArrangement.F_RIGHT_TEXT_LEFT_IMAGE);
    
    private final BasicTurret basicTurret = new BasicTurret(1.9, 2.6, 70);
    
    private final ColorButton basicTurretButton = new ColorButton("Built Turret");
    private final ColorButton lazerTurretButton = new ColorButton("Built Lazer");
    private final ColorButton cannonTurretButton = new ColorButton("Built Cannon");
    private final ColorButton machineGunTurretButton = new ColorButton("Built Machine gun");
    
    private final Label basicTurretPrice = new Label(LabelType.BODY);
    private final Label lazerTurretPrice = new Label(LabelType.BODY);
    private final Label cannonTurretPrice = new Label(LabelType.BODY);
    private final Label machineGunTurretPrice = new Label(LabelType.BODY);
    
    private final Label transactionStatus = new Label(LabelType.WARNING_LABEL, "You don't have enough dollars to complete the transaction");
    
    private final ImageButton closeButton = new ImageButton(ImageButtonArrangement.ONLY_TINY_IMAGE);
    
    
    private int turretPrice = 200;
    private int lazerPrice = 500;
    private int cannonPrice = 1000;
    private int machineGunPrice = 2000;
    
    private double xGrid, yGrid;
    
    
    private final GameUI container;
    
    public NewTurretMenu(GameUI container) {
        super(590, 260);
        
        this.container = container;
        
        initNewTurretMenu();
    }

    private void initNewTurretMenu() {
        setVisible(false);

        budgetLabel.setPreferredSize(new Dimension(140, 40));
        budgetLabel.setLabelType(LabelType.SUBTITLE);
        budgetLabel.setEnabled(false);
        budgetLabel.setLightThemedImage("ui/assets/Budget.png", true, 30, 30);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                basicTurret.lookAt(e.getX() - 35 * UIProperties.getUiScale(), e.getY());
                repaint();
            }
        });
        
        basicTurret.stopShootMechanism();

        basicTurretButton.addActionListener((Action) -> {
            boolean transactionCompleted = container.stats.chargeDollars(turretPrice);
            if (transactionCompleted) {
                container.updateGoldenDollarsAmount();
                container.showLabels();
                container.mainWindow.enemyRenderComponent.createTurret(Turrets.BASIC_TURRET, xGrid, yGrid);
                
                setVisible(false);
            } else
                transactionStatus.setVisible(true);
        });
        
        lazerTurretButton.setEnabled(false);
        cannonTurretButton.setEnabled(false);
        machineGunTurretButton.setEnabled(false);
        
        closeButton.setPaint(false);
        closeButton.addActionListener((Action) -> {
            container.showLabels();
            setVisible(false);
        });
        
        transactionStatus.setVisible(false);
        
        
        AppUtilities.addBundleImagesToImageButton(closeButton, "Delete", 20);
        
        
        add(title, this, this, UIAlignment.WEST, UIAlignment.WEST, 30, UIAlignment.NORTH, UIAlignment.NORTH, 30);
        
        add(budgetLabel, closeButton, closeButton, UIAlignment.EAST, UIAlignment.WEST, -20, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);

        add(basicTurretButton, this, title, UIAlignment.WEST, UIAlignment.WEST, 40, UIAlignment.NORTH, UIAlignment.SOUTH, 140);
        add(lazerTurretButton, basicTurretButton, basicTurretButton, UIAlignment.WEST, UIAlignment.EAST, 10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(cannonTurretButton, lazerTurretButton, lazerTurretButton, UIAlignment.WEST, UIAlignment.EAST, 10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(machineGunTurretButton, cannonTurretButton, cannonTurretButton, UIAlignment.WEST, UIAlignment.EAST, 10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        
        add(basicTurretPrice, basicTurretButton, basicTurretButton, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 5);
        add(lazerTurretPrice, lazerTurretButton, lazerTurretButton, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 5);
        add(cannonTurretPrice, cannonTurretButton, cannonTurretButton, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 5);
        add(machineGunTurretPrice, machineGunTurretButton, machineGunTurretButton, UIAlignment.HORIZONTAL_CENTER, UIAlignment.HORIZONTAL_CENTER, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 5);
        
        add(closeButton, this, this, UIAlignment.EAST, UIAlignment.EAST, -30, UIAlignment.NORTH, UIAlignment.NORTH, 30);
        
        add(transactionStatus, title, this, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.NORTH, 10);
    }

    @Override
    public void updateUISize() {
        super.updateUISize();
        if (basicTurret != null) {
            basicTurret.setDiagonalLength(70 * UIProperties.getUiScale());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        basicTurret.paintBlock(g2D);
    }
    
    public void updatePrices() {
        turretPrice = container.stats.getTurretPrice();
        lazerPrice = container.stats.getLazerPrice();
        cannonPrice = container.stats.getCannonPrice();
        machineGunPrice = container.stats.getMachineGunPrice();
        
        basicTurretPrice.setText("$ " + turretPrice);
        lazerTurretPrice.setText("$ " + lazerPrice);
        cannonTurretPrice.setText("$ " + cannonPrice);
        machineGunTurretPrice.setText("$ " + machineGunPrice);
    }
    
    public void setBudgetLabelText(String text) {
        budgetLabel.setText(text);
    }

    public void setxGrid(double xGrid) {
        this.xGrid = xGrid;
    }

    public void setyGrid(double yGrid) {
        this.yGrid = yGrid;
    }
}
