public class Board {

    private int boardsize;
    private char board[][];

    public Board(int boardsize){
        board = new char[boardsize][boardsize];
        this.boardsize = boardsize;
    }

    // Bord voor betreffende spel aanmaken of resetten
    public void createBoard(){
        // Door de rijen heen loopen (Horizontaal)
        for (int rij = 0; rij < boardsize; rij++) {

            // Door de kolom heen loopen (Verticaal)
            for (int kol = 0; kol < boardsize; kol++) {
                board[rij][kol] = '-';
            }
        }
    }

    public boolean isMoveLegal(int move){return false;}



    public void getBoard(){return ;}


    //Kijken of het bord vol is, wordt gebruikt door AI om te kijken of er nog een zet gedaan kan worden of dat het bord vol is
    //Lege velden worden aangegeven met een '-'
    public boolean BoardFilled() {
        boolean isFilled = true;

        for (int rij = 0; rij < boardsize; rij++) {
            for (int kol = 0; kol < boardsize; kol++) {
                if (board[rij][kol] == '-') {
                    isFilled = false;
                }
            }
        }
        return isFilled;
    }
}
