import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LobbyController{
    public GridPane onlineUsersGrid;
    public GridPane challengedYouGrid;
    ArrayList<String> playerList;
    HashMap<String, HashMap> challengers;


    public LobbyController() {
        challengers = new HashMap<String, HashMap>();
    }


    @FXML
    public void ververs(ActionEvent actionEvent) {
        // code needed for refreshing playerlist
        updateOnlinePlayers();
        //updateChallengedUs();

    }

    // deze methode ververst de lijst van mensen die ons uitdagen voor een spel
    public void updateChallengedUs(HashMap hm) {
        //clearOnlineUsers(challengedYouGrid);
//        for ( Object map : hm.keySet() ){
//            System.out.println(map);
//        }

        String challengerName = (String) hm.get("CHALLENGER");
        challengers.put(challengerName, hm);

        int j =0;
        for(String i : challengers.keySet()) {
            System.out.println("--------------------------------------------------------------------" + (String)i);
            Label gebruikersnaam = new Label(i);
            Label game = new Label((String) (challengers.get(i)).get("GAMETYPE"));
            Button accepteer = new Button("Accepteer");
            System.out.println((challengers.get(i)).get("CHALLENGENUMBER"));
            String challengerNumber = (String) (challengers.get(i)).get("CHALLENGENUMBER");
            accepteer.setOnAction(e -> acceptChallenger(challengerNumber));

            challengedYouGrid.add(gebruikersnaam, 0, j+1);
            challengedYouGrid.add(game, 1, j+1);
            challengedYouGrid.add(accepteer, 2, j+1);
            j++;
        }
    }

    // deze methode ververst de lijst van online users
    public void updateOnlinePlayers() {
        // onlineUsersGrid.getChildren().clear();
        clearOnlineUsers(onlineUsersGrid);
        playerList = Controller.con.getPlayerlist();

        for (int i = 0; i < playerList.size(); i++) {

            Label gebruikersnaam = new Label(playerList.get(i));
            ComboBox chooseGame = new ComboBox();
            chooseGame.getItems().addAll("Boter, kaas en eieren", "Reversi");
            chooseGame.setOnAction(e -> challenge(chooseGame));

            onlineUsersGrid.add(gebruikersnaam, 0, i+1);
            onlineUsersGrid.add(chooseGame, 1, i+1);
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
                    deleteNodes.add(node);
                }
            }
        }
        grid.getChildren().removeAll(deleteNodes);
    }

    private void acceptChallenger(String challengerNumber) {
        String challengernumber = challengerNumber;
        Controller.con.acceptChallenge(challengernumber);
    }
}
