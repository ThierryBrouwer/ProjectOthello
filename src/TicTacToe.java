import javafx.application.Platform;

public class TicTacToe extends Game {

    private int[] board;
    private int[][] board2d;
    private int row;
    private int col;
    private int piece;
    private Board b;

    /**
     * Constructor van de TicTacToe klasse
     */
    public TicTacToe() {
        resetBoard();
    }

    /**
     * Methode die een 2d board maakt en het in een int array stopt
     */
    public void makeBoard2d() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board2d[i][j] = this.board[(i * 3) + j];
            }
        }
    }

    /**
     * Deze methode zet een zet, hier wordt gecontroleerd of je move mag en of de game loopt en update de Gui
     * @param move De move die jij/ai wilt spelen
     * @param updateGui Boolean die de Gui update als je true doorgeeft
     */
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


    /**
     * Methode die een boolean terug geeft, als het vak leeg is krijg je true terug, anders false
     * @param row De row (verticaal)
     * @param col De col (horizontaal)
     * @return boolean true of false
     */
    public boolean validMove(int row, int col) {
        // kijken of de rij en kolom leeg zijn
        if (board2d[row][col] != 0) {
            return false;
        }
        return true;
    }


    /**
     * Methode die de beurt(piece) omdraait
     */
    public void changePiece() {
        if (piece == 1) {
            piece = 2;
        } else {
            piece = 1;
        }
        //System.out.println("Nu ben ik piece " + piece);
    }

    /**
     * Methode die het 2d board omzet naar een 1d board
     * @return int return het 1d board
     */
    public int[] boardConvertto1d() {
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            board[i] = board2d[row][col];
        }
        return board;
    }

    /**
     * Deze methode reset het board en geeft standaard waarden mee
     */
    public void resetBoard() {
        piece = 1;
        b = new Board(9);
        board = b.getBoard();

        board2d = new int[3][3];
        makeBoard2d();

    }

    /**
     * Deze methode vraagt de piece op
     * @return int return de piece
     */
    public int getPiece() {
        return piece;
    }

    /**
     * Methode die het board opvraagt
     * @return Board return het board
     */
    public Board aiGetBoard() {
        return b;
    }
}