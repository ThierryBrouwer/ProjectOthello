import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;


public class ReversiController {

    @FXML
    private GridPane grid;
    public Label lblPlayer1;
    public Label lblPlayer2;
    public Label lblBlackPoints;
    public Label lblWhitePoints;
    public Label lblBeurt;

    private String player1;
    private String player2;

    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();

        //send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 8;
        int index = row*columnsInRow+column;

        Controller.reversi.makeMove(index);
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

    public void setPlayer1(String player1) {
        this.player1 = player1;
        Platform.runLater(() -> {
            lblPlayer1.setText(player1);
        });
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
        Platform.runLater(() -> {
            lblPlayer2.setText(player2);
        });
    }

    public void playerRequestsBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane p = fxmlLoader.load(getClass().getResource("Lobby.fxml").openStream());
            Controller.lobbyController = fxmlLoader.getController();
            Scene nextScene = new Scene(p);

            Controller.window.setScene(nextScene);
            Controller.window.setResizable(false);
            Controller.window.setTitle("Game Lobby");

            Controller.window.show();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            Platform.runLater(Controller.lobbyController::startInfiniteUpdating);

        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }
}
