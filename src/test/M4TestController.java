import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Main;
import sample.player.Player;

import static org.junit.Assert.assertEquals;


import static org.testfx.api.FxAssert.verifyThat;

public class M4TestController extends ApplicationTest {

    private Main main = new Main();

    @Override
    public void start(Stage primaryStage) throws Exception {
        main.start(primaryStage);
    }

    @Test
    public void testPlayer() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#playerImage", NodeMatchers.isNotNull());
    }

    @Test
    public void testPlayerHpStat() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#hpProgressBar", NodeMatchers.isNotNull());
    }

    @Test
    public void testMonsterHpLabel() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#hpLabel", NodeMatchers.isNotNull());
    }

    @Test
    public void testUpPosition() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.W);
        assertEquals(Player.getPosX(), 6);
        assertEquals(Player.getPosY(), 1);
    }

    @Test
    public void testLeftPosition() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.A);
        assertEquals(Player.getPosX(), 5);
        assertEquals(Player.getPosY(), 2);
    }

    @Test
    public void testDownPosition() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.S);
        assertEquals(Player.getPosX(), 6);
        assertEquals(Player.getPosY(), 3);
    }

    @Test
    public void testRightPosition() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.D);
        assertEquals(Player.getPosX(), 7);
        assertEquals(Player.getPosY(), 2);
    }

    @Test
    public void testMap() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.M);
        verifyThat("#mapPane", NodeMatchers.isNotNull());
    }

    @Test
    public void testStartingHpValue() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        assertEquals(10, Player.getCurrentHp());
    }

    @Test
    public void testStartingGold() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        assertEquals(200, Player.getGold());
    }
}
