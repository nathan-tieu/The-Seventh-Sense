package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.room.RoomController;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static BorderPane mainLayout;

    private static final int SCREEN_WIDTH = 960;
    private static final int SCREEN_HEIGHT = 540;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dungeon Crawler");
        showWelcomeScreen();
    }

    public static void showWelcomeScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("welcome/WelcomeScreen.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showConfigScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("config/ConfigScreen.fxml"));
        BorderPane electricalDep = loader.load();
        mainLayout.setTop(electricalDep);
    }

    public static void showRoomScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("room/RoomTemplate.fxml"));
        BorderPane gameScreen = loader.load();
        mainLayout.setTop(gameScreen);
        RoomController controller = loader.getController();
        controller.focus();
    }

    public static void showWinScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("win/WinScreen.fxml"));
        Pane gameScreen = loader.load();
        mainLayout.setTop(gameScreen);
    }

    public static void showLoseScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gameover/GameOver.fxml"));
        Pane gameScreen = loader.load();
        mainLayout.setTop(gameScreen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
