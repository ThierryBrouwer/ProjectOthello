import java.util.ArrayList;

public class Reversi extends Game {

    private char[] board;
    private char spelerBeurt;

    public Reversi() {
        spelerBeurt = 'x';
        Board b = new Board(63);
        board = b.getBoard();
    }

    public int blackPoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++)
            if (board[i] == 'x')
                counter ++;
            return counter;
    }

    public int whitePoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++)
            if (board[i] == 'o')
                counter ++;
        return counter;
    }

    // Kijkt of zwart of wit de meeste punten heeft return x als zwart wint, return o als wit wint en return 0 bij gelijkspel.
    public char gameWinner() {
        if (blackPoints() > whitePoints())
            return 'x';
        if (blackPoints() < whitePoints())
            return 'o';
        else
            return '0';
        }

    }




