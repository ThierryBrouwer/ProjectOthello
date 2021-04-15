import javafx.application.Platform;

public class TicTacToe extends Game {

    private int[] board;
    private int[][] board2d;
    private int row;
    private int col;
    private int piece;
    private Board b;

    public TicTacToe() {
        resetBoard();
    }

    public void makeBoard2d() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board2d[i][j] = this.board[(i * 3) + j];
            }
        }
    }

    public void makeMove(int move, boolean updateGui) {
        //convert van 1d naar 2d
        row = move / 3;
        col = move % 3;

        if (isGameRunning && validMove(row, col)) {
            // Steen plaatsen op positie rij, kolom op bord
            makeBoard2d();
            board2d[row][col] = piece;

            board = boardConvertto1d();

            if (turn.equals(ourUsername)) {
                Controller.con.makeMove(move);
            }

            if (updateGui) {
                try {
                    if (turn.equals(player1)) {
                        turn = player2;
                    } else {
                        turn = player1;
                    }
                    Platform.runLater(() -> {
                        Controller.tttController.updateView();
                    });

                } catch (NullPointerException n) {
                    System.out.println(Controller.tttController);
                    n.printStackTrace();
                }
            }
        }
    }


    //private methode, wordt alleen gebruikt bij getMoveList
    public boolean validMove(int row, int col) {
        // kijken of de rij en kolom leeg zijn
        if (board2d[row][col] != 0) {
            return false;
        }
        return true;
    }


    //functie om de beurt om te draaien
    public void changePiece() {
        if (piece == 1) {
            piece = 2;
        } else {
            piece = 1;
        }
        //System.out.println("Nu ben ik piece " + piece);
    }

    public int[] boardConvertto1d() {
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            board[i] = board2d[row][col];
        }
        return board;
    }

    public void resetBoard() {
        piece = 1;
        b = new Board(9);
        board = b.getBoard();

        board2d = new int[3][3];
        makeBoard2d();

    }

    public int getPiece() {
        return piece;
    }

    @Override
    public Board aiGetBoard() {
        return b;
    }
}