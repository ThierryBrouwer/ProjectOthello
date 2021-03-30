public class AI {


    Board board;
    Game game;

    public AI(Board board, Game game){
        this.board = board;
        this.game = game;
    }

    public int makeMove(){
        if (game instanceof TicTacToe){
            return moveTicTacToe();
        }
        else return -1;
    }

    //return eerst mogelijke zet of -1 als er geen zet mogelijk is.
    private int moveTicTacToe(){
        for (int i=0;i<7;i++){
            if(board.isMoveLegal(i)){
                return i;
            }

        }
        return -1;
    }

    /*
    *** skelet voor miniMax algoritme, moet nog recursie in. ***
    *

    public int[] getLegalMoves(board){
        return null;
    }

    public int getBestMove(){
        int[] legalmoves = getLegalMoves();

        int bestvalue = -2;
        int bestmove = -1;

        for (int move : legalmoves){
            int movevalue = evaluatemove(move);
            if (movevalue > bestvalue){
                movevalue = bestvalue;
                move = bestmove;
            }
        }
        return bestmove;
    }

    public int evaluateMove(int move){
        checkWinner();
        if (movewins){return 1;}
        if (!movewins){return -1;}
        else(movedraw){return 0;}
    }

    */

}

