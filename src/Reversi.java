import java.util.ArrayList;

public class Reversi extends Game {

    private final int[] board;
    private final int spelerBeurt;
    private final int[][] board2d;
    private int row;
    private int col;

    public Reversi() {
        spelerBeurt = 1;
        Board b = new Board(64);
        board = b.getBoard();
        board2d = new int[8][8];
    }

    public void makeBoard2d() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; i++) {
                board2d[i][j] = board[(j * 8) + i];
            }
        }
    }

   public void makeMove(int[][] board2d, int row, int col, int piece) {
        // Steen plaatsen op positie rij, kolom op bord
        board2d[row][col] = spelerBeurt;

        // Controleren welke 'kleur' (1 of 2) de tegenstander is
        int opponent = 1;
        if (piece == 1) {
            opponent = 2;
        }

        // controleer west
        if (checkFlip(board2d, row - 1, col, -1, 0, piece, opponent))
            flipPieces(board2d, row -1, col, -1, 0, piece, opponent);
        // controleer oost
        if (checkFlip(board2d, row + 1, col, 1, 0, piece, opponent))
            flipPieces(board2d, row + 1, col, 1, 0, piece, opponent);
        // controleer zuid
        if (checkFlip(board2d, row, col - 1, 0, - 1, piece, opponent))
            flipPieces(board2d, row, col - 1, 0, - 1, piece, opponent);
        // controleer noord
        if (checkFlip(board2d, row, col + 1, 0, 1, piece, opponent))
            flipPieces(board2d, row, col + 1, 0, 1, piece, opponent);
        // controleer zuid west
        if (checkFlip(board2d, row - 1, col - 1, - 1, - 1, piece, opponent))
            flipPieces(board2d, row - 1, col - 1, - 1, - 1, piece, opponent);
        // controleer oost west
        if (checkFlip(board2d, row + 1, col - 1,  1, - 1, piece, opponent))
            flipPieces(board2d, row + 1, col - 1, 1, - 1, piece, opponent);
        // controleer noord west
        if (checkFlip(board2d, row - 1, col + 1, 1, 1, piece, opponent))
            flipPieces(board2d, row - 1, col + 1, - 1, 1, piece, opponent);
        // controleer noord oost
        if (checkFlip(board2d, row + 1, col + 1, 1, 1, piece, opponent))
            flipPieces(board2d, row + 1, col + 1, 1, 1, piece, opponent);
    }

    public boolean checkFlip(int[][] board2d, int row, int col, int deltaRow, int deltaCol, int myPiece, int opponentPiece) {
        if (board2d[row][col] == opponentPiece) {
            while ((row >= 0) && (row < 8) && (col >= 0) && (col <8)) {
                row += deltaRow;
                col += deltaCol;
                if (board2d[row][col] == 0) // niet opeenvolgend
                    return false;
                if (board2d[row][col] == myPiece)
                    return true; // We kunnen stenen flippen
                else {
                    // steen van tegenstander, richting kan gescanned blijven worden
                }
            }
        }
        return false; // Geen steen van tegenstander of einde van het bord
    }

   public void flipPieces (int[][] board2d, int row, int col, int deltaRow, int deltaCol, int myPiece, int opponentPiece) {
        while (board2d[row][col] == opponentPiece) {
            board2d[row][col] = myPiece;
            row += deltaRow;
            col += deltaCol;
        }
   }

   public boolean validMove(int[][] board2d, int row, int col, int piece) {
       // kijken of de rij en kolom leeg zijn
       if (board2d[row][col] != 0)
           return false;

       // Kijken welke steen de tegenstander is
       int opponent = 1;
       if (piece == 1)
           opponent = 2;

       // Als we een kant op kunnen, dan is de move valid
       // controleer west
       if (checkFlip(board2d, row - 1, col, -1, 0, piece, opponent))
           return true;
       // controleer oost
       if (checkFlip(board2d, row + 1, col, 1, 0, piece, opponent))
           return true;
       // controleer zuid
       if (checkFlip(board2d, row, col - 1, 0, -1, piece, opponent))
           return true;
       // controleer noord
       if (checkFlip(board2d, row, col + 1, 0, 1, piece, opponent))
           return true;
       // controleer zuid west
       if (checkFlip(board2d, row - 1, col - 1, -1, -1, piece, opponent))
           return true;
        // controleer oost west
        if (checkFlip(board2d, row + 1, col - 1,  1, - 1, piece, opponent))
           return true;
        // controleer noord west
        if (checkFlip(board2d, row - 1, col + 1, 1, 1, piece, opponent))
           return true;
        // controleer noord oost
       return checkFlip(board2d, row + 1, col + 1, 1, 1, piece, opponent);
   }

    public void getMoveList(int[][] board2d, int[] moveRow, int[] moveCol, int numMoves, int piece ) {

        numMoves = 0; // We beginnen vanaf 0 gevonden moves

        // Het bord controleren of we daarheen mogen bewegen en de coordinaten
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++)
            {
                if (validMove(board2d, row, col, piece))  { // Coordinaten opslaan
                    moveRow[numMoves] = row;
                    moveCol[numMoves] = col;
                    numMoves++;                             // Aantal moves dat gevonden is toevoegen
                }
            }
    }


    public int blackPoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 1) {
                counter++;
            }
        }
        return counter;
    }

    public int whitePoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 2) {
                counter++;
            }
        }
        return counter;
    }

    // Kijkt of zwart of wit de meeste punten heeft return x als zwart wint, return o als wit wint en return 0 bij gelijkspel.
    public int gameWinner() {
        if (blackPoints() > whitePoints()) {
            return 1;
        }
        if (blackPoints() < whitePoints()) {
            return 2;
        }
        else {
            return 0;
        }
    }


    @Override
    public Board aiGetBoard() {
        return null;
    }
}




