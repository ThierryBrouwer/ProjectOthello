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

    @FXML
    private GridPane grid;
    public Label lblPlayer1;
    public Label lblPlayer2;
    public Label lblBlackPoints;
    public Label lblWhitePoints;
    public Label lblBeurt;
    public Label loggedInAs;
    public Button btnBack;
    public Button btnForfeit;
    public AnchorPane popUpAfterGame;
    public Label lblStateOfGame;

    /**
     * Dit is de constructor van ReversiController. Hierin worden labels en buttons geset
     * Ook roepen we hierin de updateView methode aan om de begin situatie van het board
     * op te halen.
     * Daarbij roept de constructor de refreshTurnNonStop methode aan die constant gegevens op
     * blijft halen tot isReversiOpen op false wordt gezet.
     */
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
        refreshTurnNonStop(); // refresh elke x aantal seconden waarden in deze window
    }

    /**
     * De speler heeft op een veld geklikt. Dit veld/positie wordt omgerekend naar een 1d index.
     * Als het onze beurt is, sturen we deze index mee aan de makeMove functie van Reversi.java
     *
     * @param actionEvent de actionEvent wordt meegegeven om te achterhalen op welke knop is gedrukt
     */
    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource(); // returnt het object waarop het event plaatsvond

        // maak van een 2d positie een 1d positie
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 8;
        int index = row*columnsInRow+column;

        if (Game.turn.equals(Game.ourUsername)) { // check of het onze beurt is
            Controller.reversi.makeMove(index, true); // verzend de gewenste positie
        }
    }

    /**
     * De updateview haalt met name informatie op van Reversi.java
     * en update met behulp hiervan het board
     */
    public void updateView() {
        // sla het huidige board op in int[] board
        int[] board = Controller.reversi.boardConvertto1d();

        // maak een ObservableList met nodes, zodat we door alle nodes van de grid kunnen loopen
        ObservableList<Node> childrens = grid.getChildren();

        // update labels met de goede informatie van de punten
        lblBlackPoints.setText(Integer.toString(Controller.reversi.blackPoints()));
        lblWhitePoints.setText(Integer.toString(Controller.reversi.whitePoints()));

        for (Node node : childrens) { // loop door alle nodes van de grid

            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) { // check of de row en column index niet null zijn, nullpointer exceptions te voorkomen

                for (int i = 0; i < board.length; i++) { // loop door de lengte van het board

                    int row = i / 8;
                    int column = i - 8 * row;

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {

                        if (board[i] == 0) { // dit vakje is door niemand in bezit

                            // cast de node naar een button, zodat we de styling hiervan kunnen veranden
                            Button button = (Button) node;
                            button.setStyle("-fx-background-color: green;"); // maak het vakje groen
                            break;
                        }
                        if (board[i] == 1) { // dit vakje is door zwart in bezit

                            // cast de node naar een button, zodat we de styling hiervan kunnen veranden
                            Button button = (Button) node;
                            // maak het vakje groen en zet er een zwart steentje op
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/BlackCircle113.png')");
                            break;
                        }
                        if (board[i] == 2) { // dit vakje is door wit in bezit

                            // cast de node naar een button, zodat we de styling hiervan kunnen veranden
                            Button button = (Button) node;
                            // maak het vakje groen en zet er een wit steentje op
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/WhiteCircle113.png')");
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Wanneer de speler op de knop "Opgeven" drukt, wordt de server forfeit() mee gegeven
     * en is het potje hierbij ook afgelopen.
     */
    public void playerRequestsForfeit() {
        Controller.con.forfeit();
    }

    /**
     * Wanneer de speler op de knop "Terug" drukt, wordt de boolean isReversiOpen op false gezet
     * en openen we LobbyController.java
     */
    public void playerRequestsBack() {
        try {
            isReversiOpen = false; // zet de infinite loop om gegevens op te halen uit

            // open de window van de Lobby
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane p = fxmlLoader.load(getClass().getResource("Lobby.fxml").openStream());
            Controller.lobbyController = fxmlLoader.getController();
            Scene nextScene = new Scene(p);

            Controller.window.setScene(nextScene);
            Controller.window.setResizable(false);
            Controller.window.setTitle("Game Lobby");
            Controller.window.show();

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
            lblBeurt.setText(Game.turn); // set de beurt naar de persoon die aan de beurt is.
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
                        Thread.sleep(100); // we voeren elke x aantal miliseconden de bovenstaande code uit
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // voer de opdracht in een background thread uit
        Thread backgroundThread = new Thread(task);
        // start de thread
        backgroundThread.start();
    }
}
