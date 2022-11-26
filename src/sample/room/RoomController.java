package sample.room;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.item.Item;
import sample.item.Potion.AttackPotion;
import sample.item.Potion.HealthPotion;
import sample.item.Potion.ImmunityPotion;
import sample.item.Weapon.Weapon1;
import sample.item.Weapon.Weapon2;
import sample.item.Weapon.Weapon3;
import sample.monster.Monster;
import sample.player.Player;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RoomController {

    private Main main;

    // Get all of the buttons for the room
    @FXML
    private Button exitLeft;
    @FXML
    private Button exitRight;
    @FXML
    private Button exitTop;
    @FXML
    private Button exitDown;
    @FXML
    private Button exitDungeon;

    @FXML
    private Button attackPotion;
    @FXML
    private Button healthPotion;
    @FXML
    private Button defensePotion;

    @FXML
    private Button weapon1;
    @FXML
    private Button weapon2;
    @FXML
    private Button weapon3;

    // Get all of the labels
    @FXML
    private Label difficultyLabel;
    @FXML
    private Label floorLabel;
    @FXML
    private Label hpLabel;
    @FXML
    private Label goldLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label weaponNum1;
    @FXML
    private Label weaponNum2;
    @FXML
    private Label weaponNum3;

    @FXML
    private ImageView goldImageView;
    @FXML
    private ImageView heartImageView;
    @FXML
    private ImageView playerImage;
    @FXML
    private ProgressBar hpProgressBar;
    @FXML
    private GridPane dungeonGrid;

    // Map elements
    @FXML
    private GridPane mapGrid;
    @FXML
    private AnchorPane mapPane;
    @FXML
    private Rectangle roomNode;

    // Challenge Buttons
    @FXML
    private Button startChallengeBtn;
    @FXML
    private Button skipChallengeBtn;

    private Room currentRoom;
    private Room[][] dungeonLayout = new Room[6][6];

    private static boolean running;

    /**
     * Initializes the room upon start from the configuration screen.
     */
    @FXML
    public void initialize() {
        if (Player.getGender().equals("Female")) {
            playerImage.setImage(new Image("sample/images/samurai.png"));
        } else {
            playerImage.setImage(new Image("sample/images/warrior.png"));
        }

        // Populate the dungeon array

        dungeonLayout[0][1] = new Room(false, false, false, true);
        dungeonLayout[1][0] = new Room(false, true, false, false);
        dungeonLayout[1][1] = new Room(true, true, true, true);
        dungeonLayout[1][1].setRoomType("Starting Room");
        dungeonLayout[1][2] = new Room(true, false, false, true);
        dungeonLayout[1][2].setRoomType("Challenge");
        dungeonLayout[2][1] = new Room(false, true, true, true);
        dungeonLayout[2][2] = new Room(true, true, true, false);
        dungeonLayout[2][3] = new Room(true, true, false, false);
        dungeonLayout[2][3].setRoomType("Challenge");
        dungeonLayout[2][4] = new Room(true, false, false, true);
        dungeonLayout[3][1] = new Room(false, false, true, false);
        dungeonLayout[3][4] = new Room(false, false, true, true);
        dungeonLayout[4][4] = new Room(true, true, true, true);
        dungeonLayout[4][4].setRoomType("Challenge");
        dungeonLayout[4][3] = new Room(false, true, false, false);
        dungeonLayout[4][5] = new Room(true, false, false, false);
        dungeonLayout[5][4] = new Room(false, false, true, false);

        // Randomize which of the three end rooms will be the exit
        double randomNum = Math.random();
        if (randomNum <= 0.33) {
            dungeonLayout[4][3].setRoomType("Exit Room");
        } else if (randomNum > 0.33 && randomNum <= 0.66) {
            dungeonLayout[4][5].setRoomType("Exit Room");
        } else {
            dungeonLayout[5][4].setRoomType("Exit Room");
        }

        // Randomize difficulties of each room
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (dungeonLayout[i][j] != null) {
                    String roomType = dungeonLayout[i][j].getRoomType();
                    if (roomType.equals("Exit Room")) {
                        dungeonLayout[i][j].setRoomDifficulty("Boss");
                        dungeonLayout[i][j].populateBossMonster();
                    } else {
                        if (roomType.equals("Challenge")) {
                            exitLeft.setVisible(false);
                            exitRight.setVisible(false);
                            exitTop.setVisible(false);
                            exitDown.setVisible(false);
                        } else if (roomType.equals("Default")) {
                            randomNum = Math.random();
                            if (randomNum <= 0.33) {
                                dungeonLayout[i][j].setRoomDifficulty("Easy");
                            } else if (randomNum > 0.33 && randomNum <= 0.66) {
                                dungeonLayout[i][j].setRoomDifficulty("Medium");
                            } else {
                                dungeonLayout[i][j].setRoomDifficulty("Hard");
                            }

                            dungeonLayout[i][j].populateMonsterList();
                            dungeonLayout[i][j].createItemList();
                        }
                    }
                }
            }
        }

        // Update the UI of the game
        updateUI();
    }

    public void focus() {
        playerImage.requestFocus();
    }

    private Item checkItemCollision(List<Item> itemList, int xDir, int yDir) {
        for (Item item : itemList) {
            if (currentRoom.getCleared() && item != null
                    && Player.getPosX() + xDir == item.getxPos()
                    && Player.getPosY() + yDir == item.getyPos()) {
                return item;
            }
        }
        return null;
    }

    private Monster checkMonsterCollision(List<Monster> monsterList, int xDir, int yDir) {
        // Check every monster to see if the player moving in that direction would collide
        for (Monster monster : monsterList) {
            // If you find a collision, return that Monster
            if (Player.getPosX() + xDir == monster.getxPos()
                    && Player.getPosY() + yDir == monster.getyPos()) {
                return monster;
            }
        }
        // If you don't find any collisions, return Null
        return null;
    }

    private void monsterAliveCheck(Monster monster) {
        // Check if the monster is dead
        if (monster.getHealth() <= 0) {
            Player.addGold(monster.getGoldDrop());
            System.out.println("Monster Drop: " + monster.getGoldDrop()
                    + "  Current gold: " + Player.getGold());

            // Keep track of the index we will need to remove from
            int removeIndex = 0;
            boolean foundMonster = false;
            // Check all of the monsters in the room to find our match
            for (Monster checkMonster : currentRoom.getMonsters()) {
                // If we find our match, then break
                if (monster == checkMonster) {
                    foundMonster = true;
                    break;
                } else {
                    removeIndex++;
                }
            }

            // If we found our monster, remove it
            if (foundMonster) {
                System.out.println("Found the monster");
                int foundX = 0;
                int foundY = 0;
                Player.incKill();
                for (Node node : dungeonGrid.getChildren()) {
                    if (dungeonGrid.getRowIndex(node) == monster.getyPos()
                            && dungeonGrid.getColumnIndex(node) == monster.getxPos()) {
                        foundX = dungeonGrid.getRowIndex(node);
                        foundY = dungeonGrid.getColumnIndex(node);
                        System.out.println("Remove the monster!");
                        break;
                    }

                }
                // Get children of the gridpane
                // Find the child that has the same x and y as our monster
                // Remove that node?
                // Break
                Rectangle reset = new Rectangle(65, 65, Color.WHITE);
                dungeonGrid.add(reset, foundY, foundX);

                currentRoom.getMonsters().remove(removeIndex);
                // Check if the monsterList is empty, if so, set roomStatus to cleared & updateUI
                if (currentRoom.getMonsters().size() == 0) {
                    currentRoom.setCleared();
                    if (currentRoom.getRoomType().equals("Challenge")) {
                        Player.addGold(150);
                    }
                }
                updateUI();
            }
        }
    }

    public void generateItem(Item item) {
        StackPane stackPane = new StackPane();
        stackPane.setMaxWidth(64);
        stackPane.setMaxHeight(64);
        stackPane.setBackground(item.getBackground());
        dungeonGrid.add(stackPane, item.getxPos(), item.getyPos());
    }

    public void generateItem02(List<Item> itemList) {
        for (Item item : itemList) {
            if (item != null) {
                generateItem(item);
            }
        }
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        focus();
        switch (event.getCode()) {
        case W:
            if (Player.getPosY() == 0) {
                moveUp();
            } else {
                Monster potentialTarget = checkMonsterCollision(currentRoom.getMonsters(), 0, -1);
                Item potentialItem = checkItemCollision(currentRoom.getItems(), 0, -1);
                if (potentialTarget == null) {
                    if (potentialItem != null && Player.getGold() >= 50) {
                        Player.buyItem();
                        handlePickup(potentialItem);
                    }
                    Player.setPosY(Player.getPosY() - 1);
                } else {
                    Player.attack(potentialTarget);
                    monsterAliveCheck(potentialTarget);
                }
            }
            break;
        case S:
            if (Player.getPosY() == 4) {
                moveDown();
            } else {
                Monster potentialTarget = checkMonsterCollision(currentRoom.getMonsters(), 0, 1);
                Item potentialItem = checkItemCollision(currentRoom.getItems(), 0, 1);
                if (potentialTarget == null) {
                    if (potentialItem != null && Player.getGold() >= 50) {
                        Player.buyItem();
                        handlePickup(potentialItem);
                    }
                    Player.setPosY(Player.getPosY() + 1);
                } else {
                    Player.attack(potentialTarget);
                    monsterAliveCheck(potentialTarget);
                }
            }
            break;
        case A:
            if (Player.getPosX() == 0) {
                moveLeft();
            } else {
                Monster potentialTarget = checkMonsterCollision(currentRoom.getMonsters(), -1, 0);
                Item potentialItem = checkItemCollision(currentRoom.getItems(), -1, 0);
                if (potentialTarget == null) {
                    if (potentialItem != null && Player.getGold() >= 50) {
                        Player.buyItem();
                        handlePickup(potentialItem);
                    }
                    Player.setPosX(Player.getPosX() - 1);
                } else {
                    Player.attack(potentialTarget);
                    monsterAliveCheck(potentialTarget);
                }
            }
            break;
        case D:
            if (Player.getPosX() == 12) {
                moveRight();
            } else {
                Monster potentialTarget = checkMonsterCollision(currentRoom.getMonsters(), 1, 0);
                Item potentialItem = checkItemCollision(currentRoom.getItems(), 1, 0);
                if (potentialTarget == null) {
                    if (potentialItem != null && Player.getGold() >= 50) {
                        Player.buyItem();
                        handlePickup(potentialItem);
                    }
                    Player.setPosX(Player.getPosX() + 1);
                } else {
                    Player.attack(potentialTarget);
                    monsterAliveCheck(potentialTarget);
                }
            }
            break;
        case M:
            if (!mapPane.isVisible()) {
                mapPane.setVisible(true);
                mapPane.toFront();
            } else {
                mapPane.setVisible(false);
            }
            break;
        case DIGIT1:
            System.out.println("Trying to use a health potion.");
            HealthPotion.useHPotion();
            updateUI();
            break;
        case DIGIT2:
            System.out.println("Trying to use an attack potion.");
            AttackPotion.useAPotion();
            updateUI();
            break;
        case DIGIT3:
            System.out.println("Trying to use an immunity potion.");
            ImmunityPotion.useIPotion();
            updateUI();
            break;
        case I:
            if (Weapon1.getWeaponCount() > 0) {
                System.out.println("Switched to W1.");
                weapon1.setOpacity(1.0);
                weapon2.setOpacity(0.5);
                weapon3.setOpacity(0.5);
                Weapon1.equip();
                updateUI();
            }
            break;
        case O:
            if (Weapon2.getWeaponCount() > 0) {
                System.out.println("Switched to W2.");
                weapon1.setOpacity(0.5);
                weapon2.setOpacity(1.0);
                weapon3.setOpacity(0.5);
                Weapon2.equip();
                updateUI();
            }
            break;
        case P:
            if (Weapon3.getWeaponCount() > 0) {
                System.out.println("Switched to W3.");
                weapon1.setOpacity(0.5);
                weapon2.setOpacity(0.5);
                weapon3.setOpacity(1.0);
            }
            Weapon3.equip();
            updateUI();
            break;
        default:
            break;
        }
        playerImage.toFront();
        dungeonGrid.getChildren().remove(playerImage);
        dungeonGrid.add(playerImage, Player.getPosX(), Player.getPosY());
    }

    public void handlePickup(Item potentialItem) {
        switch (potentialItem.getName()) {
        case "Health Potion":
            HealthPotion.increasePotionCount();
            break;
        case "Immunity Potion":
            ImmunityPotion.increasePotionCount();
            break;
        case "Attack Potion":
            AttackPotion.increasePotionCount();
            break;
        case "Weapon 1":
            Weapon1.pickup();
            if (Player.getWeapon().equals("Weapon 1")) {
                Weapon1.equip();
            }
            break;
        case "Weapon 2":
            Weapon2.pickup();
            if (Player.getWeapon().equals("Weapon 2")) {
                Weapon2.equip();
            }
            break;
        case "Weapon 3":
            Weapon3.pickup();
            if (Player.getWeapon().equals("Weapon 3")) {
                Weapon3.equip();
            }
            break;
        default:
            break;
        }

        // Keep track of the index we will need to remove from
        int removeIndex = 0;
        boolean foundItem = false;
        // Check all of the items in the room to find our match
        for (Item item : currentRoom.getItems()) {
            // If we find our match, then break
            if (item == potentialItem) {
                foundItem = true;
                break;
            } else {
                removeIndex++;
            }
        }

        // If found item, remove it
        if (foundItem) {
            // remove the item from room
            int foundX = 0;
            int foundY = 0;
            for (Node node : dungeonGrid.getChildren()) {
                if (dungeonGrid.getRowIndex(node) == potentialItem.getyPos()
                        && dungeonGrid.getColumnIndex(node) == potentialItem.getxPos()) {
                    foundX = dungeonGrid.getRowIndex(node);
                    foundY = dungeonGrid.getColumnIndex(node);
                    break;
                }
            }
            // Get children of the gridpane, Find the child that has the same x and y as our item
            // Remove that node
            Rectangle reset = new Rectangle(65, 65, Color.WHITE);
            dungeonGrid.add(reset, foundY, foundX);

            currentRoom.getItems().remove(removeIndex);
            updateUI();
        }
    }

    /**
     * Move the player up.
     */
    @FXML
    private void moveUp() {
        if (Player.getPosX() == 6 && exitTop.isVisible()) {
            setWhite();
            Player.setRoomX(Player.getRoomX() - 1);
            dungeonLayout[Player.getRoomX()][Player.getRoomY()].setVisited(true);
            Player.setPosY(4);
            running = false;
            dungeonGrid.getChildren().clear();
            updateUI();
        }
    }

    /**
     * Move the player down.
     */
    @FXML
    private void moveDown() {
        if (Player.getPosX() == 6 && exitDown.isVisible()) {
            setWhite();
            Player.setRoomX(Player.getRoomX() + 1);
            dungeonLayout[Player.getRoomX()][Player.getRoomY()].setVisited(true);
            Player.setPosY(0);
            running = false;

            dungeonGrid.getChildren().clear();

            updateUI();
        }
    }

    /**
     * Move the player to the left.
     */
    @FXML
    private void moveLeft() {
        if (Player.getPosY() == 2 && exitLeft.isVisible()) {
            setWhite();
            Player.setRoomY(Player.getRoomY() - 1);
            dungeonLayout[Player.getRoomX()][Player.getRoomY()].setVisited(true);
            Player.setPosX(12);
            running = false;

            dungeonGrid.getChildren().clear();

            updateUI();
        }
    }

    /**
     * Move the player to the right.
     */
    @FXML
    private void moveRight() {
        if (Player.getPosY() == 2 && exitRight.isVisible()) {
            setWhite();
            Player.setRoomY(Player.getRoomY() + 1);
            dungeonLayout[Player.getRoomX()][Player.getRoomY()].setVisited(true);
            Player.setPosX(0);
            running = false;

            dungeonGrid.getChildren().clear();

            updateUI();
        }
    }

    /**
     * Private helper method to change previous room on the minimap to white.
     */
    private void setWhite() {
        roomNode = new Rectangle(50, 50);
        roomNode.setStroke(Color.BLACK);
        if (currentRoom.getRoomType().equals("Default")) {
            roomNode.setFill(Color.WHITE);
        } else {
            specialRoom();
        }
        mapGrid.add(roomNode, Player.getRoomY(), Player.getRoomX());
    }

    /**
     * Private helper method to color special rooms.
     */
    private void specialRoom() {
        switch (currentRoom.getRoomType()) {
        case "Starting Room":
            roomNode.setFill(Color.LIMEGREEN);
            break;
        case "Challenge":
            roomNode.setFill(Color.ORCHID);
            break;
        case "Exit Room":
            roomNode.setFill(Color.RED);
            break;
        default:
            roomNode.setFill(Color.WHITE);
            break;
        }
    }

    /**
     * Private helper method to color special rooms when the player is currently
     * in them.
     */
    private void currentSpecialRoom() {
        switch (currentRoom.getRoomType()) {
        case "Starting Room":
            roomNode.setFill(Color.GREEN);
            break;
        case "Challenge":
            roomNode.setFill(Color.PURPLE);
            break;
        case "Exit Room":
            roomNode.setFill(Color.DARKRED);
            break;
        default:
            roomNode.setFill(Color.GRAY);
            break;
        }
    }

    /**
     * Updates the game UI depending on changing factors.
     */
    private void updateUI() {
        // Get the current room
        currentRoom = dungeonLayout[Player.getRoomX()][Player.getRoomY()];
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = true;
        moveMonsters(currentRoom.getMonsters());

        // Update all of the labels
        difficultyLabel.setText(Player.getDifficulty());
        floorLabel.setText("Room: (" + Player.getRoomX() + ", " + Player.getRoomY() + ")");
        hpLabel.setText("HP: " + Player.getCurrentHp() + "/" + Player.getMaxHp());
        System.out.println("Player HP: " + Player.getCurrentHp());
        hpProgressBar.setProgress((double) Player.getCurrentHp() / (double) Player.getMaxHp());
        // xpLabel.setText("Current level: " + Player.getLevel());
        goldLabel.setText("x " + Player.getGold());
        typeLabel.setText(currentRoom.getRoomType());
        System.out.println(currentRoom.getRoomType());
        healthPotion.setText("HP x" + HealthPotion.getPotionCount());
        attackPotion.setText("ATK x" + AttackPotion.getPotionCount());
        defensePotion.setText("DEF x" + ImmunityPotion.getPotionCount());
        weaponNum1.setText("+" + Weapon1.getWeaponCount());
        weaponNum2.setText("+" + Weapon2.getWeaponCount());
        weaponNum3.setText("+" + Weapon3.getWeaponCount());

        if (currentRoom.getRoomType().equals("Challenge")) {
            if (!currentRoom.getChallengeHandled()) {
                System.out.println("We are hiding the exits!");
                exitLeft.setVisible(false);
                exitRight.setVisible(false);
                exitTop.setVisible(false);
                exitDown.setVisible(false);
                startChallengeBtn.setVisible(true);
                skipChallengeBtn.setVisible(true);
            }
        } else {
            startChallengeBtn.setVisible(false);
            skipChallengeBtn.setVisible(false);
        }

        // Update which exits are available IF monsters in room are killed
        // If the monsters in the current room are all killed then show normal exits
        if (currentRoom.getCleared()) {
            if (!currentRoom.getRoomType().equals("Challenge")
                    || currentRoom.getChallengeHandled()) {
                exitLeft.setVisible(currentRoom.getExitLeft());
                exitRight.setVisible(currentRoom.getExitRight());
                exitTop.setVisible(currentRoom.getExitUp());
                exitDown.setVisible(currentRoom.getExitDown());
            }
            System.out.println("The room is cleared, so we are showing the exits!");
            generateItem02(currentRoom.getItems());
        } else {
            // If the room to the left was visited, show normal exit
            if (!currentRoom.getRoomType().equals("Challenge") && checkNeighborRoom(0, -1)) {
                exitLeft.setVisible(currentRoom.getExitLeft());
            } else {
                exitLeft.setVisible(false);
            }

            // If the room to the right was visited, show normal exit
            if (!currentRoom.getRoomType().equals("Challenge") && checkNeighborRoom(0, 1)) {
                exitRight.setVisible(currentRoom.getExitRight());
            } else {
                exitRight.setVisible(false);
            }
            // If the room to the top was visited, show normal exit
            if (!currentRoom.getRoomType().equals("Challenge") && checkNeighborRoom(-1, 0)) {
                exitTop.setVisible(currentRoom.getExitUp());
            } else {
                exitTop.setVisible(false);
            }
            // If the room to the bottom was visited, show normal exit
            if (!currentRoom.getRoomType().equals("Challenge") && checkNeighborRoom(1, 0)) {
                exitDown.setVisible(currentRoom.getExitDown());
            } else {
                exitDown.setVisible(false);
            }
        }

        // Update if the exit dungeon option is available
        exitDungeon.setVisible(currentRoom.getCleared()
                && currentRoom.getRoomType().equals("Exit Room"));

        // Display current room on map
        roomNode = new Rectangle(50, 50);
        roomNode.setStroke(Color.BLACK);
        if (currentRoom.getRoomType().equals("Default")) {
            roomNode.setFill(Color.GRAY);
        } else {
            currentSpecialRoom();
        }
        mapGrid.add(roomNode, Player.getRoomY(), Player.getRoomX());
    }

    /**
     * Checks if the neighboring room is visited or not.
     *
     * @param xDir the x-direction of the room to check (-1, 0, 1)
     * @param yDir the y-direction of the room to check(-1, 0, 1)
     * @return status of adjacent room
     */
    private boolean checkNeighborRoom(int xDir, int yDir) {
        int xCheck = Player.getRoomX() + xDir;
        int yCheck = Player.getRoomY() + yDir;
        System.out.println("Checking a room: " + xCheck + ", " + yCheck);
        try {
            if (dungeonLayout[xCheck][yCheck] == null) {
                // System.out.println("\tThe room we checked does not exist.");
                return false;
            } else {
                // System.out.println("\tThe room we checked does exist. Visited Status: "
                // + dungeonLayout[xCheck][yCheck].getVisited());
                return dungeonLayout[xCheck][yCheck].getVisited();
            }
        } catch (ArrayIndexOutOfBoundsException err) {
            // System.out.println("\tThe room we checked threw an error.");
            return false;
        }

    }

    private void moveMonsters(List<Monster> monsterList) {
        Random rand = new Random();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    Platform.runLater(() -> {
                        for (Monster monster : monsterList) {
                            // Reset color of square to original
                            Rectangle reset = new Rectangle(65, 65, Color.WHITE);
                            dungeonGrid.add(reset, monster.getxPos(), monster.getyPos());

                            // Get random direction to place new monster
                            // You need to handle the edge case when it tries to go off the screen!
                            int direction = rand.nextInt(4);
                            switch (direction) {
                            case 0:
                                if (monster.getxPos() + 1 < 12
                                        && canMoveMonster(monster, monsterList, 1, 0)) {
                                    monster.setxPos(monster.getxPos() + 1);
                                }
                                break;
                            case 1:
                                if (monster.getyPos() + 1 < 4
                                        && canMoveMonster(monster, monsterList, 0, 1)) {
                                    monster.setyPos(monster.getyPos() + 1);
                                }
                                break;
                            case 2:
                                if (monster.getxPos() - 1 > 0
                                        && canMoveMonster(monster, monsterList, -1, 0)) {
                                    monster.setxPos(monster.getxPos() - 1);
                                }
                                break;
                            case 3:
                                if (monster.getyPos() - 1 > 0
                                        && canMoveMonster(monster, monsterList, 0, -1)) {
                                    monster.setyPos(monster.getyPos() - 1);
                                }
                                break;
                            default:
                                break;
                            }

                            StackPane stackPane = new StackPane();
                            stackPane.setMaxWidth(64);
                            stackPane.setMaxHeight(64);
                            stackPane.setBackground(monster.getBackground());
                            dungeonGrid.add(stackPane, monster.getxPos(), monster.getyPos());
                            Label test = new Label((Double.toString(monster.getHealth())));
                            dungeonGrid.add(test, monster.getxPos(), monster.getyPos());
                        }

                    });
                } else {
                    timer.cancel();
                    timer.purge();
                }
            }
        };
        //1000 milliseconds is 1 second, call the run function above every second
        timer.scheduleAtFixedRate(task, 0, 1_000);
    }

    /**
     * Checks if a specific monster is able to move to its new, desired position.
     *
     * @param checkMonster the Monster that wants to move
     * @param monsterList  a list of all Monsters still in the room
     * @param xMove        the direction to move in x (-1, 0, 1)
     * @param yMove        the direction to move in y (-1, 0, 1)
     * @return a boolean value if the monster can move to its new position
     */
    private boolean canMoveMonster(Monster checkMonster,
                                   List<Monster> monsterList, int xMove, int yMove) {
        for (Monster monster : monsterList) {
            if (checkMonster.getxPos() + xMove == monster.getxPos()
                    && checkMonster.getyPos() + yMove == monster.getyPos()) {
                return false;
            } else if (checkMonster.getxPos() + xMove == Player.getPosX()
                    && checkMonster.getyPos() + yMove == Player.getPosY()) {
                System.out.print("Player Health: " + Player.getCurrentHp());
                if (Player.isImmuneBuffed()) {
                    System.out.println("Tried to attack player, but was immune!");
                    Player.useImmunePotionDuration();
                } else {
                    monster.attack();
                }
                // Swap to game over screen
                try {
                    if (Player.getCurrentHp() <= 0) {
                        currentRoom.getMonsters().clear();
                        Main.showLoseScreen();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                updateUI();
                System.out.println(" Player Health: " + Player.getCurrentHp());
                return false;
            }
        }
        return true;
    }


    @FXML
    private void goWinScreen() throws IOException {
        Main.showWinScreen();
    }

    @FXML
    private void startChallenge() throws IOException {
        if (!currentRoom.getChallengeHandled()) {
            dungeonLayout[Player.getRoomX()][Player.getRoomY()].setRoomDifficulty("Challenge");
        }
        currentRoom.setChallengeHandled();
        currentRoom.setStartedRoom(true);
        skipChallengeBtn.setVisible(false);
        System.out.println("Started Challenge");
        updateUI();
    }

    @FXML
    private void skipChallenge() throws IOException {
        startChallengeBtn.setVisible(false);
        currentRoom.setChallengeHandled();
        currentRoom.setSkippedRoom(true);
        currentRoom.setCleared();
        updateUI();
    }
}
