package org.example.tiktak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/BoardUi.fxml"));
        primaryStage.setTitle("Tic-Tac-Toe Game");
        Image image=new Image(getClass().getResourceAsStream("/images/icons8-tic-tac-toe-100.png"));
        primaryStage.getIcons().add(image);

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);

        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(null);

        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
