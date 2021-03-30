public class TicTacToe extends Game{

    private char[][] board;
    private int boardsize;
    private char spelerBeurt;

    public TicTacToe() {
        board = new char[3][3];
        spelerBeurt = 'x';
        Board board = new Board(3);
    }

    // Getter om de speler die aan de beurt is te krijgen
    public char getSpelerDieSpeelt()
    {
        return spelerBeurt;
    }



    // Win functie om de andere 3 win functies aan te roepen
    // Geeft true als er een winnaar is, false als er geen winnaar is
    public boolean controleerWinnaar() {
        return (rijenWinnaar() || kolomWinnaar() || diagonaalWinnaar());
    }

    // Rijen controleren op een winnaar (Verticaal)
    private boolean rijenWinnaar() {
        for (int rij = 0; rij < 3; rij++) {
            if (controleerRij(board[rij][0], board[rij][1], board[rij][2]) == true)
            return true;
        }
        return false;
    }

    //Kolommen cotroleren op een winnaar (Horizontaal)
    private boolean kolomWinnaar() {
        for (int kol = 0; kol < 3; kol++) {
            if (controleerRij(board[0][kol], board[1][kol], board[2][kol]) == true) {
                return true;
            }
        }
        return false;
}
// de twee diagonale mogelijkheden controleren op een winnaar
private boolean diagonaalWinnaar() {
    return ((controleerRij(board[0][0], board[1][1], board[2][2]) == true) || (controleerRij(board[0][2], board[1][1], board[2][0]) == true));
}

// Controleren of de 3 waardes hetzelfde zijn om te kijken of er een winnaar is
private boolean controleerRij(char hok1, char hok2, char hok3) {
    return ((hok1 != '-') && (hok1 == hok2) && (hok2 == hok3) && (hok1 == hok3));
}



}