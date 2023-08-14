package utils;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import ui.World;
import ui.World.WorldProperties;
import ui.blocks.Block;
import ui.blocks.Block.BlockProperties;
import ui.construction.BlockFactory;
import ui.enums.BlockTypes;

/**
 *
 * @author cristopher
 */
public class WorldUtilities {
    public static final File WIN_PATH = FileUtilities.joinPath(LibUtilities.USER_HOME, "AppData", "Local", "dtc_worlds");
    public static final File UNIX_PATH = FileUtilities.joinPath(LibUtilities.USER_HOME, ".dtc_worlds");
    
    public static final File WORLDS_DIRECTORY = !LibUtilities.IS_UNIX_LIKE ? WIN_PATH : UNIX_PATH;
    
    public static File createWorldFile(World w) {
        return FileUtilities.joinPath(WORLDS_DIRECTORY, w.getName(true) + ".dtcl");
    }
    
    public static boolean saveWorld(World w) {
        File file = createWorldFile(w);
        
        return FileUtilities.writeFile(file, w.collectProperties().toString());
    }
    
    private static void setBlockProperty(String key, String value, Block b) {
        BlockProperties keyParse;
        
        try {
            keyParse = BlockProperties.valueOf(key);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return;
        }
        
        boolean isInteger = value.matches("^-?[0-9]+(\\.?[0-9]+)?$");
        
        switch (keyParse) {
            case XGRID:
                if (!isInteger)
                    throw new IllegalArgumentException("Data '" + value + "' is not a number");
                
                b.setXGrid(Double.parseDouble(value));
            break;
            case YGRID:
                if (!isInteger)
                    throw new IllegalArgumentException("Data '" + value + "' is not a number");
                
                b.setYGrid(Double.parseDouble(value));
            break;
            case TOP_FACE_VISIBLE:
                b.setTopFaceVisible(Boolean.parseBoolean(value));
            break;
            case RIGHT_FACE_VISIBLE:
                b.setRightFaceVisible(Boolean.parseBoolean(value));
            break;
            case LEFT_FACE_VISIBLE:
                b.setLeftFaceVisible(Boolean.parseBoolean(value));
            break;
        }
    }
    
    private static Block createBlock(String blockID, LinkedList<String> replacedStringIDs, LinkedList<String> replacedStrings) {
        String [] splitedData = LibUtilities.splitData(blockID, replacedStringIDs, replacedStrings);
        
        Block b = null;
        
        for (String property : splitedData) {
            String [] psplit = property.split("=");
            if (psplit[0].contains(BlockProperties.NAME.name())) {
                b = BlockFactory.createBlock(0, 0, 0, BlockTypes.valueOf(psplit[1].toUpperCase()));
                break;
            }
        }
        
        if (b == null)
            return b;
        
        for (String property : splitedData) {
            String [] psplit = property.split("=");
            setBlockProperty(psplit[0], psplit[1], b);
        }
        
        return b;
    }
    
    private static void setWorldBlocks(String worldBlocksID, LinkedList<String> replacedStringIDs, LinkedList<String> replacedStrings, World w) {
        if (replacedStringIDs.isEmpty() || replacedStrings.isEmpty())
            return;
        
        String [] splitedData = LibUtilities.splitData(worldBlocksID, replacedStringIDs, replacedStrings);
        
        ArrayList<Block> blocks = new ArrayList<>();
        for (int i = 0; i < splitedData.length; i++)
            blocks.add(null);
        
        for (String block : splitedData) {
            String [] bsplit = block.split("=");
            String bindex = bsplit[0].replace("BL", "");
            
            int index = Integer.parseInt(bindex);
            
            Block b = createBlock(bsplit[1], replacedStringIDs, replacedStrings);
            if (b == null) {
                blocks.remove(index);
                continue;
            }
            
            blocks.set(index, b);
        }
        
        w.setBlocks(blocks);
    }
    
    public static World loadWorld(File worldFile) {
        if (worldFile == null)
            return null;
        
        String data = FileUtilities.readFile(worldFile);
        if (data.isEmpty())
            return null;
        
        Object [] compressedInfo = LibUtilities.compressStringHashMap(data);
        data = (String) compressedInfo[0];
        LinkedList<String> replacedStringIDs = (LinkedList<String>) compressedInfo[1];
        LinkedList<String> replacedStrings = (LinkedList<String>) compressedInfo[2];
        
        
        HashMap<String, String> worldProperties = LibUtilities.parseProperties(data);
        if (worldProperties.isEmpty())
            return null;
        
        double widthInSquares = Double.parseDouble(worldProperties.get(WorldProperties.WIDTH_IN_SQUARES.name()));
        double heightInSquares = Double.parseDouble(worldProperties.get(WorldProperties.HEIGHT_IN_SQUARES.name()));
        
        int [] bgColorData = LibUtilities.getColorData(worldProperties.get(WorldProperties.BGCOLOR.name()));
        Color bgColor = new Color(bgColorData[0], bgColorData[1], bgColorData[2]);
        
        
        World w = new World(widthInSquares, heightInSquares);
        w.setName(worldProperties.get(WorldProperties.NAME.name()).replace("_", " "));
        w.setBGmode(worldProperties.get(WorldProperties.BGMODE.name()));
        w.setBackgroundColor(bgColor);
        
        try {
            setWorldBlocks(worldProperties.get(WorldProperties.BLOCKS.name()), replacedStringIDs, replacedStrings, w);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        
        return w;
    }
}
