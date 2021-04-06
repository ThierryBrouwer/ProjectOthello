//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.*;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//
//public class Gui{
//
//    Stage window;
//    Pane gameWindow = new Pane();
//    Pane connectWindow = new Pane();
//    Pane menu = new Pane();
//    //String playerName;
//    //Games gameName;
//    //menu>klik spel>tictactoe>getBoardsize
//    Tile[] board;
//    TextField tF;
//    Button btn;
//
//    //playerName = textField.toString();
//     //   System.out.println(playerName);
//    public Parent createGameWindow() {
//        //window.setTitle("Game Window");
//
//        int windowSize = 600;
//        gameWindow.setPrefSize(windowSize,windowSize);
//
//        Tile[] board = new Tile[9];
//        double sizeOfTiles = Math.sqrt(9);
//        double sizeOfTile = windowSize/sizeOfTiles;
//        int temp = 0;
//
//        // loop door rows
//        for (int y = 0; y < 3; y++) {
//            // loop door columns
//            for (int x = 0; x < 3; x++) {
//                Tile tile = new Tile();
//                tile.setTranslateX(x * sizeOfTile);
//                tile.setTranslateY(y * sizeOfTile);
//
//                // voeg tiles toe aan root
//                gameWindow.getChildren().add(tile);
//
//                board[temp] = tile;
//                temp++;
//            }
//        }
//        return gameWindow;
//    }
//
//
//}
