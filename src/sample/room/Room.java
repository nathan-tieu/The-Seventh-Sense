package sample.room;

import sample.item.Item;
import sample.item.Potion.AttackPotion;
import sample.item.Potion.HealthPotion;
import sample.item.Potion.ImmunityPotion;
import sample.item.Weapon.Weapon1;
import sample.item.Weapon.Weapon2;
import sample.item.Weapon.Weapon3;
import sample.monster.*;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    private final int easyNum = 3;
    private final int mediumNum = 2;
    private final int hardNum = 1;
    private boolean exitLeft;
    private boolean exitRight;
    private boolean exitUp;
    private boolean exitDown;
    private boolean visited = false;
    private boolean cleared;
    private String roomType;
    private String roomDifficulty;
    private boolean isChallenge = false;
    private boolean challengeHandled = false;
    private boolean skippedRoom = false;
    private boolean startedRoom = false;
    private ArrayList<Monster> monsters = new ArrayList<>();

    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Creates a new room object.
     *
     * @param exitL - if player can move left
     * @param exitR - if player can move right
     * @param exitU - if player can move up
     * @param exitD - if player can move down
     */
    public Room(boolean exitL, boolean exitR, boolean exitU, boolean exitD) {
        this.exitLeft = exitL;
        this.exitRight = exitR;
        this.exitUp = exitU;
        this.exitDown = exitD;
        this.roomType = "Default";
        this.roomDifficulty = "";
        this.cleared = true;
    }

    /**
     * Creates a new room object with monsters.
     *
     * @param exitL - if player can move left
     * @param exitR - if player can move right
     * @param exitU - if player can move up
     * @param exitD - if player can move down
     * @param roomDifficulty - the difficulty of the room
     */
    public Room(boolean exitL, boolean exitR, boolean exitU, boolean exitD, String roomDifficulty) {
        this.exitLeft = exitL;
        this.exitRight = exitR;
        this.exitUp = exitU;
        this.exitDown = exitD;
        this.roomType = "Monster Room";
        this.roomDifficulty = roomDifficulty;
        this.cleared = false;
        populateMonsterList();
    }

    /**
     * creates the itemlist of 2 random items for the room
     */
    public void createItemList() {
        items.add(getRandomItem());
        items.add(getRandomItem());
    }

    /**
     * generates a random item.
     * @return a random item
     */
    public Item getRandomItem() {
        Random rand = new Random();
        int random = rand.nextInt(8);
        Item newitem;

        switch (random) {
        case 0:
            newitem = new AttackPotion();
            break;
        case 1:
            newitem = new HealthPotion();
            break;
        case 2:
            newitem = new ImmunityPotion();
            break;
        case 3:
            newitem = new Weapon1();
            break;
        case 4:
            newitem = new Weapon2();
            break;
        case 5:
            newitem = new Weapon3();
            break;
        default:
            newitem = null;
            break;
        }

        if (newitem != null) {
            int randomx = rand.nextInt(10);
            int randomy = rand.nextInt(4);
            newitem.setxPos(randomx);
            newitem.setyPos(randomy);
        }

        return newitem;
    }

    /**
     * Populates the monsterList with monsters corresponds to the type of the room.
     */
    public void populateMonsterList() {
        int monsterNum;
        Monster tempMonster;

        // Based on the room difficulty determine the number of monsters needed
        switch (roomDifficulty) {
        case "Challenge":
            System.out.println("Filling challenge room");
            isChallenge = true;
            monsterNum = 3;
            Monster temp = new MediumMonster();
            temp.setxPos(3);
            temp.setyPos(3);
            monsters.add(temp);
            temp = new HardMonster();
            temp.setxPos(7);
            temp.setyPos(2);
            monsters.add(temp);
            temp = new MediumMonster();
            temp.setxPos(9);
            temp.setyPos(4);
            monsters.add(temp);
            break;
        case "Easy":
            monsterNum = easyNum;
            break;
        case "Medium":
            monsterNum = mediumNum;
            break;
        default:
            monsterNum = hardNum;
            break;
        }


        // While there are not N monsters in the room
        while (!isChallenge && this.monsters.size() < monsterNum) {
            // Flag to see if the monster already exists
            boolean monsterExists = false;
            // Create a new monster
            tempMonster = getNewMonster();
            // Check if the new monster overlaps with any existing monsters
            for (Monster monster : monsters) {
                // If so, set the flag to true and break the loop
                if (checkIfOverlap(monster, tempMonster)) {
                    monsterExists = true;
                    break;
                }
            }
            // If the monster already exists, do not add it to the list of monsters
            if (!monsterExists) {
                monsters.add(tempMonster);
            }
        }
    }

    /**
     * Populates room with a boss monster
     */
    public void populateBossMonster() {
        monsters.clear();
        monsters.add(new BossMonster());
    }

    /**
     * Checks if the coordinates of two monsters are the same.
     * @param existingMonster that is already in the room
     * @param newMonster that is trying to be added to the room
     * @return a boolean whether the two monsters are overlapping
     */
    private boolean checkIfOverlap(Monster existingMonster, Monster newMonster) {
        return (existingMonster.getxPos() == newMonster.getxPos()
                && existingMonster.getyPos() == newMonster.getyPos());
    }

    /**
     * Creates a new monster based on the difficulty of the room.
     * @return a Monster of type Easy, Medium, or Hard.
     */
    private Monster getNewMonster() {
        switch (roomDifficulty) {
        case "Easy":
            return (new EasyMonster());
        case "Medium":
            return (new MediumMonster());
        default:
            return (new HardMonster());
        }
    }

    /**
     * Getter for the left door exit status.
     *
     * @return boolean if the player can exit left
     */
    public boolean getExitLeft() {
        return exitLeft;
    }

    /**
     * Getter for the right door exit status.
     *
     * @return boolean if the player can exit right
     */
    public boolean getExitRight() {
        return exitRight;
    }

    /**
     * Getter for the up door exit status.
     *
     * @return boolean if the player can exit up
     */
    public boolean getExitUp() {
        return exitUp;
    }

    /**
     * Getter for the down door exit.
     *
     * @return boolean if the player can exit down
     */
    public boolean getExitDown() {
        return exitDown;
    }

    /**
     * Getter for the room type.
     *
     * @return the type of the room as a String
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Setter for the room type.
     *
     * @param roomType type of the room.
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
        if (this.roomType.equals("Starting Room")) {
            this.visited = true;
        }
    }

    /**
     * Getter for the list of monsters.
     * @return an ArrayList of type Monster
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Getter for the visited status of the room.
     * @return a boolean if the room is visited or not
     */
    public boolean getVisited() {
        return visited;
    }

    /**
     * Setter for the visited status of the room.
     * ONLY TO BE USED FOR TESTING PURPOSES!
     * @param value returns a boolean
     */
    public void setVisited(boolean value) {
        this.visited = value;
    }

    /**
     * Sets the visited status of the room to be visited (true).
     */
    public void visit() {
        this.visited = true;
    }

    @Override
    public String toString() {
        return "L: " + exitLeft + "\nU: " + exitUp + "\nR: " + exitRight + "\nD:" + exitDown;
    }

    /**
     * Sets the status of a room to be cleared.
     */
    public void setCleared() {
        this.cleared = true;
    }

    /**
     * Getter for the status of the room being cleared or not.
     * @return a boolean if the room is cleared or not
     */
    public boolean getCleared() {
        return this.cleared;
    }

    /**
     * Setter for the difficulty of the room.
     * @param roomDifficulty new difficulty of room
     */
    public void setRoomDifficulty(String roomDifficulty) {
        this.roomDifficulty = roomDifficulty;
        populateMonsterList();
        this.cleared = false;
    }

    /**
     * Getter for the difficulty of the room.
     * @return current difficulty of the room
     */
    public String getRoomDifficulty() {
        return roomDifficulty;
    }

    /**
     * Getter for if the challenge room has been skipped.
     * @return true if skipped, false if otherwise
     */
    public boolean isSkippedRoom() {
        return skippedRoom;
    }

    /**
     * Setter for if the challenge room has been skipped.
     * @param skippedRoom true if skipped, false if otherwise
     */
    public void setSkippedRoom(boolean skippedRoom) {
        this.skippedRoom = skippedRoom;
    }

    /**
     * Getter for if the challenge room has been started.
     * @return true if the challenge has started, false if otherwise
     */
    public boolean isStartedRoom() {
        return startedRoom;
    }

    /**
     * Setter for if the challenge room has been started.
     * @param startedRoom true if the challenge has started, false if otherwise
     */
    public void setStartedRoom(boolean startedRoom) {
        this.startedRoom = startedRoom;
    }

    /**
     * Getter for if the challenge choice has been handled.
     * @return true if challenge has been handled, false if otherwise
     */
    public boolean getChallengeHandled() {
        return this.challengeHandled;
    }

    /**
     * Marks that challenge has been handled.
     */
    public void setChallengeHandled() {
        this.challengeHandled = true;
    }
}
