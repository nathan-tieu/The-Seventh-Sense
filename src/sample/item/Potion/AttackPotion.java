package sample.item.Potion;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;

public class AttackPotion extends Potion {
    private static int potionCount = 2;
    private static final int ATTACK_BUFF = 2;
    private static final int POTION_DURATION = 3;

    /**
     * Constructor for an attack potion.
     */
    public AttackPotion() {
        super("Attack Potion");
    }

    /**
     * Uses a potion and gets its effect.
     */
    public static void useAPotion() {
        if (canUseAttack()) {
            System.out.println("We are able to use an attack potion.");
            Player.buffAttack(POTION_DURATION);
            potionCount--;
            Player.incPotion();
            System.out.println("Potion count is now: " + potionCount);
            return;
        }
        System.out.println("We are unable to use an attack potion.");
    }

    /**
     * Returns a boolean value if a Player has enough potions to use.
     * @return if the player can use an attack potion
     */
    public static boolean canUseAttack() {
        return (potionCount > 0);
    }

    public static int getAttackBuff() {
        return ATTACK_BUFF;
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.CORAL,
                CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static void increasePotionCount() {
        potionCount++;
    }

    public static void setPotionCount(int count) {
        potionCount = count;
    }

    public static int getPotionCount() {
        return potionCount;
    }

}