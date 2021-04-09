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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Controller {

    public static TicTacToe ttt;
    public static Reversi reversi;
    public static ReversiController reversiController;
    public static Connection con;
    public static LobbyController lobbyController;;
    public TextField playerName;
    public static HashMap<String, HashMap> challengers;

//    public Connection con;

    public String playerNamestring;
    Stage window;
    public PrintWriter pr;
    public Socket s;
    public String serverMsg;
    public String cleanServermsg;
    public BufferedReader bf;
    public AI ai;
    //public TicTacToe game;
    public Boolean yourturn;
    private Game game;

    public Controller() {
        ttt = new TicTacToe();
        reversi = new Reversi();
        challengers = new HashMap<>();
    }

    public void startConnectie(String playerNamestring){
        //connectie in&output
        this.playerNamestring = playerNamestring;

        try {
            s = new Socket("localhost", 7789);

            this.pr = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        con = new Connection(playerNamestring, pr);
        System.out.println("ik ben speler " + playerNamestring);

        //Connection con2 = new Connection("Bassie");

        //Thread t1 = new Thread(con);
        //Thread t2 = new Thread(con2);

        //t1.start();

        //t2.start();
        Thread gamethread = new Thread(this::playGame); //weet niet hoe dit werkt, intelliJ deed het automatisch :S
        gamethread.start();

    }

    public void playGame() {
        //con.selectGame("Tic-tac-toe");
        //System.out.println(con.getCleanServermsg());
        //System.out.println(con.getCleanServermsg());
        //con.selectGame("Tic-tac-toe");

        pr.println("login " + playerNamestring);
        pr.flush();


        try {
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            bf = new BufferedReader(in);

            int i = 0;
            while ((serverMsg = bf.readLine()) != null) {
                //serverMsg = bf.readLine();
                //System.out.println(serverMsg);
                cleanServermsg = con.dissect(serverMsg);

                System.out.println("servermsg " + i + " = " + cleanServermsg);
                System.out.println(serverMsg);
                useServerMessage(cleanServermsg);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void useServerMessage(String serverMsg) {
        switch (serverMsg) {
            case "OK":
                //niks dit betekend dat een commando succesvol is uitgevoerd, hoef je eigenlijk niks mee te doen
                break;

            case "SVR GAME MATCH":
                //System.out.println(serverMsg);
                //System.out.println(con.getMsgHashMap());
                //je weet nu dat je een hashmap kan krijgen met welke speler er nu aan de beurt is, het speltype en de naam van je tegenstander
                //{GAMTYPE: "<speltype>", PLAYERTOMOVE: "<naam speler1>", OPPONENT: "<naam tegenstander>"}

                //System.out.println(con.getMsgHashMap().get("PLAYERTOMOVE"));
                //System.out.println(con.getMsgHashMap().get(" OPPONENT")); //Er moet een spatie voor de keys die niet het eerste in de hashmap staan. (dissect() moet daar nog op worden aangepast)


                Object playertomove = con.getMsgHashMap().get("PLAYERTOMOVE");

                Object gameType = con.getMsgHashMap().get("GAMETYPE");

                if (gameType == "Tic-tac-toe"){
                    game = ttt;
                }
                else{
                    game = reversi;
                    reversi.resetBoard();
                    //System.out.println("REVERSI");
                }

                ai = new AI(game);


                //System.out.println(con.getMsgHashMap());
                //System.out.println("playernaam = " + playerNamestring);
                if (playertomove.equals(playerNamestring)) {
                    break;
                } else {
                    reversi.changePiece();
                    //System.out.println("ik doe een zet match start");
                    //int move = ai.makeMove();
                    //con.makeMove(move);
                    //game.updateBoard(move);
                    //yourturn = false;
                }

                break;

            case "SVR GAME YOURTURN":
                System.out.println("Het is mijn beurt");
                //System.out.println(con.getMsgHashMap());
                //hashmap: {TURNMESSAGE: "<bericht voor deze beurt>"}
                //System.out.println(serverMsg);
                //int move = ai.makeMove();
                //con.makeMove(move);
                //game.updateBoard(move);
                //yourturn = true;
                int move3 = ai.makeMove();
                con.makeMove(move3);
                //game.updateBoard(move3);
                //System.out.println("ik, " + playerNamestring + ", zet " + move3);
                yourturn = false;


                break;

            case "SVR GAME MOVE":
                System.out.println("GAME MOVE");
                //System.out.println(con.getMsgHashMap());
                //hashmap: {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
                //System.out.println(serverMsg);
                Object move1 = con.getMsgHashMap().get("MOVE");
                String move2 = (String) move1;
                int pos = Integer.parseInt(move2);

                Object player = con.getMsgHashMap().get("PLAYER");
                String player1 = (String) player;
                if (!player1.equals(playerNamestring)){
                    reversi.changePiece();
                    reversi.makeMove(pos);
                    reversi.changePiece();
                }
                else{reversi.makeMove(pos);}

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
                System.out.println(con.getMsgHashMap());
                //lobbyController.updateChallengers(con.getMsgHashMap());
                String challengerName = (String) con.getMsgHashMap().get("CHALLENGER");
                challengers.put(challengerName, con.getMsgHashMap());
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
                break;

        }


    }






    // View

    public void changeScreenChooseGame(ActionEvent actionEvent) throws IOException {

        if (!playerName.getText().isEmpty()) {
            // stuur playerName naar connection
            startConnectie(playerName.getText());

            lobbyController = new LobbyController();
            Parent nextParent = FXMLLoader.load(getClass().getResource("Lobby.fxml"));

            Scene nextScene = new Scene(nextParent);

            window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(nextScene);
            window.setResizable(false);
            window.setTitle("Game Lobby");

            window.show();

        }
        // else vul naam in!
    }

    public void changeScreenTicTacToe(ActionEvent actionEvent) throws IOException {
        game = new TicTacToe();

        Parent nextParent = FXMLLoader.load(getClass().getResource("TicTacToe.fxml"));
        Scene nextScene = new Scene(nextParent);
//
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(nextScene);
        window.setResizable(true);
        window.setTitle("Boter, Kaas en Eieren");

        window.show();
    }

    public void reversiView() throws IOException {
        game = new Reversi();

        reversiController = new ReversiController();

        Parent nextParent = FXMLLoader.load(getClass().getResource("Reversi.fxml"));
        Scene nextScene = new Scene(nextParent);
//
//        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(nextScene);
        window.setResizable(false);
        window.setTitle("Reversi");

        window.show();
    }

    // Model


}
