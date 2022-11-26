package sample.item;
import javafx.scene.layout.Background;

public abstract class Item {
    private String name;
    private int xPos;
    private int yPos;
    private boolean found = false;

    /**
     * Constructs an item.
     * @param name - the name of the item
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the item.
     * @return the name of the item as a String
     */
    public String getName() {
        return name;
    }

    public abstract Background getBackground();

    /**
     * if the item is already picked up by player, returns true
     * @return if the item is found
     */
    public boolean isFound() {
        return found;
    }

    /**
     * set the found value to true if the player found the item
     * @param found sets the value to being found or not
     */
    public void setFound(boolean found) {
        this.found = found;
    }

    public void setxPos(int xpos) {
        this.xPos = xpos;
    }

    public void setyPos(int ypos) {
        this.yPos = ypos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}