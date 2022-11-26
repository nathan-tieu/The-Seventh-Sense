package sample.monster;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;


public class BossMonster extends Monster {

    /**
     * Constructs an EasyMonster using the super keyword.
     */
    public BossMonster() {
        super("Boss", 2, 40, 0);
    }

    /**
     * Override the attack method with a basic attack.
     */
    @Override
    public void attack() {
        int critFactor = 1;
        double critRandom = Math.random();
        if (critRandom < 0.2) {
            critFactor = 3;
        } else if (critRandom >= 0.2 && critRandom < 0.6) {
            critFactor = 2;
        }
        int damage = this.getAttack() * critFactor;
        int newHP = Player.getCurrentHp() - damage;
        Player.setCurrentHp(newHP);
        Player.setDmgTaken(damage);
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.DARKGRAY,
                CornerRadii.EMPTY, Insets.EMPTY));
    }

}
