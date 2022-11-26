package sample.config;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import sample.Main;
import sample.item.Weapon.Weapon1;
import sample.item.Weapon.Weapon2;
import sample.item.Weapon.Weapon3;
import sample.player.Player;

public class ConfigurationController {

    private ObservableList<String> genderList = FXCollections.observableArrayList("Female", "Male");
    private ObservableList<String> difficultyList =
            FXCollections.observableArrayList("Easy", "Normal", "Hard");
    private ObservableList<String> weaponList =
            FXCollections.observableArrayList("Weapon 1", "Weapon 2", "Weapon 3");

    private Main main;

    @FXML
    private TextField namefield;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private ChoiceBox<String> difficulty;
    @FXML
    private ChoiceBox<String> weapon;
    @FXML
    private Button btnContinue;

    @FXML
    private void initialize() {
        gender.setValue("Female");
        gender.setItems(genderList);
        difficulty.setValue("Normal");
        difficulty.setItems(difficultyList);
        weapon.setValue("Weapon 1");
        weapon.setItems(weaponList);
    }

    public void handleContinue() throws Exception {
        if (namefield.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Invalid Username");
            a.setContentText("Please enter a valid username.");
            a.showAndWait();
        } else {
            Player.setName(namefield.getCharacters().toString());
            Player.setGender(gender.getValue());
            Player.setDifficulty(difficulty.getValue());
            switch (difficulty.getValue()) {
            case "Easy":
                Player.setGold(300);
                break;
            case "Normal":
                Player.setGold(200);
                break;
            case "Hard":
                Player.setGold(100);
                break;
            default:
                break;
            }

            switch (weapon.getValue()) {
            case "Weapon 1":
                Weapon1.pickup();
                Weapon1.equip();
                break;
            case "Weapon 2":
                Weapon2.pickup();
                Weapon2.equip();
                break;
            case "Weapon 3":
                Weapon3.pickup();
                Weapon3.equip();
                break;
            default:
                break;
            }
            Player.resetPlayerStats();
            main.showRoomScreen();
        }
    }
}
