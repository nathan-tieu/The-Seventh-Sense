package sample.item.Potion;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;

import static java.lang.Math.min;

public class HealthPotion extends Potion {
    private static int potionCount = 2;
    private static final int HEAL_AMOUNT = 3;

    /**
     * Constructor for a health potion.
     */
    public HealthPotion() {
        super("Health Potion");
    }


    public static void useHPotion() {
        if (canUseHeal()) {
            System.out.println("We are able to use a health potion.");
            int maxHP = Player.getMaxHp();
            int healedHP = Player.getCurrentHp() + HEAL_AMOUNT;
            Player.setCurrentHp(min(maxHP, healedHP));
            potionCount--;
            Player.incPotion();
            System.out.println("Potion count is now: " + potionCount);
            return;
        }
        System.out.println("We could not use a health potion.");
    }

    /**
     * Returns a boolean value if a Player can heal and is not at max health.
     * @return if can use the heal potion
     */
    public static boolean canUseHeal() {
        return (potionCount > 0 && Player.getCurrentHp() != Player.getMaxHp());
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.LIGHTGREEN,
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
