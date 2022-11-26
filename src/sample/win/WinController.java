package sample.win;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.player.Player;

import java.io.IOException;


public class WinController {
    @FXML
    private Label killLabel;
    @FXML
    private Label dmgLabel;
    @FXML
    private Label potionLabel;
    @FXML
    private Label goldLabel;
    @FXML
    private Button playAgainButton;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        killLabel.setText("" + Player.getKillCount());
        dmgLabel.setText("" + Player.getDmgTaken());
        potionLabel.setText("" + Player.getPotionCount());
        goldLabel.setText("" + Player.getGoldTotal());
    }

    @FXML
    private void goConfig(ActionEvent event) throws IOException {
        Player.setCurrentHp(15);
        Main.showConfigScreen();
    }

    @FXML
    private void exitButtonAction() throws Exception {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
