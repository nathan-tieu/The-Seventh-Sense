package sample.item.Weapon;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;

public class Weapon1 extends Weapon {
    private static int weaponCount = 0;
    private static int currAttack = 1;

    /**
     * Constructor for an attack potion.
     */
    public Weapon1() {
        super("Weapon 1");
    }

    /**
     * Equip weapon 1.
     */
    public static void equip() {
        System.out.println("Current weapon attack: " + currAttack);
        Player.setAttackStat(currAttack);
        System.out.println("Current player attack: " + Player.getAttackStat());
        Player.setWeapon("Weapon 1");
    }

    /**
     * Increases the number of weapon 1's currently owned.
     */
    public static void pickup() {
        Weapon1.weaponCount++;
        if (weaponCount % 2 == 0) {
            currAttack++;
        }
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.YELLOW,
                CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static int getWeaponCount() {
        return weaponCount;
    }

    public static void resetWeaponCount() {
        weaponCount = 0;
        currAttack = 1;
    }
}
