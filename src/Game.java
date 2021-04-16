public abstract class Game {

    public static String turn;
    public static String ourUsername;
    public static String opponent;
    public static String player1;
    public static String player2;
    public static int stateOfGame; // 0 = game is still going; 1 = won; 2 = lost; 3 = Draw
    public static boolean isGameRunning;
    public static boolean isAI = false;

    /**
     * Methode die een mogelijkheid bied aan AI voor uitbreiding.
     * Methode die kijkt of er een winner is
     * @return String geeft momenteel een null terug
     */
    public String checkForWinner() {
        //check of bord vol is board.boardIsFilled()
        return null;
    }

    /**
     * Methode die het board ophaalt voor AI
     * @return Board geeft een board terug aan de AI
     */
    public abstract Board aiGetBoard();

    /**
     * Methode die de beurt(piece) omdraait
     */
    public abstract void changePiece();

    /**
     * Deze methode zet een zet, hier wordt gecontroleerd of je move mag en of de game loopt en update de Gui
     * @param pos De move die jij/ai wilt spelen
     * @param b Boolean die de Gui update als je true doorgeeft
     */
    public abstract void makeMove(int pos, boolean b);

    /**
     * Deze methode reset het board en geeft standaard waarden mee
     */
    public abstract void resetBoard();

    /**
     * Methode die een boolean terug geeft, als het vak leeg is krijg je true terug, anders false
     * @param row De row (verticaal)
     * @param col De col (horizontaal)
     * @return boolean true of false
     */
    public abstract boolean validMove(int row, int col);

}
