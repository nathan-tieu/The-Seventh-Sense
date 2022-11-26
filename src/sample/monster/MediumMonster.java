package sample.monster;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;

public class MediumMonster extends Monster {
    /**
     * Constructs an EasyMonster using the super keyword.
     */
    public MediumMonster() {
        super("Medium", 2, 5, 20);
    }

    /**
     * Override the attack method with a simple attack with a crit chance.
     */
    @Override
    public void attack() {
        int critFactor = 1;
        if (Math.random() < 0.2) {
            critFactor = 2;
        }
        int damage = this.getAttack() * critFactor;
        int newHP = Player.getCurrentHp() - damage;
        Player.setCurrentHp(newHP);
        Player.setDmgTaken(damage);
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.SALMON,
                CornerRadii.EMPTY, Insets.EMPTY));
    }
}