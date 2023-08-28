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
    
//    protected int turretDamage = 80;
    protected int lazerDamage = 50;
    protected int cannonDamage = 500;
    protected int machineGunDamage = 90;
    
    protected int turretPrice = 500;
    protected int lazerPrice = 800;
    protected int cannonPrice = 1400;
    protected int machineGunPrice = 2400;
    
    
    private final GameUI gameUI;

    public GameState(GameUI gameUI) {
        this.gameUI = gameUI;
        
        displayInfo(0);
    }
    
    
    private void levelUpEasy() {
        int extraCash = (int) (Math.random() * 100);
        int extraMonster = (int) (Math.random() * 2);
        
        if ((int) (Math.random() * 10) % 2 == 0 && (int) (Math.random() * 10) % 2 == 0)
            if (level % 2 == 0)
                extraCash *= 1.1;
        
        goldenDollars += (200 * level) * 0.21 + extraCash;
        monsterAmount = (int) Math.ceil(level * 0.8) + extraMonster;
        monsterHealth += 6 * level;
        gameUI.mainWindow.enemyRenderComponent.updateNumberOfSteps(-1);
    }
    
    private void levelUpMedium() {
        int extraCash = (int) (Math.random() * 90);
        int extraMonster = (int) (Math.random() * 4);
        
        goldenDollars += (200 * level) * 0.19 + extraCash;
        monsterAmount = (int) Math.ceil(level * 0.9) + extraMonster;
        monsterHealth += 8 * level;
        gameUI.mainWindow.enemyRenderComponent.updateNumberOfSteps(-1);
    }
    
    private void levelUpHard() {
        int extraCash = (int) (Math.random() * 80);
        int extraMonster = (int) (Math.random() * 8);
        
        if ((int) (Math.random() * 10) % 2 == 0 && (int) (Math.random() * 10) % 2 == 0)
            if (level % 2 == 0)
                extraCash = -extraCash;
        
        goldenDollars += (200 * level) * 0.17 + extraCash;
        monsterAmount = (int) Math.ceil(level * 1.2) + extraMonster;
        monsterHealth += 12 * level;
        gameUI.mainWindow.enemyRenderComponent.updateNumberOfSteps((int) (-1 - level * 0.05));
    }

    public int getLevel() {
        return level;
    }

    public int getGoldenDollars() {
        return goldenDollars;
    }
    
    public boolean chargeDollars(int amount) {
        if (amount < 0)
            amount = -amount;
        
        if (goldenDollars - amount < 0)
            return false;
        
        goldenDollars -= amount;
        
        return true;
    }

    public int getStars() {
        return stars;
    }

    public int getExchargedStars() {
        return exchargedStars;
    }

    public int getMonsterAmount() {
        return monsterAmount;
    }

    public int getMonsterHealth() {
        return monsterHealth;
    }

    public int getClockHealth() {
        return clockHealth;
    }
    
    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public int getTurretPrice() {
        return turretPrice;
    }

    public int getLazerPrice() {
        return lazerPrice;
    }

    public int getCannonPrice() {
        return cannonPrice;
    }

    public int getMachineGunPrice() {
        return machineGunPrice;
    }
    
    public void levelUp() {
        level++;
        
        // Debug
//        switch (GameModes.HARD) {
        int dollars = goldenDollars;

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
        
        displayInfo(dollars);
    }
    
    public int decreaseClockHealth() {
        clockHealth--;
        return clockHealth;
    }
    
    public void updateNumberOfSteps(int modification) {
        numberOfSteps += modification;
    }

    private void displayInfo(int dollars) {
        System.out.println("");
        System.out.println("[INFO] Level up!");
        System.out.println("[INFO] Level " + level);
        System.out.println("[INFO] Monster amount: " + monsterAmount);
        System.out.println("[INFO] Monster health: " + monsterHealth);
        System.out.println("[INFO] Monster steps:  " + numberOfSteps);
        System.out.println("[INFO] Got " + (goldenDollars - dollars) + " golden dollars");
    }
}
