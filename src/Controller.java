import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;
import java.io.IOException;

public class Controller {

    public TextField playerName;

    public Connection con;


    //Gui gui;
    Board board;

    public Controller() {

        //startGUI();
        //gui = new Gui();

    }

    public void startConnectie(String playerName) {
        //connectie in&output

        con = new Connection(playerName);

        //Connection con2 = new Connection("Bassie");

        Thread t1 = new Thread(con);
        //Thread t2 = new Thread(con2);

        t1.start();

        //t2.start();

        Thread gamethread = new Thread(this::playGame); //weet niet hoe dit werkt, intelliJ deed het automatisch :S
        gamethread.start();

    }

    public void playGame(){
        TicTacToe game = new TicTacToe();
        con.selectGame("Tic-tac-toe");
        //System.out.println(con.getCleanServermsg());
        //System.out.println(con.getCleanServermsg());
        int i = 0;
        con.selectGame("Tic-tac-toe");

        while(con.getCleanServermsg() != "exit"){
            String serverMsg = con.getCleanServermsg();

            /*if (i <= 100){
                System.out.println(servermsg);
                i+=1;
            }*/

            switch(serverMsg) {
                case "OK":
                    //niks dit betekend dat een commando succesvol is uitgevoerd, hoef je eigenlijk niks mee te doen
                    break;

                case "SVR GAME MATCH":
                    //System.out.println(serverMsg);
                    //System.out.println(con.getMsgHashMap());
                    //je weet nu dat je een hashmap kan krijgen met welke speler er nu aan de beurt is, het speltype en de naam van je tegenstander
                    //{GAMTYPE: "<speltype>", PLAYERTOMOVE: "<naam speler1>", OPPONENT: "<naam tegenstander>"}
                    break;

                case "SVR GAME YOURTURN":
                    //System.out.println(con.getMsgHashMap());
                    //hashmap: {TURNMESSAGE: "<bericht voor deze beurt>"}
                    break;

                case "SVR GAME MOVE":
                    //System.out.println(con.getMsgHashMap());
                    //hashmap: {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
                    break;

                case "SVR GAME LOSS":
                    //verloren :(
                    //System.out.println(con.getMsgHashMap());
                    //hashmap: {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Client disconnected"}
                    //het laatste item, COMMENT, kan "Player forfeited match" zijn of "Client disconnected"
                    break;

                case "SVR GAME WIN":
                    //gewonnen :)
                    //System.out.println(con.getMsgHashMap());
                    //hashmap: {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Client disconnected"}
                    break;

                case "SVR GAME CHALLENGE":
                    //System.out.println(con.getMsgHashMap());
                    //hashmap: {CHALLENGER: "Sjors", GAMETYPE: "Guess Game", CHALLENGENUMBER: "1"}
                    break;

                case "SVR GAME CHALLENGE CANCELLED":
                    //System.out.println(con.getMsgHashMap());
                    //hashmap: {CHALLENGENUMBER: "<uitdaging nummer>"}
                    break;

                case "(C) Copyright 2015 Hanzehogeschool Groningen":
                    //copyright melding van de Hanze
                    break;

                default:
                    System.out.println(serverMsg);

            }

            System.out.print(""); //heel vreemd, wanneer ik na de switch statement niets uitvoer word de hele switch statement niet uitgevoerd

        }

    }


    // View

    public void changeScreenChooseGame(ActionEvent actionEvent) throws IOException {

        if (!playerName.getText().isEmpty()) {
            // stuur playerName naar connection
            startConnectie(playerName.getText());


            Parent nextParent = FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
            Scene nextScene = new Scene(nextParent);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(nextScene);
            window.setResizable(false);
            window.setTitle("Kies je spel");

            window.show();

        }
        // else vul naam in!
    }

    public void changeScreenTicTacToe(ActionEvent actionEvent) throws IOException {

        Parent nextParent = FXMLLoader.load(getClass().getResource("Reversie.fxml"));
        Scene nextScene = new Scene(nextParent);
//
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(nextScene);
        window.setResizable(true);
        window.setTitle("Boter, Kaas en Eieren");

        window.show();
    }

    // Model


}
