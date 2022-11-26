package sample.monster;

import javafx.scene.layout.Background;

import java.util.Random;

public abstract class Monster {
    private String difficulty;
    protected int attack;
    protected double health;
    protected int xPos;
    protected int yPos;
    protected int goldDrop;

    /**
     * Constructs a monster.
     * @param difficulty - the difficulty of the monster
     * @param attack - the attack value
     * @param health - the health value
     * @param goldDrop - amount of gold dropped
     */
    public Monster(String difficulty, int attack, int health, int goldDrop) {
        this.difficulty = difficulty;
        this.attack = attack;
        this.health = health;
        this.goldDrop = goldDrop;
        // Randomly generate a coordinate for the monster
        Random random = new Random();
        this.xPos = random.nextInt(11) + 1;
        this.yPos = random.nextInt(2) + 1;

    }

    /**
     * Abstract method for monsters to attack the player.
     */
    public abstract void attack();

    /**
     * Get the difficulty of the monster.
     * @return the difficulty as a String
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Get the attack of the monster.
     * @return the attack as an integer
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Get the health of the monster.
     * @return the health as an integer
     */
    public double getHealth() {
        return health;
    }

    /**
     * Setter for the health of the monster.
     * @param health the new health for the monster
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Getter for the xPos of the monster.
     * @return the xPos as an integer
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Setter for the xPos of the monster.
     * @param x the x position of the monster
     */
    public void setxPos(int x) {
        xPos = x;
    }

    /**
     * Getter for the yPos of the monster.
     * @return the yPos as an integer
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Setter for the yPos of the monster.
     * @param y the y position of the monster
     */
    public void setyPos(int y) {
        yPos = y;
    }

    /**
     * Getter for the gold drop of the monster
     * @return the amount of gold dropped by the monster
     */
    public int getGoldDrop() {
        return goldDrop;
    }

    @Override
    public String toString() {
        return "Monster of difficulty: " + difficulty + " (" + xPos + ", " + yPos + ").";
    }

    @Override
    public boolean equals(Object obj) {
        Monster that = (Monster) obj;

        return (this.difficulty.equals(that.getDifficulty())
                && this.attack == that.getAttack()
                && this.health == that.getHealth()
                && this.xPos == that.getxPos()
                && this.yPos == that.getyPos());
    }

    public abstract Background getBackground();
}
