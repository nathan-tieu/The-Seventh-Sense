package sample.player;
import sample.item.Potion.AttackPotion;
import sample.item.Potion.HealthPotion;
import sample.item.Potion.ImmunityPotion;
import sample.item.Weapon.Weapon1;
import sample.item.Weapon.Weapon2;
import sample.item.Weapon.Weapon3;
import sample.monster.Monster;

public class Player {
    private static String name;
    private static String gender = "Female";
    private static String difficulty = "Medium";
    private static String weapon = "Weapon 1";
    private static int gold = 0;
    private static int xp = 0;
    private static int level = 0;
    private static int attackStat = 1;
    private static int currentHp = 10;
    private static int maxHp = 10;
    private static int roomX = 1;
    private static int roomY = 1;
    private static int posX = 6;
    private static int posY = 2;
    private static boolean attackBuffed = false;
    private static int attackPotionDuration = 0;
    private static boolean immuneBuffed = false;
    private static int immunePotionDuration = 0;
    private static int killCount = 0;
    private static int dmgTaken = 0;
    private static int potionCount = 0;
    private static int goldTotal = 0;

    public static void resetPlayerStats() {
        posX = 6;
        posY = 2;
        roomX = 1;
        roomY = 1;
        currentHp = 10;
        killCount = 0;
        dmgTaken = 0;
        potionCount = 0;
        HealthPotion.setPotionCount(2);
        AttackPotion.setPotionCount(2);
        ImmunityPotion.setPotionCount(2);
        Weapon1.resetWeaponCount();
        Weapon2.resetWeaponCount();
        Weapon3.resetWeaponCount();
        if (Player.getWeapon().equals("Weapon 1")) {
            Weapon1.equip();
            Weapon1.pickup();
        } else if (Player.getWeapon().equals("Weapon 2")) {
            Weapon2.equip();
            Weapon2.pickup();
        } else {
            Weapon3.equip();
            Weapon3.pickup();
        }
    }

    /**
     * Attack a specific monster based on the base weapon damage and attack potion buff.
     * @param monster - the monster to attack
     */
    public static void attack(Monster monster) {
        System.out.print("Attacked Monster. Health: " + monster.getHealth());
        if (attackBuffed) {
            System.out.println("Using buffed attack.");
            monster.setHealth(monster.getHealth() - attackStat * AttackPotion.getAttackBuff());
            attackPotionDuration--;
            if (attackPotionDuration == 0) {
                attackBuffed = false;
            }
        } else {
            monster.setHealth(monster.getHealth() - attackStat);
        }

        System.out.println(" Health: " + monster.getHealth());
    }

    public static int getAttackStat() {
        return attackStat;
    }

    public static void setAttackStat(int attackStat) {
        Player.attackStat = attackStat;
    }

    public static int getRoomX() {
        return roomX;
    }

    public static void setRoomX(int roomX) {
        Player.roomX = roomX;
    }

    public static int getRoomY() {
        return roomY;
    }

    public static void setRoomY(int roomY) {
        Player.roomY = roomY;
    }

    public static int getPosX() {
        return posX;
    }

    public static void setPosX(int posX) {
        Player.posX = posX;
    }

    public static int getPosY() {
        return posY;
    }

    public static void setPosY(int posY) {
        Player.posY = posY;
    }

    public static int getKillCount() {
        return killCount;
    }

    public static void incKill() {
        killCount++;
    }

    public static int getDmgTaken() {
        return dmgTaken;
    }

    public static void setDmgTaken(int dmgTaken) {
        Player.dmgTaken += dmgTaken;
    }

    public static int getPotionCount() {
        return potionCount;
    }

    public static void incPotion() {
        potionCount++;
    }

    public static int getGoldTotal() {
        return goldTotal;
    }

    /**
     * Buffs the attack of the Player by a certain multiplier for a certain duration.
     * @param potionDuration - how many attacks it lasts for
     */
    public static void buffAttack(int potionDuration) {
        attackBuffed = true;
        attackPotionDuration += potionDuration;
    }

    /**
     * Sets the player to be immune for a certain number of attacks.
     * @param potionDuration the number of attacks the player is immune for
     */
    public static void setImmune(int potionDuration) {
        immuneBuffed = true;
        immunePotionDuration += potionDuration;
    }

    /**
     * Returns the duration of the immune potion
     * @return if the player is buffed or not
     */
    public static boolean isImmuneBuffed() {
        return immuneBuffed;
    }

    /**
     * Uses a duration of the immune potion when hit by a monster.
     */
    public static void useImmunePotionDuration() {
        immunePotionDuration--;
        if (immunePotionDuration == 0) {
            immuneBuffed = false;
        }
    }

    public String getName() {
        return name;
    }

    public static void setName(String name) {
        Player.name = name;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Player.gender = gender;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(String difficulty) {
        Player.difficulty = difficulty;
    }

    public static String getWeapon() {
        return weapon;
    }

    public static void setWeapon(String weapon) {
        Player.weapon = weapon;
    }

    public static int getGold() {
        return gold;
    }

    public static void setGold(int gold) {
        goldTotal = gold;
        Player.gold = gold;
    }

    public static void addGold(int gold) {
        goldTotal += gold;
        Player.gold += gold;
    }

    public static void buyItem() {
        Player.gold -= 50;
    }

    public static int getXp() {
        return xp;
    }

    public static void setXp(int xp) {
        Player.xp = xp;
    }

    public static int getLevel() {
        return level;
    }

    public static void levelUp() {
        level++;
    }

    public static int getCurrentHp() {
        return currentHp;
    }

    public static void setCurrentHp(int currentHp) {
        Player.currentHp = currentHp;
    }

    public static int getMaxHp() {
        return maxHp;
    }

    public static void setMaxHp(int maxHp) {
        Player.maxHp = maxHp;
    }
}
