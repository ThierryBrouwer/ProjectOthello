import java.util.ArrayList;

public class Reversi extends Game {

    private int[] board;
    private int spelerBeurt;

    public Reversi() {
        spelerBeurt = 1;
        Board b = new Board(64);
        board = b.getBoard();
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




