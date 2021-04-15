public class Board {

    private int boardsize;
    private int board[];

    public Board(int boardsize) {
        this.board = new int[boardsize];
        this.boardsize = boardsize;
        createBoard();
    }

    // Bord voor betreffende spel aanmaken of resetten
    public void createBoard() {
        for (int i = 0; i < boardsize; i++) {
            board[i] = 0;
        }
    }

    public int[] getBoard() {
        return this.board;
    }

}
