import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ReversiController {

    public GridPane grid;

    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();

         //button.setStyle("-fx-background-color: green; -fx-background-image: url('Images/WhiteCircle113.png')");

        //send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 3;
        int index = row*columnsInRow+column;

        if (Controller.ttt.isMoveLegal(index)) {
            Controller.ttt.updateBoard(index);
            updateView(Controller.ttt.getBoard());
        }

//        //updateBoard() testen
//        int[] arr = new int[64];
//        for (int i = 0; i < 64; i++) {
//            arr[i] = (int)(Math.random() * ((2-0) + 1)) + 0;
//        }
//
//        updateBoard(arr);
    }

    public void updateView(int[] board) {

        Node result;
        ObservableList<Node> childrens = grid.getChildren();

        for (Node node : childrens) {

            if (grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {

                for (int i = 0; i < board.length; i++) {

                    int row = i / 8;
                    int column = i - 8 * row;
                    //System.out.println(row + ", " + column + ", " + grid.getRowIndex(node) + ", " + grid.getColumnIndex(node));

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {

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