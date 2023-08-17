package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JComponent;
import ui.blocks.Block;
import ui.blocks.ClockBlock;
import ui.blocks.EnemyPathBlock;
import ui.blocks.EnemySpawnBlock;
import ui.mouselisteners.BlockDragger;
import ui.mouselisteners.BlockSelector;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class World extends JComponent implements ComponentSetup {
    public enum WorldProperties {
        NAME, WIDTH_IN_SQUARES, HEIGHT_IN_SQUARES, BGMODE, BGCOLOR, BLOCKS
    }
    
    protected String name = "Untitled world";
    protected String bgmode = "Auto";
    
    protected int width = -1;
    protected int height = -1;
    protected double gridLength = -1;
    protected double gridHalfLength = -1;
    
    protected final double widthInSquares;
    protected final double heightInSquares;
    
    private Color backgroundColor = UIProperties.APP_BG;
    
    private boolean gridVisible = true;
    private boolean borderVisible = false;
    private Color borderColor = UIProperties.DIM_TEXT_COLOR;
    
    public final BlockSelector blockSelector = new BlockSelector(this);
    public final BlockDragger blockDragger = new BlockDragger(this);
    
    private ArrayList<Block> blocks = new ArrayList<>();
    
    protected boolean allPathBlockTogether = false;
    protected boolean oneSpawnPathBlockFound = false;
    protected boolean oneClockBlockFound = false;
    
    protected Point2D enemySpawnPoint = null;
    
    private LinkedList<Point2D> enemyPathCoordinates = new LinkedList<>();
    
    
    public GameWindow mainWindow = null;

    public World(double widthInSquares, double heightInSquares) {
        this.widthInSquares = widthInSquares;
        this.heightInSquares = heightInSquares;
    }
    
    public World(GameWindow mainWindow, int width, int height, double gridLength) {
        if (gridLength < 0)
            throw new IllegalArgumentException("Negative gridLength");
        
        this.mainWindow = mainWindow;
        this.width = width;
        this.height = height;
        this.gridLength = gridLength;
        this.gridHalfLength = this.gridLength / 2d;
        
        widthInSquares = this.width / this.gridLength;
        heightInSquares = this.height / this.gridLength;
        
        setPreferredSize(new Dimension(this.width, this.height));
        
        addListeners();
    }
    
    @Override
    public void initUI() { }

    @Override
    public void updateUISize() {
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void updateUIFont() { }

    @Override
    public void updateUITheme() {
        if (bgmode.equalsIgnoreCase("Auto"))
            this.backgroundColor = UIProperties.APP_BG;
    }

    @Override
    public void updateUIColors() {
        if (bgmode.equalsIgnoreCase("Auto"))
            this.backgroundColor = UIProperties.APP_BG;
    }

    @Override
    public void setUseAppTheme(boolean useAppTheme) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUseAppColor(boolean useAppColor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setRoundCorners(boolean roundCorners) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPaintBorder(boolean paintBorder) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final void setPreferredSize(Dimension preferredSize) {
        this.width = preferredSize.width;
        this.height = preferredSize.height;
        
        preferredSize.width = (int) (preferredSize.width * UIProperties.uiScale);
        preferredSize.height = (int) (preferredSize.height * UIProperties.uiScale);
        
        super.setPreferredSize(preferredSize);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2D.setColor(backgroundColor);
        g2D.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        
        g2D.setStroke(new BasicStroke(1 * UIProperties.uiScale));
        if (gridVisible) {
            g2D.setColor(UIProperties.DIM_TEXT_COLOR);
            
            double scaledGrid = gridLength * UIProperties.getUiScale();
            
            for (int i = 0; i < widthInSquares; i++)
                for (int j = 0; j < heightInSquares; j++)                
                    g2D.draw(new Rectangle2D.Double(i * scaledGrid, j * scaledGrid, scaledGrid, scaledGrid));
        }
        
        if (borderVisible) {
            g2D.setColor(borderColor);
            g2D.draw(new Rectangle2D.Double(0, 0, getPreferredSize().width - 1, getPreferredSize().height - 1));
        }
        
        for (Block b : blocks)
            b.paintBlock(g2D);
        
//        paintRoute(g2D);
    }
    
    public final void addListeners() {
        addMouseListener(blockSelector);
        addMouseMotionListener(blockDragger);
    }

    public String getName(boolean replaceSpaces) {
        if (replaceSpaces)
            return name.replace(" ", "_");
        
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getBGmode() {
        return bgmode;
    }

    public void setBGmode(String bgmode) {
        this.bgmode = bgmode;
    }

    public void setMainWindow(GameWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    public final void resizeWorld(int width, int height) {
        this.width = width;
        this.height = height;
        
        setPreferredSize(new Dimension(this.width, this.height));
        
        this.gridLength = this.width / this.widthInSquares;
//        this.gridLength = this.height / this.heightInSquares;
//        this.gridLength = ((this.width / this.widthInSquares) + (this.height / this.heightInSquares)) / 2d;
        this.gridHalfLength = this.gridLength / 2d;
        
        for (Block b : blocks)
            b.setDiagonalLength(gridLength);
        
        repaint();
    }

    public double getGridLength() {
        return gridLength;
    }

    public double getGridHalfLength() {
        return gridHalfLength;
    }

    public double getWidthInSquares() {
        return widthInSquares;
    }

    public double getHeightInSquares() {
        return heightInSquares;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        if (bgmode.equalsIgnoreCase("Auto")) {
            this.backgroundColor = UIProperties.APP_BG;
            return;
        }
        
        this.backgroundColor = backgroundColor;
    }

    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
        repaint();
    }

    public void setBorderVisible(boolean borderVisible) {
        this.borderVisible = borderVisible;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public Point2D getEnemySpawnPoint() {
        return enemySpawnPoint;
    }

    public Point2D getEnemyPathCoordinate(int index) {
        if (index >= enemyPathCoordinates.size())
            return null;
        
        return enemyPathCoordinates.get(index);
    }
    
    public void addBlock(Block b) {
        if (b == null)
            return;
        
        blockSelector.setSelectedBlock(b);
        blockSelector.setSelectedBlockIndex(blocks.size());
        blocks.add(b);
        repaint();
    }
    
    public void setBlock(int index, Block b) {
        if (b == null) {
            blockSelector.setSelectedBlock(null);
            blocks.remove(index);
            return;
        }
        
        blockSelector.setSelectedBlock(b);
        blocks.set(index, b);
        repaint();
    }
    
    public void deleteBlock(Block b) {
        blockSelector.setSelectedBlock(null);
        blocks.remove(b);
        repaint();
    }
    
    public void deleteSelectedBlock() {
        deleteBlock(blockSelector.getSelectedBlock());
    }
    
    public Block selectBlock(Point2D p) {
        for (int i = blocks.size() - 1; i > -1; i--) {
            Block b = blocks.get(i);
            if (!b.isSelectable())
                continue;
            
            if (b.isPointInside(p)) {
                b.setSelected(true);
                blockSelector.setSelectedBlockIndex(i);
                return b;
            }
        }
        
        return null;
    }
    
    private void moveBlockLayer(int position) {
        if (blockSelector.getSelectedBlock() == null)
            return;
        
        int index = blockSelector.getSelectedBlockIndex();
        
        if (index == position)
            return;
        
        if (index < 0 || position < 0)
            return;
        if (index >= blocks.size() || position >= blocks.size())
            return;
        
        if (index > position) {
            blocks.remove(index);
            blocks.add(position, blockSelector.getSelectedBlock());
        } else {
            blocks.add(position + 1, blockSelector.getSelectedBlock());
            blocks.remove(index);
        }
        
        blockSelector.setSelectedBlockIndex(position);
    }
    
    public void moveSelectedBlockALayer(boolean forward) {
        moveBlockLayer(blockSelector.getSelectedBlockIndex() + (forward ? 1 : -1));
    }
    
    public void moveSelectedBlockTo(boolean front) {
        moveBlockLayer(front ? blocks.size() - 1 : 0);
    }
    
    public void setEnemyBlocksVisible(boolean b) {
        for (Block block : blocks) {
            if (block instanceof EnemyPathBlock)
                ((EnemyPathBlock) block).setVisible(b);
            if (block instanceof EnemySpawnBlock)
                ((EnemySpawnBlock) block).setVisible(b);
        }
    }
    
    public void verifyWorldPath() {
        ArrayList<Block> pathBlocks = new ArrayList<>();
        enemyPathCoordinates = new LinkedList<>();
        
        int spawnBlocks = 0;
        int clockBlocks = 0;
        
        Block spawnBlock = null;
        Block clockBlock = null;
        
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            
            if (b instanceof EnemyPathBlock)
                pathBlocks.add(b);
            if (b instanceof EnemySpawnBlock) {
                spawnBlock = b;
                spawnBlocks++;
            }
            if (b instanceof ClockBlock) {
                clockBlock = b;
                clockBlocks++;
            }
        }
        
        oneClockBlockFound = clockBlocks == 1;
        if (!oneClockBlockFound)
            return;
        
        oneSpawnPathBlockFound = spawnBlocks == 1;
        if (!oneSpawnPathBlockFound)
            return;
        
        if (pathBlocks.isEmpty()) {
            allPathBlockTogether = false;
            return;
        }
        
        allPathBlockTogether = false;
        
        Point2D destinyCoordinates = clockBlock.getCoordinates();
        
        Block lastBlock = spawnBlock;
        
        while (pathBlocks.size() > 1) {
            Point2D [] points = lastBlock.getAdjacentPoints();
            
            boolean connected = false;
            
            for (int i = 0; i < pathBlocks.size(); i++) {
                Block b = pathBlocks.get(i);
                
                connected = b.doCoordinatesEqual(points[0]) || b.doCoordinatesEqual(points[1]) 
                        || b.doCoordinatesEqual(points[2]) || b.doCoordinatesEqual(points[3]);
                
                if (connected) {
                    lastBlock = pathBlocks.remove(i);
                    enemyPathCoordinates.add(lastBlock.getCoordinates());
                    break;
                }
            }
            
            if (!connected)
                return;
        }
        
        for (Point2D p : pathBlocks.get(0).getAdjacentPoints())
            if (p.equals(destinyCoordinates)) {
                allPathBlockTogether = true;
                break;
            }
        
        enemyPathCoordinates.add(pathBlocks.get(0).getCoordinates());
        enemySpawnPoint = spawnBlock.getCoordinates();
        
//        System.out.println(allPathBlockTogether ? "Route COMPLETE" : "Route incomplete");
    }
    
    public void paintRoute(Graphics2D g2D) {
        if (enemyPathCoordinates.isEmpty())
            return;
        
        g2D.setFont(UIProperties.APP_TITLE_FONT);
        
        for (int i = 0; i < enemyPathCoordinates.size(); i++) {
            Point2D p = enemyPathCoordinates.get(i);
            p.setLocation(((p.getX() - 1) * gridLength + (gridLength/2) - 5) * UIProperties.getUiScale(), (p.getY() - 1) * gridLength * UIProperties.getUiScale());
            
            g2D.setColor(Color.GREEN);
            g2D.fill(new Rectangle2D.Double(p.getX(), p.getY(), 10, 10));
            
            g2D.setColor(Color.BLACK);
            g2D.drawString("" + i, (float) p.getX(), (float) p.getY());
        }
    }
    
    public HashMap<String, String> collectProperties() {
        HashMap<String, String> properties = new HashMap<>();
        
        properties.put(WorldProperties.NAME.name(), getName(true));
        properties.put(WorldProperties.WIDTH_IN_SQUARES.name(), "" + widthInSquares);
        properties.put(WorldProperties.HEIGHT_IN_SQUARES.name(), "" + heightInSquares);
        properties.put(WorldProperties.BGMODE.name(), bgmode);
        properties.put(WorldProperties.BGCOLOR.name(), LibUtilities.colorToString(backgroundColor));
        
        HashMap<String, String> blocksProperties = new HashMap<>();
        for (int i = 0; i < blocks.size(); i++)
            blocksProperties.put("BL" + i, blocks.get(i).collectProperties().toString());
        
        properties.put(WorldProperties.BLOCKS.name(), blocksProperties.toString());
        
        return properties;
    }
}
