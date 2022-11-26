import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Main;
import sample.player.Player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class M6TestController extends ApplicationTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private Main main = new Main();

    @Before
    public void setStreams() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void restoreInitialStreams() {
        System.setOut(originalOut);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        main.start(primaryStage);
    }
    @Test
    public void testStartingGold() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        assertEquals(200, Player.getGold());
    }

    @Test
    public void testPlayer() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#playerImage", NodeMatchers.isNotNull());
    }

    @Test
    public void testBossRoom() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#BossRoom", NodeMatchers.isNotNull());
    }

    @Test
    public void testBoss1() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#Boss1", NodeMatchers.isNotNull());
    }

    @Test
    public void testBoss2() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#Boss2", NodeMatchers.isNotNull());
    }

    @Test
    public void testChallengeRoom1() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("Challenge1", NodeMatchers.isNotNull());
    }

    @Test
    public void testChallengeRoom2() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#Challenge2", NodeMatchers.isNotNull());
    }

    @Test
    public void testChallengeRoom3() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#Challenge3", NodeMatchers.isNotNull());
    }

    @Test
    public void testEndGameScreen() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#EndGame", NodeMatchers.isNotNull());
    }

    @Test
    public void testUseAttackPotion() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.DIGIT2);
        assertTrue(out.toString().contains("Trying to use an attack potion."));
    }

    @Test
    public void testUseImmunity() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.DIGIT3);
        assertTrue(out.toString().contains("Trying to use an immunity potion."));
    }

    @Test
    public void testSwitchWeapon() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.O);
        assertFalse(out.toString().contains("Switched to W2."));
    }

    @Test
    public void testImmunityPotion() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#defensePotion", NodeMatchers.isNotNull());
    }

    @Test
    public void testUseHealth() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.DIGIT1);
        assertTrue(out.toString().contains("Trying to use a health potion."));
    }
}