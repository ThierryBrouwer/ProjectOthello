import javafx.application.Platform;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Reversi extends Game {

    private int[] board;
    private int spelerBeurt;
    private int[][] board2d;
    private int row;
    private int col;
    private int piece;
    private Board b;
    private int lastmove;

    public Reversi(){
        resetBoard();
    }

    public void makeBoard2d() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board2d[i][j] = this.board[(i * 8) + j];
            }
        }
    }

   public void makeMove(int move, boolean updateGui) {
        //convert van 1d naar 2d
        row = move / 8;
        col = move % 8;

        if (isGameRunning && validMove(row, col)) {
                // Steen plaatsen op positie rij, kolom op bord
                makeBoard2d();
                board2d[row][col] = piece;

                // Controleren welke 'kleur' (1 of 2) de tegenstander is
                int opponent = 1;
                if (piece == 1) {
                    opponent = 2;
                }

                // controleer west
                if (checkFlip(board2d, row - 1, col, -1, 0, piece, opponent))
                    flipPieces(row - 1, col, -1, 0, piece, opponent);
                // controleer oost
                if (checkFlip(board2d, row + 1, col, 1, 0, piece, opponent))
                    flipPieces(row + 1, col, 1, 0, piece, opponent);
                // controleer zuid
                if (checkFlip(board2d, row, col - 1, 0, -1, piece, opponent))
                    flipPieces(row, col - 1, 0, -1, piece, opponent);
                // controleer noord
                if (checkFlip(board2d, row, col + 1, 0, 1, piece, opponent))
                    flipPieces(row, col + 1, 0, 1, piece, opponent);
                // controleer zuid west
                if (checkFlip(board2d, row - 1, col - 1, -1, -1, piece, opponent))
                    flipPieces(row - 1, col - 1, -1, -1, piece, opponent);
                // controleer oost west
                if (checkFlip(board2d, row + 1, col - 1, 1, -1, piece, opponent))
                    flipPieces(row + 1, col - 1, 1, -1, piece, opponent);
                // controleer noord west
                if (checkFlip(board2d, row - 1, col + 1, -1, 1, piece, opponent))
                    flipPieces(row - 1, col + 1, -1, 1, piece, opponent);
                // controleer noord oost
                if (checkFlip(board2d, row + 1, col + 1, 1, 1, piece, opponent))
                    flipPieces(row + 1, col + 1, 1, 1, piece, opponent);

        board = boardConvertto1d();

       //print2dBoard(board2d);

                //swap beurt
                //changePiece();
                //print2dBoard(board2d);

                if (!isAI) {
                    Controller.con.makeMove(move);
                }

                //Platform.runLater(Controller.reversiController::updateView);
            if (updateGui) {
                try {
                    Game.turn = Game.opponent;
                    //Controller.game.turn = Controller.game.opponent;
                    Platform.runLater(() -> {
                        Controller.reversiController.updateView();
                    });

                } catch (NullPointerException n) {
                    System.out.println(Controller.reversiController);
                    n.printStackTrace();
                }
            }


            System.out.println("Het is niet jouw beurt");
        }
        else {
            System.out.println("Oeps, je kan geen steentje zetten hier!");
        }
    }

    public boolean checkFlip(int[][] board2d, int row, int col, int deltaRow, int deltaCol, int myPiece, int opponentPiece) {
        //check voor piece aan buitenste ring
        if(row < 0 ||row > 7|| col < 0|| col > 7){return false;}


        if (board2d[row][col] == opponentPiece) {
            while ((row >= 0) && (row < 8) && (col >= 0) && (col <8)) {
                row += deltaRow;
                col += deltaCol;
                if(row < 0 ||row > 7|| col < 0|| col > 7){return false;} //als de tile buiten het veld valt, kunnen we niet flippen


                if (board2d[row][col] == 0) // niet opeenvolgend
                    return false;
                if (board2d[row][col] == myPiece)
                    return true; // We kunnen stenen flippen
                else {
                    // steen van tegenstander, richting kan gescanned blijven worden
                }
            }
        }
        return false; // Geen steen van tegenstander of einde van het bord
    }

   public void flipPieces (int row, int col, int deltaRow, int deltaCol, int myPiece, int opponentPiece) {
        while (board2d[row][col] == opponentPiece) {
            board2d[row][col] = myPiece;
            row += deltaRow;
            col += deltaCol;
        }
   }

   //private methode, wordt alleen gebruikt bij getMoveList
   public boolean validMove(int row, int col) {
       // kijken of de rij en kolom leeg zijn
       if (board2d[row][col] != 0)
           return false;

       // Kijken welke steen de tegenstander is
       int opponent = 1;
       if (piece == 1)
           opponent = 2;

       // Als we een kant op kunnen, dan is de move valid
       // controleer west
       if (checkFlip(board2d, row - 1, col, -1, 0, piece, opponent))
           return true;
       // controleer oost
       if (checkFlip(board2d, row + 1, col, 1, 0, piece, opponent))
           return true;
       // controleer zuid
       if (checkFlip(board2d, row, col - 1, 0, -1, piece, opponent))
           return true;
       // controleer noord
       if (checkFlip(board2d, row, col + 1, 0, 1, piece, opponent))
           return true;
       // controleer zuid west
       if (checkFlip(board2d, row - 1, col - 1, -1, -1, piece, opponent))
           return true;
        // controleer oost west
        if (checkFlip(board2d, row + 1, col - 1,  1, - 1, piece, opponent))
           return true;
        // controleer noord west
        if (checkFlip(board2d, row - 1, col + 1, -1, 1, piece, opponent))
           return true;
        // controleer noord oost
       return checkFlip(board2d, row + 1, col + 1, 1, 1, piece, opponent);
   }

    public ArrayList<Pair> getMoveList() {

        ArrayList<Pair> legalMoves = new ArrayList<>();
        // Het bord controleren of we daarheen mogen bewegen en de coordinaten
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (validMove(row, col)) { // Coordinaten opslaan
                    Pair p = new Pair(row,col);
                    legalMoves.add(p);
                }
            }
        }
        return legalMoves;
    }


    public int blackPoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 2) {
                counter++;
            }
        }
        return counter;
    }

    public int whitePoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 1) {
                counter++;
            }
        }
        return counter;
    }

    // Kijkt of zwart of wit de meeste punten heeft return x als zwart wint, return o als wit wint en return 0 bij gelijkspel.
    public int gameWinner() {
        if (blackPoints() > whitePoints()) {
            return 1;
        }
        if (blackPoints() < whitePoints()) {
            return 2;
        }
        else {
            return 0;
        }
    }

    public int[] convertMovesto1d(ArrayList<Pair> legalmoves2d){
        int[] legalmoves1d = new int[legalmoves2d.size()];
        for (int i=0;i < legalmoves2d.size();i++){
            Pair coord = legalmoves2d.get(i);
            Object key = coord.getKey();
            Object value = coord.getValue();
            int row = (int) key;
            int col = (int) value;
            legalmoves1d[i] = (8*row+col);
        }
        return legalmoves1d;
    }

    public void print2dBoard(int[][] board){

        for(int i=0;i < board.length;i++){
            String s = "";
            for(int j=0;j < board.length;j++){
                s = s + board[i][j];
            }
            System.out.println(s);
        }
        System.out.println("");
    }

    //functie om de beurt om te draaien
    public void changePiece(){
        if (piece == 1){
            piece = 2;
        }
        else{piece = 1;}
        //System.out.println("Nu ben ik piece " + piece);

    }

    public int[] boardConvertto1d() {
        for (int i=0;i<64;i++){
            int row = i / 8;
            int col = i % 8;
            board[i] = board2d[row][col];
        }
        return board;
    }

    public void resetBoard(){
        spelerBeurt = 1;
        Board b = new Board(64);
        board = b.getBoard();

        //begin positie invullen
        board[27] = 2;
        board[28] = 1;
        board[35] = 1;
        board[36] = 2;

        piece = 1;

        board2d = new int[8][8];
        makeBoard2d();
    }

    public void setBoard(int[] board){
        this.board = board;
        makeBoard2d();
    }

    public int[] getBoard() {
        return this.board;
    }

    public ArrayList<Reversi> getChildren(boolean isOtherPlayer){
        ArrayList<Reversi> children = new ArrayList<>();
        //System.out.println(isOtherPlayer);
        if (isOtherPlayer){changePiece();}
        int[] movelist = convertMovesto1d(getMoveList());
        for (int i : movelist){
            Reversi child = new Reversi();
            int[] oldboard = getBoard();
            int[] newboard = copyboard(oldboard);

            child.setBoard(newboard);

            if (isOtherPlayer){child.changePiece();}
            child.makeMove(i, false);
            child.lastmove = i;
            children.add(child);
        }
        return children;
    }

    public int[] copyboard(int[] board) {
        int[] newboard = new int[64];
        int j = 0;
        for (int i : board) {
            newboard[j] = i;
            j++;
        }
        return newboard;
    }

    public int getPiece(){
        return piece;
    }

    @Override
    public Board aiGetBoard() {
        return null;
    }

}




