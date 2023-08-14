package ui.construction;

import ui.blocks.Block;
import ui.blocks.GrassBlock;
import ui.blocks.LavaBlock;
import ui.blocks.RoadBlock;
import ui.blocks.WaterBlock;
import ui.enums.BlockTypes;

/**
 *
 * @author cristopher
 */
public class BlockFactory {
    public static Block createBlock(double xGrid, double yGrid, double diagonalLength, BlockTypes blockType) {
        switch (blockType) {
            case WIRE:
            return new Block(xGrid, yGrid, diagonalLength);
            
            case GRASS:
            return new GrassBlock(xGrid, yGrid, diagonalLength);
            
            case ROAD:
            return new RoadBlock(xGrid, yGrid, diagonalLength);
            
            case WATER:
            return new WaterBlock(xGrid, yGrid, diagonalLength);
            
            case LAVA:
            return new LavaBlock(xGrid, yGrid, diagonalLength);
        }
        
        return null;
    }
}
