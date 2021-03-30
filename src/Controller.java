import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    Connection con;
    Gui gui;

    public Controller() {
        startConnectie();
        //startGUI();
        gui = new Gui();
    }

    public void startConnectie() {
        //connectie in&output
        Connection con = new Connection("Henk");
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
    public void startGUI(){
        new Thread(() -> Application.launch(Gui.class)).start();

    }

    public void changeScreenChooseGame(ActionEvent actionEvent) throws IOException {
        Parent nextParent = FXMLLoader.load(getClass().getResource("GameWindow.fxml"));
        Scene nextScene = new Scene(nextParent);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(nextScene);
        window.setResizable(false);
        window.setTitle("Kies je spel");

        window.show();
    }

    public void changeScreenTicTacToe(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.hide();
        Parent root = gui.createGameWindow();
        Scene nextScene = new Scene(root);

        Stage window2 = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window2.setScene(nextScene);
        window2.setResizable(false);
        window2.setTitle("Boter, Kaas en Eieren");

        window2.show();
    }

    // Model


}
