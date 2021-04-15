import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TicTacToeController {

    public static boolean isTttOpen;
    public Label loggedInAs;
    public Button btnBack;
    public Button btnForfeit;

    @FXML
    private GridPane grid;
    public Label lblPlayer1;
    public Label lblPlayer2;
    public Label lblBeurt;

    public TicTacToeController(){
// default waarden van labels veranderen
        Platform.runLater(() -> {
            lblPlayer1.setText(Game.player1);
            lblPlayer2.setText(Game.player2);
            lblBeurt.setText(Game.player1);
            loggedInAs.setText(Game.ourUsername);
            btnBack.setVisible(false);

            updateView();
        });
        isTttOpen = true;
        refreshTurnNonStop();
    }



    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();

        //send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 3;
        int index = row*columnsInRow+column;

        if(Game.turn.equals(Game.ourUsername)){
            Controller.ttt.makeMove(index, true);
        }
    }

    public void updateView() {
        int[] board = Controller.ttt.boardConvertto1d();
        Node result;
        ObservableList<Node> childrens = grid.getChildren();

        // update labels met de goede informatie
        Platform.runLater(() -> {

            // update beurt
            if (Controller.ttt.getPiece() == 1) {
                lblBeurt.setText(Game.player1);
            } else {
                lblBeurt.setText(Game.player2);
            }
        });

        for (Node node : childrens) {

            if(grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {

                for (int i=0; i < board.length ; i++) {

                    int row = i / 3;
                    int column = i-3*row;
                    //System.out.println(row + ", " + column + ", " + grid.getRowIndex(node) + ", " + grid.getColumnIndex(node));

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {

                        if(board[i] == 0) {

                            result = node;
                            Button button = (Button) result;
                            button.setText("");
                            break;
                        }
                        if(board[i] == 1) {

                            result = node;
                            Button button = (Button) result;
                            button.setText("X");
                            button.setStyle("-fx-background-color: green; -fx-font-size: 50");
                            break;
                        }
                        if(board[i] == 2) {

                            result = node;
                            Button button = (Button) result;
                            button.setText("O");
                            button.setStyle("-fx-background-color: green; -fx-font-size: 50");
                            break;
                        }
                    }
                }
            }
        }
    }

    public void playerRequestsForfeit() {
        Controller.con.forfeit();
    }

    public void playerRequestsBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane p = fxmlLoader.load(getClass().getResource("Lobby.fxml").openStream());
            Controller.lobbyController = fxmlLoader.getController();
            Scene nextScene = new Scene(p);

            Controller.window.setScene(nextScene);
            Controller.window.setResizable(false);
            Controller.window.setTitle("Game Lobby");

            Controller.window.show();

            // zet updaten beurt uit
            isTttOpen = false;

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            Platform.runLater(Controller.lobbyController::startInfiniteUpdating);

        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }

    public void updateTurn() {
        Platform.runLater(() -> {
            lblBeurt.setText(Game.turn);
        });
    }

    public void refreshTurnNonStop()
    {
        // Create a Runnable
        Runnable task = new Runnable()
        {
            public void run()
            {
                while(isTttOpen) {
                    updateTurn();

                    if (!Game.isGameRunning) {
                        btnBack.setVisible(true);
                        btnBack.setDisable(false);
                        btnForfeit.setDisable(true);
                        btnForfeit.setVisible(false);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // start the thread
        backgroundThread.start();
    }

}
