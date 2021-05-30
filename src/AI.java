import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class AI {

    //private Board board;
    private Reversi game;

    private int[][] possibleMoves;
    private int nextMove;
    //private GameMechanics gameMechanics;


    /**
     * Constructor voor de tweede AI voor het onderzoek, ondersteund voor nu alleen Reversi als spel.
     * @param game (Reversi)
     */
    public AI(Game game) {
        this.game = (Reversi) game;
    }

    /**
     * @return Loops through current board from top left to bottom right and returns first valid move
     */
    public int getNextMove() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(possibleMoves[i][j] == 3) {

                    return (i * 8 - j);
                }
            }
        }

        return nextMove;
    }

    /**
     * @param board int[][] of current board status
     * @return Returns a random move
     */
    public int useRandomMove(int[][] board) {
        int[] boardArray = new int[board.length * board[0].length];
        ArrayList<Integer> possibleMovesList = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boardArray[i + (j * board.length)] = board[i][j];
            }
        }
        for (int i = 0; i++ < boardArray.length; i++) {
            if (boardArray[i] == 3) {
                possibleMovesList.add(i);
            }
        }
        Collections.shuffle(possibleMovesList);
        return possibleMovesList.get(0);
    }

    /**
     * @return Returns move that takes the most pieces
     */
    public int makeMove() {
        //Board currentBoard = gameMechanics.getBoard();
        int[][] board = getPossibleMoves();
        //Rules currentRules = gameMechanics.getCurrentRules();
        int oldScore = 0;
        int newScore = 0;
        int bestMove = 0;
        for (int i = 0; i < board.length; i++) {        //loop door 2D array voor geldige zetten
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 3) {                 //Bij geldige zet, flip stones en bereken score
                    oldScore = getPlayerScore(game.getBoard2d(), game.getPiece());
                    if (oldScore > newScore) {
                        newScore = oldScore;
                        bestMove = i * 8 + j;
                    }
                }
            }
        }
        (DataPusher.getInstance()).setLastMove(bestMove); //onderzoek
        return bestMove;
    }

    /**
     * @return Returns valid moves
     */
    private int[][] getPossibleMoves() {
        setPossibleMoves();
        return possibleMoves;
    }


    /**
     * @param board int[][] of current status of board
     * @param player
     * @return
     */
    private int getPlayerScore(int[][] board, int player) {
        int score = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == player) {
                    score++;
                }
            }
        }
        return score;
    }


    /**
     * Sets valid moves
     */
    private void setPossibleMoves() {
        ArrayList<Pair> legalMoves = game.getMoveList();
        (DataPusher.getInstance()).setValidMovesCount(legalMoves.size()); //onderzoek
        int[] tempBoard = game.copyboard(game.getBoard());
        (DataPusher.getInstance()).setLastBoard(tempBoard); //onderzoek
        int[][] tempBoard2d = game.getConvertedBoard2d(tempBoard);

        for(Pair pair : legalMoves){
            int row = (int)pair.getKey();
            int col = (int)pair.getValue();

            tempBoard2d[row][col] = 3;
        }

        possibleMoves = tempBoard2d;
    }

}
