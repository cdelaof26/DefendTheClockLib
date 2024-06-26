package ui.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import ui.UIProperties;

/**
 *
 * @author cristopher
 */
public class HealthBar {
    protected double x, y;
    
    protected int maximumValue;
    protected int value;

    protected int width = 60, height = 8;
    protected int halfWidth, halfHeight;
    
    protected Rectangle paintArea;
    
    public HealthBar(int maximumValue) {
        this.maximumValue = maximumValue;
        this.value = this.maximumValue;
        
        scale();
    }
    
    public void paintBar(Graphics2D g2D, double x, double y) {
        this.x = x - halfWidth;
        this.y = y - height - 3;
        
        g2D.setColor(Color.BLACK);
        
        paintArea = new Rectangle((int) x - halfWidth, (int) y - height - 3, width, height);
        g2D.fill(new Rectangle2D.Double(this.x, this.y, width, height));
        
        g2D.setColor(Color.RED);
        g2D.fill(new Rectangle2D.Double(x - halfWidth + 2, y - height - 1, width - 4, height - 4));
        
        g2D.setColor(Color.GREEN);
        g2D.fill(new Rectangle2D.Double(x - halfWidth + 2, y - height - 1, value * (width - 4) / maximumValue, height - 4));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setValue(int value) {
        if (value < 0 || value > maximumValue)
            throw new IllegalArgumentException("Health value is outside the range [0, " + maximumValue + "]");
        
        this.value = value;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }
    
    public void decreaseValue() {
        if (value < 1)
            return;
        
        value--;
    }
    
    public void decreaseValue(int decrease) {
        if (value - decrease < 1) {
            value = 0;
            return;
        }
        
        value -= decrease;
    }
    
    public boolean isAlive() {
        return value > 0;
    }

    public Rectangle getPaintArea() {
        return paintArea;
    }
    
    private void scale() {
        width *= UIProperties.getUiScale();
        height *= UIProperties.getUiScale();
        halfWidth = width / 2;
        halfHeight = height / 2;
    }

    public void makeSmall() {
        width = 50; height = 8;
        scale();
    }
}
