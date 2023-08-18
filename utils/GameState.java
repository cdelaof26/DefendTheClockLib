package utils;

import ui.gameui.GameUI;

/**
 *
 * @author cristopher
 */
public class GameState {
    protected int numberOfSteps = 210;
    
    protected int level = 1;
    
    protected int goldenDollars = 1000;
    protected int stars = 0;
    protected int exchargedStars = 0;
    
    protected int clockHealth = 30;
    
    protected int monsterAmount = 1;
    protected int monsterHealth = 300;
    
    protected int turretDamage = 120;
    protected int lazerDamage = 80;
    protected int cannonDamage = 600;
    protected int machineGunDamage = 100;
    
    
    private final GameUI gameUI;

    public GameState(GameUI gameUI) {
        this.gameUI = gameUI;
    }
    
    
    private void levelUpEasy() {
        int extraCash = (int) (Math.random() * 150);
        int extraMonster = (int) (Math.random() * 2);
        
        if ((int) (Math.random() * 10) % 2 == 0 && (int) (Math.random() * 10) % 2 == 0)
            if (level % 2 == 0)
                extraCash *= 1.5;
        
        goldenDollars += (200 * level) * 0.25 + extraCash;
        monsterAmount = (int) Math.ceil(level * 0.8) + extraMonster;
        gameUI.mainWindow.enemyRenderComponent.updateNumberOfSteps(-1);
    }
    
    private void levelUpMedium() {
        int extraCash = (int) (Math.random() * 100);
        int extraMonster = (int) (Math.random() * 4);
        
        if ((int) (Math.random() * 10) % 2 == 0 && (int) (Math.random() * 10) % 2 == 0)
            if (level % 2 == 0)
                extraCash *= 1.25;
        
        goldenDollars += (200 * level) * 0.20 + extraCash;
        monsterAmount = (int) Math.ceil(level * 0.9) + extraMonster;
        gameUI.mainWindow.enemyRenderComponent.updateNumberOfSteps(-1);
    }
    
    private void levelUpHard() {
        int extraCash = (int) (Math.random() * 80);
        int extraMonster = (int) (Math.random() * 8);
        
        if ((int) (Math.random() * 10) % 2 == 0 && (int) (Math.random() * 10) % 2 == 0)
            if (level % 2 == 0)
                extraCash = -extraCash;
        
        goldenDollars += (200 * level) * 0.15 + extraCash;
        monsterAmount = (int) Math.ceil(level * 1.2) + extraMonster;
        gameUI.mainWindow.enemyRenderComponent.updateNumberOfSteps((int) (-1 + level * 0.05));
    }

    public int getLevel() {
        return level;
    }

    public int getGoldenDollars() {
        return goldenDollars;
    }

    public int getStars() {
        return stars;
    }

    public int getMonsterAmount() {
        return monsterAmount;
    }

    public int getMonsterHealth() {
        return monsterHealth;
    }
    
    public void levelUp() {
        level++;
        
        // Debug
//        switch (GameModes.HARD) {

        switch (gameUI.mainWindow.gamemode) {
            case EASY:
                levelUpEasy();
            break;
            case MEDIUM:
                levelUpMedium();
            break;
            case HARD:
                levelUpHard();
            break;
        }
    }

    public int getClockHealth() {
        return clockHealth;
    }
    
    public int decreaseClockHealth() {
        clockHealth--;
        return clockHealth;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }
    
    public void updateNumberOfSteps(int modification) {
        numberOfSteps += modification;
    }
}
