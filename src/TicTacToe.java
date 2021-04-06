public class TicTacToe extends Game{

    private int[] board;
    private int spelerBeurt;
    public Board b;

    public TicTacToe() {
        //board = new char[3][3];
        spelerBeurt = 1;
        b = new Board(9);
        board = b.getBoard();
    }

    // Getter om de speler die aan de beurt is te krijgen
    public int getSpelerDieSpeelt()
    {
        return spelerBeurt;
    }


    //zet de move op het bord en past de spelerbeurt aan zodat de volgende move van de andere speler is.
    public void updateBoard(int move){
        board[move] = spelerBeurt;
        if (spelerBeurt == 1){
            spelerBeurt = 2;
        }
        else if (spelerBeurt == 2){
            spelerBeurt = 1;
        }
    }

    public Board aiGetBoard(){
        return b;
    }


    // Win functie om de andere 3 win functies aan te roepen
    // Geeft true als er een winnaar is, false als er geen winnaar is
    public boolean controleerWinnaar() {
        return (rijenWinnaar() || kolomWinnaar() || diagonaalWinnaar());
    }

    // Rijen controleren op een winnaar (Verticaal)
    private boolean rijenWinnaar() {
        for (int rij = 0; rij < 3; rij++) {
            if (controleerRij(board[3 *rij], board[3 * rij + 1], board[3 * rij + 2]) == true)
            return true;
        }
        return false;
    }

    //Kolommen cotroleren op een winnaar (Horizontaal)
    private boolean kolomWinnaar() {
        for (int kol = 0; kol < 3; kol++) {
            if (controleerRij(board[kol * 1], board[kol * 2], board[kol * 3]) == true) {
                return true;
            }
        }
        return false;
}
// de twee diagonale mogelijkheden controleren op een winnaar
private boolean diagonaalWinnaar() {
    return ((controleerRij(board[0], board[4], board[8]) == true) || (controleerRij(board[2], board[4], board[6]) == true));
}

// Controleren of de 3 waardes hetzelfde zijn om te kijken of er een winnaar is
private boolean controleerRij(int hok1, int hok2, int hok3) {
    return ((hok1 != 0) && (hok1 == hok2) && (hok2 == hok3) && (hok1 == hok3));
}

    public int[] getBoard() {
        return board;
    }

    public boolean isMoveLegal(int move){
        return board[move] == 0;
    }
}