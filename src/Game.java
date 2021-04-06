public abstract class Game {

    String gamename;
    int boardSize;

    public String getPlayerTurn() {return "";} //returned naam van de speler die aan de beurt is

    public String checkForWinner(){ //wordt gebruikt door AI
        //check of bord vol is board.boardIsFilled()
        return null;
    }

    public void updateBoard(int move){}

    public abstract Board aiGetBoard();

    //public Board aiGetBoard(){return null;}



}
