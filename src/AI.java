import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * AI klasse. Hierin worden zetten gevonden en gereturned, afhankelijk van het spel en de moeilijkheidsgraad die geselecteerd is.
 *
 * @author Joost P
 * @version 15/4/2021
 */
public class AI {


    Board board;
    Game game;
    private final int MAX_DEPTH = 7;
    static int difficulty;

    /**
     * Constructor voor AI klasse. Gebruikt de static variabele game uit de Controller klasse.
     */
    public AI(Game game) {
        this.game = game;

    }


    /**
     * makeMove methode checkt of er tic-tac-toe of reversi gespeeld wordt.
     * Daarna roept de methode de juiste private move methode aan.
     *
     * @return een int, die een plek op het bord voor stelt.
     */
    public int makeMove() {
        if (game instanceof TicTacToe) {
            this.board = game.aiGetBoard();
            return moveTicTacToe();
        }
        if (game instanceof Reversi) {
            return moveReversi();
        } else return -1;
    }

    /**
     * moveReversi checkt welke difficulty geselecteerd is, en roept aan de hand daarvan het juiste algoritme aan.
     *
     * @return een int, die een plek op het bord voor stelt.
     */
    private int moveReversi() {
        if (difficulty == 1) {
            int move = useMiniMax();
            return move;
        } else {
            //int move = useRandomMove();
            int move = useMiniMax();
            return move;
        }

    }

    /**
     * useRandomMove kiest een random move uit de lijst van valid moves, die uit de Reversi klasse wordt opgehaald.
     *
     * @return een int, die een plek op het bord voor stelt.
     */
    private int useRandomMove() {
        Reversi rev = (Reversi) game;
        ArrayList<Pair> movelist2d = rev.getMoveList();
        int[] movelist = rev.convertMovesto1d(movelist2d);
        Random r = new Random();
        int i = r.nextInt(movelist.length);
        System.out.println("move reversi " + movelist[i]);
        String s = "";
        for (int j = 0; j < movelist.length; j++) {
            s += movelist[j] + " ";
        }
        System.out.println(s);
        //rev.makeMove(movelist[i]);
        return movelist[i];
    }

    /**
     * useMiniMax roept de miniMax methode aan.
     *
     * @return int, de beste zet die het miniMax algoritme heeft gevonden.
     */
    private int useMiniMax() {
        Reversi rev = (Reversi) game;
        ArrayList<Pair> movelist2d = rev.getMoveList();
        int[] movelist = rev.convertMovesto1d(movelist2d);

        (DataPusher.getInstance()).setValidMovesCount(movelist.length); //onderzoek




        ArrayList<Reversi> children = new ArrayList<>();

        int highestvalue = Integer.MIN_VALUE;
        int lowestvalue = Integer.MAX_VALUE;
        int bestmove = movelist[0];
        int[] oldboard = rev.getBoard();
        (DataPusher.getInstance()).setLastBoard(oldboard); //onderzoek
        for (int i : movelist) {

            Reversi child = new Reversi();
            int[] newboard = rev.copyboard(oldboard);

            child.setBoard(newboard);

            child.makeMove(i, false);
            child.changePiece();
            int value = miniMax(child, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (rev.getPiece() == 1) {
                if (value > highestvalue) {
                    highestvalue = value;
                    bestmove = i;
                }
                //System.out.println("value: " + value + " move: " + i + " bestmove: " + bestmove);
                (DataPusher.getInstance()).setLastMove(i); //onderzoek
            } else {
                if (value < lowestvalue) {
                    lowestvalue = value;
                    bestmove = i;
                }
                //System.out.println("value: " + value + " move: " + i + " bestmove: " + bestmove);
                (DataPusher.getInstance()).setLastMove(i); //onderzoek
            }


        }
        return bestmove;
    }


    /**
     * moveTicTacToe returned de eerste geldige zet die gezet kan worden
     *
     * @return een int, die een plek op het bord voor stelt.
     */
    private int moveTicTacToe() {
        Random r = new Random();
        int i = r.nextInt(9);
        int row = i / 3;
        int col = i % 3;
        while (!game.validMove(row, col)) {

            i = r.nextInt(9);
            row = i / 3;
            col = i % 3;
        }

        int index = row * 3 + col;
        //game.updateBoard(i);
        return index;


    }

    /**
     * evaluatie methode voor het minimax algoritme. De methode telt de witte en zwarte punten en hoeken tellen zwaarder mee.
     * Als de waarde in het voordeel van zwart is, is de evaluatie positief.
     * Als de waarde in het voordeel van wit is, is de evaluatie negatief.
     *
     * @param game de game positie om te evalueren.
     * @return een int, die een plek op het bord voor stelt.
     */
    private int evaluate(Reversi game) {
        int eval = game.blackPoints() - game.whitePoints();

        int[] board = game.getBoard();
        int whitecorners = 0;
        int blackcorners = 0;

        if (board[0] == 1) {
            blackcorners++;
        }
        if (board[7] == 1) {
            blackcorners++;
        }
        if (board[56] == 1) {
            blackcorners++;
        }
        if (board[63] == 1) {
            blackcorners++;
        }
        if (board[0] == 2) {
            whitecorners++;
        }
        if (board[7] == 2) {
            whitecorners++;
        }
        if (board[56] == 2) {
            whitecorners++;
        }
        if (board[63] == 2) {
            whitecorners++;
        }

        eval += blackcorners * 24;
        eval -= whitecorners * 24;
        return eval;
    }


    /**
     * useMiniMax is de methode die het minimax algoritme met alpha-beta pruning gebruikt.
     * De methode roept zichzelf recursief aan.
     * Raadpleeg de documentatie voor meer info over deze functie.
     *
     * @param position de gamepositie om mee verder te rekenen.
     * @param depth    de huidige recursie diepte.
     * @param isMax    boolean die aangeeft of er gemaximized of geminimized moet worden
     * @param alpha    de alpha waarde. Wordt initieel op Integer.MIN_VALUE gezet.
     * @param beta     de beta waarde. Wordt initieel op Integer.MAX_VALUE gezet.
     * @return
     */
    public int miniMax(Reversi position, int depth, boolean isMax, int alpha, int beta) {

        if (depth == MAX_DEPTH || position.convertMovesto1d(position.getMoveList()).length == 0) {
            if (evaluate(position) > 2000000) {
                System.out.println("alpha: " + alpha);
                System.out.println("beta: " + beta);
            }
            return evaluate(position);
        }

        if (isMax) {
            int maxEval = Integer.MIN_VALUE;
            for (Reversi child : position.getChildren(false)) {
                int eval = miniMax(child, depth + 1, false, alpha, beta);
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Reversi child : position.getChildren(true)) {
                int eval = miniMax(child, depth + 1, true, alpha, beta);
                minEval = min(minEval, eval);
                beta = min(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }
}



