package sample.item.Potion;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;


public class ImmunityPotion extends Potion {
    private static int potionCount = 2;
    private static final int POTION_DURATION = 3;

    /**
     * Constructor for an immunity potion.
     */
    public ImmunityPotion() {
        super("Immunity Potion");
    }

    public static void useIPotion() {
        if (canUseImmunity()) {
            System.out.println("We are able to use an immunity potion.");
            Player.setImmune(POTION_DURATION);
            potionCount--;
            Player.incPotion();
            System.out.println("Potion count is now: " + potionCount);
            return;
        }
        System.out.println("We are unable to use an immunity potion.");
    }

    /**
     * returns a boolean value if a Player has enough potions to use.
     * @return if can be immune
     */
    public static boolean canUseImmunity() {
        return (potionCount > 0);
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.VIOLET,
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
