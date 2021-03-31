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

    Gui gui;
    Board board;

    public Controller() {

        //startGUI();
        gui = new Gui();

    }

    public void startConnectie(String playerName) {
        //connectie in&output

        con = new Connection(playerName);

        Connection con2 = new Connection("Bassie");

        Thread t1 = new Thread(con);
        Thread t2 = new Thread(con2);

        t1.start();

        t2.start();

        Thread gamethread = new Thread(this::playGame); //weet niet hoe dit werkt, intelliJ deed het automatisch :S
        gamethread.start();

    }

    public void playGame(){
        TicTacToe game = new TicTacToe();
        con.selectGame("Tic-tac-toe");
        System.out.println(con.getCleanServermsg());

        while(true){
            String servermsg = con.getservermsg();
            System.out.println(servermsg);
            if (servermsg.contains("YOURTURN")){
                System.out.println(servermsg);
                break;
            }

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
