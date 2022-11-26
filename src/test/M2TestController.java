import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Main;

import static org.testfx.api.FxAssert.verifyThat;

public class M2TestController extends ApplicationTest {

    private Main main = new Main();

    @Override
    public void start(Stage primaryStage) throws Exception {
        main.start(primaryStage);
    }

    @Test
    public void testStartingRoom() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("Room: (1, 1)", NodeMatchers.isNotNull());
    }

    @Test
    public void testStartingRoomExit1() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#exitTop", NodeMatchers.isNotNull());
    }

    @Test
    public void testStartingRoomExit2() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#exitDown", NodeMatchers.isNotNull());
    }

    @Test
    public void testStartingRoomExit3() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#exitLeft", NodeMatchers.isNotNull());
    }

    @Test
    public void testStartingRoomExit4() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        verifyThat("#exitRight", NodeMatchers.isNotNull());
    }

    @Test
    public void testSecondRoomExit() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        clickOn("#exitRight");
        verifyThat("#exitDown", NodeMatchers.isNotNull());
    }

    @Test
    public void testThirdRoomExit() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        clickOn("#exitRight");
        clickOn("#exitDown");

        verifyThat("#exitRight", NodeMatchers.isNotNull());
    }

    @Test
    public void testFourthRoomExit() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        clickOn("#exitRight");
        clickOn("#exitDown");
        clickOn("#exitRight");

        verifyThat("#exitRight", NodeMatchers.isNotNull());
    }

    @Test
    public void testFifthRoomExit() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        clickOn("#exitRight");
        clickOn("#exitDown");
        clickOn("#exitRight");
        clickOn("#exitRight");

        verifyThat("#exitDown", NodeMatchers.isNotNull());
    }

    @Test
    public void testSixthRoomExit() {
        clickOn("#startButton");
        clickOn("#namefield").write("Seventh Sense");
        clickOn("#btnContinue");
        clickOn("#exitRight");
        clickOn("#exitDown");
        clickOn("#exitRight");
        clickOn("#exitRight");
        clickOn("#exitDown");

        verifyThat("#exitDown", NodeMatchers.isNotNull());
    }
}
