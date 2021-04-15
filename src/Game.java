public abstract class Game {

    String gamename;
    int boardSize;
    public static String turn;
    public static String ourUsername;
    public static String opponent;
    public static String player1;
    public static String player2;
    public static int stateOfGame; // 0 = game is still going; 1 = won; 2 = lost; 3 = Draw
    public static boolean isGameRunning;
    public static boolean isAI = false;

    public String getPlayerTurn() {return "";} //returned naam van de speler die aan de beurt is

    public String checkForWinner(){ //wordt gebruikt door AI
        //check of bord vol is board.boardIsFilled()
        return null;
    }

    public void updateBoard(int move){}

    public abstract Board aiGetBoard();

    public abstract void changePiece();

    public abstract void makeMove(int pos, boolean b);

    //public Board aiGetBoard(){return null;}
}
