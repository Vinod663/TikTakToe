package org.example.tiktak.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.tiktak.Model.*;
import org.example.tiktak.Piece;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BoardController implements Initializable,BoardUi {

    @FXML
    private Button btn00;

    @FXML
    private Button btn01;

    @FXML
    private Button btn02;

    @FXML
    private Button btn10;

    @FXML
    private Button btn11;

    @FXML
    private Button btn12;

    @FXML
    private Button btn20;

    @FXML
    private Button btn21;

    @FXML
    private Button btn22;

    @FXML
    private ComboBox<String> gameModeComboBox;

    @FXML
    private TextField playerNameField;

    private BoardImpl board;
    private Player human;
    private AiPlayer ai;
    private boolean gameOver = false;


    @FXML
    public void handleButtonClick00() { handleButtonClick(0, 0); }
    @FXML
    public void handleButtonClick01() { handleButtonClick(0, 1); }
    @FXML
    public void handleButtonClick02() { handleButtonClick(0, 2); }
    @FXML
    public void handleButtonClick10() { handleButtonClick(1, 0); }
    @FXML
    public void handleButtonClick11() { handleButtonClick(1, 1); }
    @FXML
    public void handleButtonClick12() { handleButtonClick(1, 2); }
    @FXML
    public void handleButtonClick20() { handleButtonClick(2, 0); }
    @FXML
    public void handleButtonClick21() { handleButtonClick(2, 1); }
    @FXML
    public void handleButtonClick22() { handleButtonClick(2, 2); }

    private void handleButtonClick(int row, int col) {
        if (!gameOver && board.isLegalMove(row, col)) {
            human.move(row, col);
            board.printBoard();

            updateUI();

            //Api(X) play karrdi kawru hari dinalda kiyala check wenawa
            Piece winner = board.checkWinner();//X,O,null,Empty
            if (winner != null) {//handle  win and draw
                if (winner == Piece.EMPTY) {
                    endGame("It's a Draw!");  // Handle draw
                } else {
                    endGame(winner == Piece.X ? playerNameField.getText() + " wins!" : "You Lose!");//Handle the winner//AI wins!
                }
                return;
            }
            ///////////////////////////////////////////////////
            // If no winner call Ai
            ai.makeMove();//Ai move
            board.printBoard();
            updateUI();

            //Ai(0) play karrdi kawru hari dinalda kiyala check wenawa
            winner = board.checkWinner();
            if (winner != null) {
                if (winner == Piece.EMPTY) {
                    endGame("It's a Draw!");  // Handle draw
                } else {
                    endGame(winner == Piece.X ? playerNameField.getText() + " wins!" : "You Lose!");
                }
            }
        }
    }


    private void endGame(String message) {////////////////////////
        gameOver = true;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(message);
        alert.setContentText("Would you like to play again?");

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType exit = new ButtonType("Exit");

        alert.getButtonTypes().setAll(playAgain, exit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == playAgain) {
            resetGame();  // Start  new game
        } else {
            System.exit(0);  // Exit the game
        }
    }

    private void resetGame() {
       String selectedMode = gameModeComboBox.getValue();////////////////////////
        gameOver = false;
      board = new BoardImpl();
        board.initializeBoard();
       human = new HumanPlayer(board);
        updateUI();
        System.out.println(selectedMode);
      // String selectedMode = gameModeComboBox.getValue();
        if (selectedMode != null) {
            ai=new AiPlayer(board, selectedMode);
//            switch (selectedMode) {
//                case "Easy":
//                    ai = new AiPlayer(board, "easy");
//                    break;
//                case "Medium":
//                    ai = new AiPlayer(board, "medium");
//                    break;
//                case "Hard":
//                    ai = new AiPlayer(board, "hard");
//                    break;
//                default:
//                    ai = new AiPlayer(board, "medium");
//            }
        } else {
            ai = new AiPlayer(board, "Medium");
        }
//        updateUI();
    }
    @Override
    public void updateUI() {///////////////////
        updateButton(btn00, board.getPieceAt(0, 0));
        updateButton(btn01, board.getPieceAt(0, 1));
        updateButton(btn02, board.getPieceAt(0, 2));
        updateButton(btn10, board.getPieceAt(1, 0));
        updateButton(btn11, board.getPieceAt(1, 1));
        updateButton(btn12, board.getPieceAt(1, 2));
        updateButton(btn20, board.getPieceAt(2, 0));
        updateButton(btn21, board.getPieceAt(2, 1));
        updateButton(btn22, board.getPieceAt(2, 2));
    }
@Override
public void updateButton(Button button, Piece piece) {
        button.setStyle("-fx-background-color: #1DB954;");

        if (piece == Piece.X) {
            button.setText("X");
            button.setStyle(button.getStyle() + "-fx-text-fill: #ffffff;");  // White text for "X"
        } else if (piece == Piece.O) {
            button.setText("O");
            button.setStyle(button.getStyle() + "-fx-text-fill: #333333;");  // Dark gray text for "O"
        } else {
            button.setText("");
           // button.setStyle(button.getStyle() + "-fx-text-fill: transparent;");  // Transparent text for empty cells
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {/////////////////
        gameModeComboBox.getItems().addAll("Easy", "Medium", "Hard");  // Add game modes
        resetGame();
    }

    public void ResetOnAction(MouseEvent mouseEvent) {
//        String selectedMode = gameModeComboBox.getValue();
//        if (selectedMode != null) {
//            // Reinitialize AI when mode is changed
//            ai = new AiPlayer(board, selectedMode);
//        }
        resetGame();
    }


    public void ChangeMode(ActionEvent actionEvent) {
        resetGame();
    }
}
