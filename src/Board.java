public class Board {

    private int boardsize;
    private char board[];

    public Board(int boardsize){
        this.board = new char[boardsize];
        this.boardsize = boardsize;
    }

    // Bord voor betreffende spel aanmaken of resetten
    public void createBoard(){
        for(int i=0; i < 8;i++){
            board[i] = '-';
        }
    }

    public boolean isMoveLegal(int move){
        return board[move] != '-';
    }



    public char[] getBoard(){return this.board;}


    //Kijken of het bord vol is, wordt gebruikt door AI om te kijken of er nog een zet gedaan kan worden of dat het bord vol is
    //Lege velden worden aangegeven met een '-'
    public boolean BoardFilled() {
        boolean isFilled = true;

        for(int i=0; i < 8; i++){
            if (board[i] == '-'){
                    isFilled = false;
                }

        }
        return isFilled;
    }
}
