package ui.gameui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
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
public class StarsMenu extends Panel {
    private final Label title = new Label(LabelType.BOLD_TITLE, "Exchange stars");
    private final Label descriptionLabel = new Label(LabelType.BODY, 
            "<html>Stars are a rare item dropped by monsters, "
            + "you can exchange them <br>for golden dollars to buy more weapons</html>"
    );
    
    private final ImageButton offer1 = new ImageButton("1 star = $1000", false, ImageButtonArrangement.UP_XL_IMAGE);
    private final ImageButton offer2 = new ImageButton("2 stars = $2500", false, ImageButtonArrangement.UP_XL_IMAGE);
    private final ImageButton offer3 = new ImageButton("3 stars = $4000", false, ImageButtonArrangement.UP_XL_IMAGE);
    
    private final Label budgetSubtitle = new Label(LabelType.SUBTITLE, "Your funds");
    private final ImageButton budgetLabel = new ImageButton("$ 1000", false, ImageButtonArrangement.F_RIGHT_TEXT_LEFT_IMAGE);
    private final ImageButton starsLabel = new ImageButton("0 stars", false, ImageButtonArrangement.F_RIGHT_TEXT_LEFT_IMAGE);
    
    private final ImageButton closeButton = new ImageButton(ImageButtonArrangement.ONLY_TINY_IMAGE);
    
    private final Label transactionStatus = new Label(LabelType.WARNING_LABEL, "You don't have enough stars to complete the transaction");
    
    
    
    private final GameUI container;

    
    public StarsMenu(GameUI container) {
        super(640, 280);
        
        this.container = container;
        
        initStarsMenu();
    }

    private void initStarsMenu() {
        setVisible(false);

        ActionListener action = (Action) -> {
            transactionStatus.setVisible(true);
        };

        offer1.setLightThemedImage("ui/assets/Budget.png", true, 40, 40);
        offer1.addActionListener(action);
        
        offer2.setLightThemedImage("ui/assets/Budget.png", true, 40, 40);
        offer2.addActionListener(action);
        
        offer3.setLightThemedImage("ui/assets/Budget.png", true, 40, 40);
        offer3.addActionListener(action);

        closeButton.setPaint(false);
        closeButton.addActionListener((Action) -> {
            container.toggleStarsMenuVisibility();
        });
        
        
        budgetLabel.setPreferredSize(new Dimension(140, 40));
        budgetLabel.setLabelType(LabelType.SUBTITLE);
        budgetLabel.setEnabled(false);
        budgetLabel.setLightThemedImage("ui/assets/Budget.png", true, 30, 30);
        
        starsLabel.setPreferredSize(new Dimension(140, 40));
        starsLabel.setLabelType(LabelType.SUBTITLE);
        starsLabel.setEnabled(false);
        starsLabel.setLightThemedImage("ui/assets/Star.png", true, 30, 30);
        
        
        transactionStatus.setVisible(false);
        
        
        AppUtilities.addBundleImagesToImageButton(closeButton, "Delete", 20);
        
        
        add(title, this, this, UIAlignment.WEST, UIAlignment.WEST, 30, UIAlignment.NORTH, UIAlignment.NORTH, 30);
        add(descriptionLabel, this, title, UIAlignment.WEST, UIAlignment.WEST, 30, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(offer1, this, descriptionLabel, UIAlignment.WEST, UIAlignment.WEST, 40, UIAlignment.NORTH, UIAlignment.SOUTH, 40);
        add(offer2, offer1, offer1, UIAlignment.WEST, UIAlignment.EAST, 10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        add(offer3, offer2, offer2, UIAlignment.WEST, UIAlignment.EAST, 10, UIAlignment.VERTICAL_CENTER, UIAlignment.VERTICAL_CENTER, 0);
        
        add(budgetSubtitle, closeButton, closeButton, UIAlignment.EAST, UIAlignment.EAST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 20);
        
        add(budgetLabel, budgetSubtitle, budgetSubtitle, UIAlignment.EAST, UIAlignment.EAST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        add(starsLabel, budgetLabel, budgetLabel, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.NORTH, UIAlignment.SOUTH, 10);
        
        add(transactionStatus, title, this, UIAlignment.WEST, UIAlignment.WEST, 0, UIAlignment.SOUTH, UIAlignment.SOUTH, -15);
        
        add(closeButton, this, this, UIAlignment.EAST, UIAlignment.EAST, -30, UIAlignment.NORTH, UIAlignment.NORTH, 30);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        
        transactionStatus.setVisible(!aFlag);
    }
    
    public void setBudgetLabelText(String text) {
        budgetLabel.setText(text);
    }
}
