import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Deze klasse is verantwoordelijk voor het opstarten van het hele framework
 *
 * @author
 * @version 14/4/2021
 */
public class Main extends Application{

    public static void main(String[] args) {
        Controller controller = new Controller();
        launch(args);

//        Reversi rev = new Reversi();
//
//        AI ai = new AI(rev);
//        ai.makeMove();
//        rev.changePiece();
//        ai.makeMove();
    }

    /**
     * Zorgt er voor dat de Stage iets ander is, wat verander is dat deze nu afgeleid is van Login.fxml en de titel van het eerste gui venster "Login" is.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
