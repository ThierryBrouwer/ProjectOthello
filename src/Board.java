public class Board {

    private int boardsize;
    private int board[];

    public Board(int boardsize){
        this.board = new int[boardsize];
        this.boardsize = boardsize;
        createBoard();
    }

    public Board() {

    }

    // Bord voor betreffende spel aanmaken of resetten
    public void createBoard(){
        for(int i=0; i < boardsize;i++){
            board[i] = '0';
        }
    }

    public boolean isMoveLegal(int move){
        return board[move] == '0';
    }



    public int[] getBoard(){return this.board;}


    //Kijken of het bord vol is, wordt gebruikt door AI om te kijken of er nog een zet gedaan kan worden of dat het bord vol is
    //Lege velden worden aangegeven met een '-'
    public boolean BoardFilled() {
        boolean isFilled = true;

        for(int i=0; i < boardsize; i++){
            if (board[i] == '0'){
                    isFilled = false;
                }

        }
        return isFilled;
    }
}
