import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

public class LobbyController{

    @FXML
    public GridPane onlineUsersGrid;
    public GridPane challengedYouGrid;
    public Label uname;
    public ComboBox cbDifficultyAI;
    public CheckBox isAICheckbox;

    ArrayList<String> playerList;
    static boolean isLobbyWindowOpen;

    public LobbyController() {
        isLobbyWindowOpen = true;
        Platform.runLater(() -> {
            uname.setText(Controller.playerNamestring);
            cbDifficultyAI.getItems().addAll("Normaal", "Moeilijk");
            cbDifficultyAI.setOnAction(e -> setDifficulty(cbDifficultyAI));
            if (Game.isAI) {
                isAICheckbox.setSelected(true);
            }
        });
    }

    @FXML
    public void refresh() {
        System.out.println(Controller.challengers);
        updateOnlinePlayers();
        updateChallengedUs();
    }

    /**
     * deze methode ververst de lijst van mensen die ons uitdagen voor een spel
     */
    public void updateChallengedUs() {
        HashMap<String, HashMap>challengers = Controller.challengers;
        clearGrid(challengedYouGrid);
        int j =0;
        if(Controller.challengers != null) {
            for (String i : Controller.challengers.keySet()) {
                if (!nameExists(challengedYouGrid, i)) {
                    Label gebruikersnaam = new Label(i);
                    Label game = new Label((String) (challengers.get(i)).get("GAMETYPE"));
                    Button accepteer = new Button("Accepteer");
                    System.out.println((challengers.get(i)).get("CHALLENGENUMBER"));
                    String challengerNumber = (String) (challengers.get(i)).get("CHALLENGENUMBER");
                    accepteer.setOnAction(e -> {
                        try {
                            acceptChallenger(challengerNumber);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    challengedYouGrid.add(gebruikersnaam, 0, j + 1);
                    challengedYouGrid.add(game, 1, j + 1);
                    challengedYouGrid.add(accepteer, 2, j + 1);
                    j++;
                }
            }
        }
    }

    /**
     * deze methode ververst de lijst van online users
     */
    public void updateOnlinePlayers() {
        playerList = Controller.con.getPlayerlist();
        clearGrid(onlineUsersGrid);
        if (playerList == null){ //als de lijst met spelers leeg is gaan hebben we hier niks aan, dit is om een nullPointerError te voorkomen
            return;
        }
        for (String name : playerList) {
            if (!nameExists(onlineUsersGrid, name) && !name.equals(Controller.playerNamestring)) {

                // maak een label aan met als tekst de naam van de speler
                Label gebruikersnaam = new Label(name);

                // maak een combobox aan en voeg hier de games aan toe die gespeeld kunnen worden
                ComboBox chooseGame = new ComboBox();
                chooseGame.getItems().addAll("Boter, kaas en eieren", "Reversi");
                // als er geklikt wordt op een combo dan voeren we de methode
                // challenge uit en geven we het als parameter de ComboBox mee
                chooseGame.setOnAction(e -> challenge(chooseGame));

                int row = getRowCount(onlineUsersGrid); // haal het aantal rijen op

                // voeg de label en combobox toe aan de grid
                onlineUsersGrid.add(gebruikersnaam, 0, row);
                onlineUsersGrid.add(chooseGame, 1, row);
            }
        }
    }

    /**
     * deze methode reageert op welke handelingen er moeten gebeuren wanneer een andere speler wordt uitgedaagd
     *
     * @param cb is de ComboBox waarmee de gebruiker iemand mee heeft uitgedaagd
     */
    private void challenge(ComboBox cb) {

        String s = (String) cb.getValue();
        String game;
        String name = (String) stringPlayerChallenged(cb);

        System.out.println(stringPlayerChallenged(cb));
        switch (s) {//check for a match

            case "Boter, kaas en eieren":
                game = "Tic-tac-toe";
                System.out.println("Je hebt " + stringPlayerChallenged(cb) + " uigedaagd voor een potje " + game);
                Controller.con.challengePlayer(name, game); //stuur de playername en game naar de methode van Connection om te kunnen uitdagen
                break;

            case "Reversi":
                game = "Reversi";
                System.out.println("Je hebt " + stringPlayerChallenged(cb) + " uigedaagd voor een potje " + game);
                Controller.con.challengePlayer(stringPlayerChallenged(cb), game); //stuur de playername en game naar de methode van Connection om te kunnen uitdagen
                break;

            default:
                System.out.println("Geen match");
                break;
        }
    }

    /**
     * deze methode zoekt door alle children van de GridPane om de juiste naam van de uitgedaagde op te halen
     *
     * @param cb is de ComboBox waarmee de gebruiker iemand mee heeft uitgedaagd
     * @return (String) de naam van de speler die wij hebben uitgedaagd
     */
    private String stringPlayerChallenged(ComboBox cb) {
        int row = onlineUsersGrid.getRowIndex(cb);
        int col = 0;
        Node result;
        ObservableList<Node> childrens = onlineUsersGrid.getChildren();

        // loop door alle nodes van de GridPane om de playername van de uitgedaagde te vinden.
        for (Node node : childrens) {

            if (onlineUsersGrid.getRowIndex(node) != null && onlineUsersGrid.getColumnIndex(node) != null) {

                if (onlineUsersGrid.getRowIndex(node) == row && onlineUsersGrid.getColumnIndex(node) == col) {
                    // playername gevonden van de persoon die wij willen uitdagen
                    result = node;
                    Label lb = (Label) result;

                    return lb.getText(); //playername returnen
                }
            }
        }
        // niks gevonden, return null
        return null;
    }

    /**
     * deze methode verwijderd nodes van de bestaande GridPanes
     * wanneer deze gebruikers niet meer online zijn
     *
     * @param grid is de GridPane die we willen clearen
     */
    private void clearGrid(GridPane grid) {

        ObservableList<Node> childrens = grid.getChildren();
        Set<Node> deleteNodes = new HashSet<>();
        ArrayList<Integer> rowsThatStay = new ArrayList<>();

        // loop door alle nodes van de GridPane om de lijst leeg te maken.
        for (Node node : childrens) {
            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {
                if (grid.getRowIndex(node) != 0) {
                    if (grid.getColumnIndex(node) == 0) {

                        Label lb = (Label) node;
                        if (lb.getText() != null && playerList.contains(lb.getText())) {
                            // loop door alle namen van playerList

                            for (int i = 0; i < playerList.size(); i++) {
                                if (playerList.get(i).equals(lb.getText())) {
                                    rowsThatStay.add(grid.getRowIndex(node));
                                }
                            }
                        // deze player is niet meer online, dus verwijderen we hem uit de challengers list (als hij hierin zit)
                        } else {
                            if (Controller.challengers.containsKey(lb.getText())) {
                                Controller.challengers.keySet().removeIf(i -> i.equals(lb.getText()));
                            }
                        }
                    }
                    if (!rowsThatStay.contains(grid.getColumnIndex(node))) {
                        deleteNodes.add(node);
                    }
                }
            }
        }
        grid.getChildren().removeAll(deleteNodes);
    }

    /**
     * deze methode zorgt ervoor dat wanneer er op de accepteer knop is gedrukt dat
     * de server het seintje ontvangt dat wij tegen deze speler willen spelen
     *
     * @param challengerNumber is een Int die de server wilt ontvangen om
     *                         de twee spelers met elkaar te kunnen matchen
     */
    private void acceptChallenger(String challengerNumber) throws IOException {
        String challengernumber = challengerNumber;
        Controller.con.acceptChallenge(challengernumber);
    }

    /**
     * Wanneer deze methode wordt aangeroepen gaan we de methoden
     * updateOnlinePlayers en updateChallengedUs uitvoeren op de
     * JavaFX Thread
     */
    public void updateLobby() {
        Platform.runLater(() -> {
            updateOnlinePlayers();
            updateChallengedUs();
        });
    }

    /**
     * Zolang isLobbyWindowOpen true is, wordt updateLobby() steeds aangeroepen
     */
    public void startInfiniteUpdating()
    {
        // Create a Runnable
        Runnable task = new Runnable()
        {
            public void run()
            {
                while(isLobbyWindowOpen) {
                    updateLobby();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);

        // Start the thread
        backgroundThread.start();
    }

    /**
     * Deze methode geeft als return het aantal rijen van de mee gegeven GridPane
     *
     * @param grid is de GridPane waarvan we willen weten hoeveel rijen deze lang is.
     * @return (Int) geeft het aantal rijen van de hudige grid
     */
    private int getRowCount(GridPane grid) {
        int numRows = 0;
        ObservableList<Node> childrens = grid.getChildren();

        // loop door alle nodes van de GridPane om te checken hoeveel rijen er zijn
        for (Node node : childrens) {
            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) == 0) {
                numRows += 1;
            }
        }
        return numRows;
    }

    private boolean nameExists(GridPane grid, String name) {

        ObservableList<Node> childrens = grid.getChildren();

        // loop door alle nodes van de GridPane om te checken of de gevraagdee naam hierin staat.
        for (Node node : childrens) {
            if (grid.getRowIndex(node) != null && grid.getRowIndex(node) != 0) {
                if (grid.getColumnIndex(node) == 0) {
                    Label lb = (Label) node;
                    if (lb.getText().equals(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void playerIsAI() {
        if (!Game.isAI) {
            Game.isAI = true;
        } else {
            Game.isAI = false;
        }
    }

    private void setDifficulty(ComboBox cb) {
        String difficulty = (String) cb.getValue();

        switch (difficulty) {//check for a match

            case "Normaal":
                Platform.runLater(() -> {
                    AI.difficulty = 0; // verander de difficulty van de AI
                });
                break;

            case "Moeilijk":
                Platform.runLater(() -> {
                    AI.difficulty = 1; // verander de difficulty van de AI
                });
                break;

            default:
                break;
        }
    }
}
