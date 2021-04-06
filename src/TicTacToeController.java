import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class TicTacToeController {
    @FXML
    public GridPane grid;

    public void playerRequestsMove(ActionEvent actionEvent) {

        Button button = (Button)actionEvent.getSource();

        //send index to check if move is valid
        int row = grid.getRowIndex(button);
        int column = grid.getColumnIndex(button);
        int columnsInRow = 3;
        int index = row*columnsInRow+column;

        if (Controller.ttt.isMoveLegal(index)) {
            Controller.ttt.updateBoard(index);
            updateView(Controller.ttt.getBoard());
            System.out.println(Controller.ttt.getBoard()[1]);
        }

//        for (int i=0; i<9; i++){
//            System.out.println(Controller.ttt.getBoard()[i]);
//        }

//        //updateBoard() testen
//        int[] arr = new int[9];
//        arr[0] = 1;
//        arr[1] = 2;
//        arr[2] = 2;
//        arr[3] = 2;
//        arr[4] = 1;
//        arr[5] = 0;
//        arr[6] = 1;
//        arr[7] = 2;
//        arr[8] = 0;
//
//        updateBoard(arr);
    }

    public void updateView(int[] board) {

        Node result;
        ObservableList<Node> childrens = grid.getChildren();

        for (Node node : childrens) {

            if(grid.getRowIndex(node) != null && grid.getColumnIndex(node) != null) {

                for (int i=0; i < board.length ; i++) {

                    int row = i / 3;
                    int column = i-3*row;
                    //System.out.println(row + ", " + column + ", " + grid.getRowIndex(node) + ", " + grid.getColumnIndex(node));

                    if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {

                        if(board[i] == 0) {

                            result = node;
                            Button button = (Button) result;
                            button.setText("");
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
}
