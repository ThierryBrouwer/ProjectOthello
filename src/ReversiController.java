import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;


public class ReversiController {

    public static boolean isReversiOpen;
    public Label loggedInAs;
    public Button btnBack;
    public Button btnForfeit;
    public AnchorPane popUpAfterGame;
    public Label lblStateOfGame;

    @FXML
    private GridPane grid;
    public Label lblPlayer1;
    public Label lblPlayer2;
    public Label lblBlackPoints;
    public Label lblWhitePoints;
    public Label lblBeurt;

    public ReversiController() {
        // default waarden van labels veranderen
        Platform.runLater(() -> {
            lblPlayer1.setText(Game.player1);
            lblPlayer2.setText(Game.player2);
            lblBeurt.setText(Game.player1);
            loggedInAs.setText(Game.ourUsername);
            btnBack.setVisible(false);

            updateView();
        });
        isReversiOpen = true;
        refreshTurnNonStop();
    }

    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();

        // send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 8;
        int index = row*columnsInRow+column;

        if (Game.turn.equals(Game.ourUsername)) {
            Controller.reversi.makeMove(index, true);
        }
    }

    public void updateView() {

        int[] board = Controller.reversi.boardConvertto1d();
        Node result;
        ObservableList<Node> childrens = grid.getChildren();

        // update labels met de goede informatie

        lblBlackPoints.setText(Integer.toString(Controller.reversi.blackPoints()));
        lblWhitePoints.setText(Integer.toString(Controller.reversi.whitePoints()));
        // update beurt
        if (Controller.reversi.getPiece() == 1) {
            lblBeurt.setText(Game.player1);
        } else {
            lblBeurt.setText(Game.player2);
        }


        for (Node node : childrens) {

            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {

                for (int i = 0; i < board.length; i++) {

                    int row = i / 8;
                    int column = i - 8 * row;

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {

                        if (board[i] == 0) {

                            result = node;
                            Button button = (Button) result;
                            button.setStyle("-fx-background-color: green;");
                            break;
                        }
                        if (board[i] == 1) {

                            result = node;
                            Button button = (Button) result;
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/BlackCircle113.png')");
                            break;
                        }
                        if (board[i] == 2) {

                            result = node;
                            Button button = (Button) result;
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/WhiteCircle113.png')");
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
            isReversiOpen = false;

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
                while(isReversiOpen) {
                    updateTurn();

                    if (!Game.isGameRunning) {
                        btnBack.setVisible(true);
                        btnBack.setDisable(false);
                        btnForfeit.setDisable(true);
                        btnForfeit.setVisible(false);

                        Platform.runLater(() -> {
                            // als het potje is afgelopen geven we de user een pop up met daarin de staat van het potje
                            switch (Game.stateOfGame) {//check for a match

                                case 1: // we hebben gewonnen
                                    lblStateOfGame.setText("Je hebt gewonnen!");
                                    popUpAfterGame.setVisible(true);
                                    break;

                                case 2: // we hebben verloren
                                    lblStateOfGame.setText("Je hebt verloren");
                                    popUpAfterGame.setVisible(true);
                                    break;

                                case 3: // we hebben gelijk gespeeld
                                    lblStateOfGame.setText("Je hebt gelijk gespeeld");
                                    popUpAfterGame.setVisible(true);
                                    break;

                                default:
                                    break;
                            }
                        });

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
