import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class AI {


    Board board;
    Game game;
    private final int MAX_DEPTH = 8;
    static int difficulty = 1;


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
        if (difficulty == 1){
            int move = useMiniMax();
            return move;
        }
        else{
            int move = useRandomMove();
            return move;
        }

    }

    private int useRandomMove(){
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

    private int useMiniMax(){
            Reversi rev = (Reversi) game;
            ArrayList<Pair> movelist2d = rev.getMoveList();
            int[] movelist = rev.convertMovesto1d(movelist2d);


            ArrayList<Reversi> children = new ArrayList<>();

            int highestvalue = Integer.MIN_VALUE;
            int lowestvalue = Integer.MAX_VALUE;
            int bestmove = movelist[0];
            int[] oldboard = rev.getBoard();
            for (int i : movelist){

                Reversi child = new Reversi();
                int[] newboard = rev.copyboard(oldboard);

                child.setBoard(newboard);

                child.makeMove(i,false);
                child.changePiece();
                int value = miniMax(child, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

                if(rev.getPiece() == 1){
                    if (value > highestvalue){
                        highestvalue = value;
                        bestmove = i;
                    }
                    System.out.println("value: " + value + " move: " + i +  " bestmove: " + bestmove);
                }
                else{
                    if (value < lowestvalue){
                        lowestvalue = value;
                        bestmove = i;
                    }
                    System.out.println("value: " + value + " move: " + i +  " bestmove: " + bestmove);
                }


            }
            return bestmove;
        }


    //return random mogelijke zet.
    private int moveTicTacToe() {
        Random r = new Random();
        int i = r.nextInt(8);
        while (!board.isMoveLegal(i)) {
            i = r.nextInt(8);
        }
        game.updateBoard(i);
        return i;


    }

    private int evaluate(Reversi game) {
        return game.whitePoints() - game.blackPoints();
    }


    public int miniMax(Reversi position, int depth, boolean isMax, int alpha, int beta) {

        if (depth == MAX_DEPTH || position.convertMovesto1d(position.getMoveList()).length == 0){
            return evaluate(position);
        }

        if (isMax){
            int maxEval = Integer.MIN_VALUE;
            for (Reversi child : position.getChildren(false)){
                int eval = miniMax(child, depth +1, false, alpha, beta);
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if (beta <= alpha){break;}
            }
            return maxEval;
        }
        else{
            int minEval = Integer.MAX_VALUE;
            for (Reversi child : position.getChildren(true)){
                int eval = miniMax(child, depth +1, true, alpha, beta);
                minEval = min(minEval, eval);
                beta = min(alpha, eval);
                if (beta <= alpha){break;}
            }
            return minEval;
        }
    }
}



