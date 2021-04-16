import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    public Label lblStateOfGame;
    public AnchorPane popUpAfterGame;

    /**
     * De constructor van de TicTacToeController, maakt een background thread voor de Gui aan
     * Hier word ook updateView aangeroepen om het bord op te halen
     * isTttOpen boolean wordt op true gezet en refreshTurnNonStop() wordt aangeroepen, deze stopt pas als isTttOpen op false wordt gezet
     */
    public TicTacToeController() {
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

    /**
     * Deze methode wordt gebruikt als er op het speelveld wordt geklikt door de speler
     * Er wordt gekeken of het onze beurt is, zo ja dan gebruiken we de makeMove methode
     * @param actionEvent actionEvent geeft de input door van de speler
     */
    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button) actionEvent.getSource();

        //send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 3;
        int index = row * columnsInRow + column;

        if (Game.turn.equals(Game.ourUsername)) {
            Controller.ttt.makeMove(index, true);
        }
    }

    /**
     * De updateview haalt met name informatie op van Reversi.java
     * en update met behulp hiervan het board
     */

    /**
     * Deze methode haalt informatie uit de TicTacToe klasse en update de Gui
     */
    public void updateView() {
        // sla het huidige board op in int[] board
        int[] board = Controller.ttt.boardConvertto1d();

        // maak een ObservableList met nodes, zodat we door alle nodes van de grid kunnen loopen
        ObservableList<Node> childrens = grid.getChildren();

        for (Node node : childrens) { // loop door alle nodes van de grid

            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) { // check of de row en column index niet null zijn, nullpointer exceptions te voorkomen

                for (int i = 0; i < board.length; i++) { // loop door de lengte van het board

                    int row = i / 3;
                    int column = i - 3 * row;

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {

                        if (board[i] == 0) { // dit vakje is door niemand in bezit
                            // cast de node naar een button, zodat we de styling hiervan kunnen veranden
                            Button button = (Button) node;
                            button.setStyle("-fx-background-color: green;"); // zet style background-color green
                            break;
                        }
                        if (board[i] == 1) {
                            // cast de node naar een button, zodat we de styling hiervan kunnen veranden
                            Button button = (Button) node;
                            // we geven de button een style met image
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/blackX_160px.png')");
                            break;
                        }
                        if (board[i] == 2) {
                            // cast de node naar een button, zodat we de styling hiervan kunnen veranden
                            Button button = (Button) node;
                            // we geven de button een style met image
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/blackCircle_160px.png')");
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Methode die doorgeeft aan de Connection dat je wilt opgeven
     */
    public void playerRequestsForfeit() {
        Controller.con.forfeit();
    }

    /**
     * Deze methode wordt aangeroepen wanneer je in de Gui op "Ga terug" klikt, dan wordt de boolean isTttOpen op false gezet en openen we LobbyController.java
     */
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

    /**
     * Methode die de turn voor de Gui update
     */
    public void updateTurn() {
        Platform.runLater(() -> {
            lblBeurt.setText(Game.turn);
        });
    }

    /**
     * Deze methode draait de heletijd draaien totdat er een ander scherm geopend wordt
     */
    public void refreshTurnNonStop() {
        // Create a Runnable
        Runnable task = new Runnable() {
            public void run() {
                while (isTttOpen) {
                    updateTurn();

                    if (!Game.isGameRunning) {
                        btnBack.setVisible(true);
                        btnBack.setDisable(false);
                        btnForfeit.setDisable(true);
                        btnForfeit.setVisible(false);

                        Platform.runLater(() -> { // draai de volgende instructies op de JavaFX Thread

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
