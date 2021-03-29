import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Gui extends Application {

    Stage window;
    Pane gameWindow = new Pane();
    Pane connectWindow = new Pane();
    Pane menu = new Pane();
    //String playerName;
    //Games gameName;
    //menu>klik spel>tictactoe>getBoardsize
    Tile[] board;
    TextField tF;
    Button btn;

    //playerName = textField.toString();
     //   System.out.println(playerName);
    private Parent createGameWindow() {
        window.setTitle("Game Window");

        int windowSize = 600;
        gameWindow.setPrefSize(windowSize,windowSize);

        Tile[] board = new Tile[9];
        double sizeOfTiles = Math.sqrt(9);
        double sizeOfTile = windowSize/sizeOfTiles;
        int temp = 0;

        // loop door rows
        for (int y = 0; y < 3; y++) {
            // loop door columns
            for (int x = 0; x < 3; x++) {
                Tile tile = new Tile();
                tile.setTranslateX(x * sizeOfTile);
                tile.setTranslateY(y * sizeOfTile);

                // voeg tiles toe aan root
                gameWindow.getChildren().add(tile);

                board[temp] = tile;
                temp++;
            }
        }
        return gameWindow;
    }
    EventHandler<ActionEvent> test = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            window.setScene(new Scene(createGameWindow()));
        }

    };
    private Parent createConnectWindow() {
        connectWindow.setPrefSize(400, 200);
        window.setTitle("Verbinden");
        window.setResizable(false);
        
        Label name = new Label("Naam: ");
        tF = new TextField();
        btn = new Button("Verbinden");

        name.setLayoutX(90);
        name.setLayoutY(40);
        tF.setLayoutX(130);
        tF.setLayoutY(37.5);
        btn.setLayoutX(169);
        btn.setLayoutY(100);

        connectWindow.getChildren().addAll(name, tF, btn);

        btn.setOnAction(test);

        return connectWindow;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setScene(new Scene(createConnectWindow()));
        window.show();
    }
//    public static void main(String[] args) { launch(args); }
}
