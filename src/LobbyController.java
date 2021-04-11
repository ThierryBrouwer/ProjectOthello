import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

public class LobbyController{
    public GridPane onlineUsersGrid;
    public GridPane challengedYouGrid;
    ArrayList<String> playerList;


    public LobbyController() {
//        challengers = new HashMap<String, HashMap>();
        System.out.println(onlineUsersGrid);
    }

    @FXML
    public void ververs(ActionEvent actionEvent) {
        System.out.println(Controller.challengers);
        updateOnlinePlayers();
        updateChallengedUs();

    }

    // deze methode ververst de lijst van mensen die ons uitdagen voor een spel
    public void updateChallengedUs() {
        clearOnlineUsers(challengedYouGrid);
        int j =0;
        HashMap<String, HashMap>challengers = Controller.challengers;
        if(Controller.challengers != null) {
            for (String i : Controller.challengers.keySet()) {

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

    // deze methode ververst de lijst van online users
    public void updateOnlinePlayers() {
        playerList = Controller.con.getPlayerlist();
        clearOnlineUsers(onlineUsersGrid);

        for (int i = 0; i < playerList.size(); i++) {
            if (!nameExists(onlineUsersGrid, playerList.get(i)) && !playerList.get(i).equals(Controller.playerNamestring)) {
                if (!playerList.get(i).contains("Version 1.0")) {
                    Label gebruikersnaam = new Label(playerList.get(i));
                    ComboBox chooseGame = new ComboBox();
                    chooseGame.getItems().addAll("Boter, kaas en eieren", "Reversi");
                    chooseGame.setOnAction(e -> challenge(chooseGame));

                    int row = getRowCount(onlineUsersGrid);

                    onlineUsersGrid.add(gebruikersnaam, 0, row);
                    onlineUsersGrid.add(chooseGame, 1, row);
                }
            }
        }
    }

    // deze methode reageert op welke handelingen er moeten gebeuren wanneer een tegenstander wordt uitgedaagd.
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

    // deze methode zoekt door alle children van de GridPane om de juiste naam van de uitgedaagde op te halen
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

    private void clearOnlineUsers(GridPane grid) {
        Node result;
        ObservableList<Node> childrens = grid.getChildren();
        Set<Node> deleteNodes = new HashSet<>();

        // loop door alle nodes van de GridPane om de lijst leeg te maken.
        for (Node node : childrens) {
            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {
                if (grid.getRowIndex(node) != 0) {
                    if (grid.getColumnIndex(node) == 0) {

                        Label lb = (Label) node;
                        if (lb.getText() != null && playerList.contains(lb.getText())) {
                            break;
                        }
                        else {
                            System.out.println(playerList + "----------------> " + node.getId());
                            deleteNodes.add(node);
                        }
                    }
                }
            }
        }
        grid.getChildren().removeAll(deleteNodes);
    }

    private void acceptChallenger(String challengerNumber) throws IOException {
        String challengernumber = challengerNumber;
        Controller.con.acceptChallenge(challengernumber);
    }

    public void updateLobby() {
        Platform.runLater(() -> {
            updateOnlinePlayers();
            updateChallengedUs();
        });
    }

    public void startInfiniteUpdating()
    {
        // Create a Runnable
        Runnable task = new Runnable()
        {
            public void run()
            {
                while(true) {
                    updateLobby();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

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

        // loop door alle nodes van de GridPane om de lijst leeg te maken.
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



}
