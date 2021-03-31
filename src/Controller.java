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
import java.util.ArrayList;

public class Controller {

    public TextField playerName;

    public GridPane grid;
    Connection con;
    Gui gui;
    Board board;

    public Controller() {

        //startGUI();
        gui = new Gui();

    }

    public void startConnectie(String playerName) {
        //connectie in&output
        Connection con = new Connection(playerName);
        Connection con2 = new Connection("Bassie");

        Thread t1 = new Thread(con);
        Thread t2 = new Thread(con2);

        t1.start();
        t2.start();
    }

    public void uitpluizenlekkerbezig(){
        String msg = con.getservermsg();
        //uitpluizerarij
        //dingen voor gui worden later duidelijk met fxml
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

    public void changeButton(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();
        //String id = button.getId();
        //System.out.println(id);
        button.setText("X");
//        System.out.println(button);
//        System.out.println(grid.getRowIndex(button));
//        System.out.println(grid.getColumnIndex(button));
        updateBoard(2,2);

    }

    public void updateBoard(int row, int column) {

        Node result = null;
        ObservableList<Node> childrens = grid.getChildren();

        for (Node node : childrens) {
            if (grid.getRowIndex(node)!= null && grid.getColumnIndex(node)!= null) {
                if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
                    result = node;
                    break;
                }
            }
        }
        Button button = (Button) result;

        button.setText("X");
    }

    // Model


}
