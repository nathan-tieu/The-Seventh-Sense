package sample.welcome;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import sample.Main;

public class WelcomeController {

    private Main main;

    @FXML
    private void goConfig() throws Exception {
        main.showConfigScreen();
    }

    @FXML
    private Button exitButton;

    @FXML
    private void exitButtonAction() throws Exception {
        // Get the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // Close the stage
        stage.close();
    }

}
