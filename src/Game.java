public abstract class Game {

    String gamename;
    int boardSize;
    public static String turn;
    public static String ourUsername;
    public static String player1;
    public static String player2;
    public static boolean isGameRunning;
    public static boolean isAI = true;

    public String getPlayerTurn() {return "";} //returned naam van de speler die aan de beurt is

    public String checkForWinner(){ //wordt gebruikt door AI
        //check of bord vol is board.boardIsFilled()
        return null;
    }

    public void updateBoard(int move){}

    public abstract Board aiGetBoard();

    //public Board aiGetBoard(){return null;}

    public static void setTurn(String turn) {
        Game.turn = turn;
    }
}
