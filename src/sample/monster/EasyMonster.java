package sample.monster;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.player.Player;


public class EasyMonster extends Monster {

    /**
     * Constructs an EasyMonster using the super keyword.
     */
    public EasyMonster() {
        super("Easy", 1, 1, 10);
    }

    /**
     * Override the attack method with a basic attack.
     */
    @Override
    public void attack() {
        int newHP = Player.getCurrentHp() - this.getAttack();
        Player.setCurrentHp(newHP);
        Player.setDmgTaken(this.getAttack());
    }

    @Override
    public Background getBackground() {
        return new Background(new BackgroundFill(Color.PINK,
                CornerRadii.EMPTY, Insets.EMPTY));
    }

}
