package org.example.tiktak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/BoardUi.fxml"));
        primaryStage.setTitle("Tic-Tac-Toe Game");

        primaryStage.setScene(new Scene(root));  // Customize window size
        // Disable the ability to resize the window
        primaryStage.setResizable(false);
        // Disable full-screen mode explicitly
        primaryStage.setFullScreen(false);

        // Optionally, prevent the user from toggling full-screen
        primaryStage.setFullScreenExitHint("");  // Disable the default exit hint
        primaryStage.setFullScreenExitKeyCombination(null); // Disable key combo to toggle full-screen

        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
