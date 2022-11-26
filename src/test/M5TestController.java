import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class M5TestController extends ApplicationTest {
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
    public void testHealthPotion() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#healthPotion", NodeMatchers.isNotNull());
    }

    @Test
    public void testAttackPotion() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#attackPotion", NodeMatchers.isNotNull());
    }

    @Test
    public void testImmunityPotion() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#defensePotion", NodeMatchers.isNotNull());
    }

    @Test
    public void testWeapon1() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#weapon1", NodeMatchers.isNotNull());
    }

    @Test
    public void testWeapon2() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#weapon2", NodeMatchers.isNotNull());
    }

    @Test
    public void testWeapon3() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#weapon3", NodeMatchers.isNotNull());
    }

    @Test
    public void testUseHealth() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        press(KeyCode.DIGIT1);
        assertTrue(out.toString().contains("Trying to use a health potion."));
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
}
