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
import java.util.HashMap;

public class Controller {

    public TextField playerName;

    public Button button0;
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;
    public Button button7;
    public Button button8;
    public Connection con;
    public GridPane grid;

    public String playerNamestring;

    Gui gui;
    Board board;

    public Controller() {

        //startGUI();
        gui = new Gui();

    }

    public void startConnectie(String playerNamestring) {
        //connectie in&output
        this.playerNamestring = playerNamestring;
        con = new Connection(playerNamestring);
        System.out.println("ik ben speler " + playerNamestring);

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
        AI ai = new AI(game);
        //con.selectGame("Tic-tac-toe");
        //System.out.println(con.getCleanServermsg());
        //System.out.println(con.getCleanServermsg());
        //con.selectGame("Tic-tac-toe");

        while(con.getCleanServermsg() != "exit"){
            String serverMsg = con.getCleanServermsg();
            System.out.println(serverMsg);

            /*if (i <= 100){
                System.out.println(servermsg);
                i+=1;
            }*/
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e){e.printStackTrace();}

            switch(serverMsg) {
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
                   System.out.println(con.getMsgHashMap());
                    //System.out.println(playerNamestring);
                   if (!playertomove.equals(playerNamestring)){
                       break;}
                   else{
                       int move = ai.makeMove();
                       con.makeMove(move);
                   }

                    break;

                case "SVR GAME YOURTURN":
                    System.out.println(con.getMsgHashMap());
                    //hashmap: {TURNMESSAGE: "<bericht voor deze beurt>"}
                    //System.out.println(serverMsg);
                    int move = ai.makeMove();
                    con.makeMove(move);
                    con.getMsgHashMap();
                    break;

                case "SVR GAME MOVE":
                    System.out.println(con.getMsgHashMap());
                    //hashmap: {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
                    //System.out.println(serverMsg);
                    System.out.println(con.getMsgHashMap());
                    Object move1 = con.getMsgHashMap().get(" MOVE");
                    String move2 = (String) move1;
                    int pos =Integer.parseInt(move2);
                    game.updateBoard(pos);
                    if (!con.getMsgHashMap().get("PLAYERTOMOVE").equals(playerNamestring)){break;}
                    else{  int move3 = ai.makeMove();
                            con.makeMove(move3);}
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
//        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//        window.hide();
//        Parent root = gui.createGameWindow();
//        Scene nextScene = new Scene(root);

//        Parent nextParent = gui.createGameWindow();
//        Scene nextScene = new Scene(nextParent);
//        Stage window2 = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//        window2.setScene(nextScene);
//        window2.setResizable(true);
//        window2.setTitle("Boter, Kaas en Eieren");
//
//        window2.show();

        Parent nextParent = FXMLLoader.load(getClass().getResource("GameWindow.fxml"));
        Scene nextScene = new Scene(nextParent);
//
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(nextScene);
        window.setResizable(false);
        window.setTitle("Boter, Kaas en Eieren");

        window.show();
    }

    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();
        //String id = button.getId();
        //System.out.println(id);
        button.setText("X");
//        System.out.println(button);
//        System.out.println(grid.getRowIndex(button));
//        System.out.println(grid.getColumnIndex(button));

        //send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 3;
        int index = row*columnsInRow+column;

        //updateBoard() testen
        int[] arr = new int[9];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 2;
        arr[3] = 2;
        arr[4] = 1;
        arr[5] = 0;
        arr[6] = 1;
        arr[7] = 2;
        arr[8] = 0;

        updateBoard(arr);
    }

    public void updateBoard(int[] board) {

        Node result;
        ObservableList<Node> childrens = grid.getChildren();

        for (Node node : childrens) {

            if(grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {

                for (int i=0; i < board.length ; i++) {

                    int row = i / 3;
                    int column = i-3*row;
                    System.out.println(row + ", " + column + ", " + grid.getRowIndex(node) + ", " + grid.getColumnIndex(node));

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {

                        if(board[i] == 0) {

                            result = node;
                            Button button = (Button) result;
                            button.setText("-");
                            break;
                        }
                        if(board[i] == 1) {

                            result = node;
                            Button button = (Button) result;
                            button.setText("X");
                            break;
                        }
                        if(board[i] == 2) {

                            result = node;
                            Button button = (Button) result;
                            button.setText("O");
                            break;
                        }
                    }
                }
            }
        }
    }

    // Model


}
