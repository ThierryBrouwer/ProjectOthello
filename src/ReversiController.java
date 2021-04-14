import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;


public class ReversiController {

    @FXML
    private GridPane grid;

    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();

         //button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/WhiteCircle113.png')");

        //send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 8;
        int index = row*columnsInRow+column;

//        if (Controller.reversi.makeMove(index)) {
//            Controller.reversi.updateBoard(index);
//            updateView(Controller.reversi.getBoard());
//        }

        Controller.reversi.makeMove(index,true);

//        //updateBoard() testen
        int[] array1d = new int[64];
//        for (int i = 0; i < 64; i++) {
//            arr[i] = (int)(Math.random() * ((2-0) + 1)) + 0;
//        }
//
//        updateBoard(arr);
        int[][] array2d = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                index = i*8+j;
                array2d[i][j] = array1d[index];
            }
        }
    }

    public void updateView() {

        int[] board = Controller.reversi.boardConvertto1d();
        Node result;
        System.out.println(grid);
        ObservableList<Node> childrens = grid.getChildren();

        for (Node node : childrens) {

            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {

                for (int i = 0; i < board.length; i++) {

                    int row = i / 8;
                    int column = i - 8 * row;
                    //System.out.println(row + ", " + column + ", " + grid.getRowIndex(node) + ", " + grid.getColumnIndex(node));

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
                        System.out.println(board[i]);

                        if (board[i] == 0) {

                            result = node;
                            Button button = (Button) result;
                            button.setStyle("-fx-background-color: green;");
                            break;
                        }
                        if (board[i] == 1) {

                            result = node;
                            Button button = (Button) result;
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/BlackCircle113.png')");
                            break;
                        }
                        if (board[i] == 2) {

                            result = node;
                            Button button = (Button) result;
                            button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/WhiteCircle113.png')");
                            break;
                        }
                    }
                }
            }
        }
    }

    public void playerRequestsForfeit(ActionEvent actionEvent) {
        Controller.con.forfeit();
    }
}
