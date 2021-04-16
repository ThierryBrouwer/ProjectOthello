/**
 * de klasse bord, hierin wordt het bord gemaakt, gereset en bijgehouden.
 *
 * @author Djordy
 * @version 15/4/2021
 */

public class Board {

    private int boardsize;
    private int board[];

    /**
     * de contructor voor klasse Board
     *
     * @param boardsize het aantal vakjes dat het bord moet krijgen
     */
    public Board(int boardsize) {
        this.board = new int[boardsize];
        this.boardsize = boardsize;
        createBoard();
    }

    /**
     * de methode om het bord aan te maken of te resetten en te printen met lege vakjes (0)
     */
    public void createBoard() {
        for (int i = 0; i < boardsize; i++) {
            board[i] = 0;
        }
    }

    /**
     * methode om het bord op te vragen
     *
     * @return return hoe het bord eruit ziet
     */
    public int[] getBoard() {
        return this.board;
    }

}
