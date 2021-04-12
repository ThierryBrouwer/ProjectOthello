import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class AI {


    Board board;
    Game game;

    public AI() {
        this.game = Controller.game;
    }


    public int makeMove(){
        if (game instanceof TicTacToe){
            this.board = game.aiGetBoard();
            return moveTicTacToe();
        }
        if (game instanceof Reversi){
            return moveReversi();
        }
        else return -1;
    }

    private int moveReversi() {

        Reversi rev = (Reversi) game;
        ArrayList<Pair> movelist2d = rev.getMoveList();
        int[] movelist = rev.convertMovesto1d(movelist2d);
        Random r = new Random();
        int i = r.nextInt(movelist.length);
        System.out.println("move reversi " + movelist[i]);
        String s = "";
        for(int j=0; j < movelist.length;j++) {
            s += movelist[j] + " ";
        }
        System.out.println(s);
        //rev.makeMove(movelist[i]);
        return movelist[i];
    }

    //return eerst mogelijke zet.
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

