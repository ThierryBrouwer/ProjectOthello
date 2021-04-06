import java.util.Random;

public class AI {


    Board board;
    TicTacToe game;     //nu gehardcode dat dit een TicTacToe is, moet nog opgelost worden

    public AI(TicTacToe game){
        this.game = game;
    }

    public AI(Reversi game){}

    public int makeMove(){
        if (game instanceof TicTacToe){
            this.board = game.aiGetBoard();
            return moveTicTacToe();
        }
        else return -1;
    }

    //return eerst mogelijke zet of -1 als er geen zet mogelijk is.
    private int moveTicTacToe(){
        Random r = new Random();
        int i = r.nextInt(8);
        while(!board.isMoveLegal(i)){i = r.nextInt(8);}
        game.updateBoard(i);
        return i;


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

