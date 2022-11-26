package sample.monster;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;

public class HardMonster extends Monster {
    /**
     * Constructs an EasyMonster using the super keyword.
     */
    public HardMonster() {
        super("Hard", 3, 10, 50);
    }

    /**
     * Override the attack method with a simple attack with various crit values.
     */
    @Override
    public void attack() {
        int critFactor = 1;
        double critRandom = Math.random();
        if (critRandom < 0.1) { // Super crit
            critFactor = 3;
        } else if (critRandom >= 0.1 && critRandom < 0.3) { // Normal crit
            critFactor = 2;
        }
        int damage = this.getAttack() * critFactor;
        int newHP = Player.getCurrentHp() - damage;
        Player.setCurrentHp(newHP);
        Player.setDmgTaken(damage);
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.RED,
                CornerRadii.EMPTY, Insets.EMPTY));
    }
}
