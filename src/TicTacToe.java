import javafx.application.Platform;
import javafx.util.Pair;

import java.util.ArrayList;

public class TicTacToe extends Game{

    private int[] board;
    private int[][] board2d;
    private int row;
    private int col;
    private int piece;
    private Board b;
    private int lastmove;

    public TicTacToe() {
        resetBoard();
    }

    public void makeBoard2d() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board2d[i][j] = this.board[(i * 3) + j];
            }
        }
    }

    public void makeMove(int move, boolean updateGui) {
        //convert van 1d naar 2d
        row = move / 3;
        col = move % 3;

        if (isGameRunning && validMove(row, col)) {
            // Steen plaatsen op positie rij, kolom op bord
            makeBoard2d();
            board2d[row][col] = piece;

            // Controleren welke 'kleur' (1 of 2) de tegenstander is
            int opponent = 1;
            if (piece == 1) {
                opponent = 2;
            }



            board = boardConvertto1d();

            //print2dBoard(board2d);

            //swap beurt
            //changePiece();
            //print2dBoard(board2d);
            if (turn.equals(ourUsername)) {
                Controller.con.makeMove(move);
            }

            //Platform.runLater(Controller.reversiController::updateView);
            if (updateGui) {
                try {
                    if (turn.equals(player1)) {
                        turn = player2;
                    } else {
                        turn = player1;
                    }
                    //Controller.game.turn = Controller.game.opponent;
                    Platform.runLater(() -> {
                        Controller.tttController.updateView();
                    });

                } catch (NullPointerException n) {
                    System.out.println(Controller.tttController);
                    n.printStackTrace();
                }
            }
        }
        else {
            System.out.println("Oeps, je kan geen steentje zetten hier!");
        }
    }





    //private methode, wordt alleen gebruikt bij getMoveList
    public boolean validMove(int row, int col) {
        // kijken of de rij en kolom leeg zijn
        if (board2d[row][col] != 0){
            return false;
        }else{
            // Kijken welke steen de tegenstander is
            int opponent = 1;
            if (piece == 1) {
                opponent = 2;
            }
            return true;
        }
    }

    public ArrayList<Pair> getMoveList() {

        ArrayList<Pair> legalMoves = new ArrayList<>();
        // Het bord controleren of we daarheen mogen bewegen en de coordinaten
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (validMove(row, col)) { // Coordinaten opslaan
                    Pair p = new Pair(row,col);
                    legalMoves.add(p);
                }
            }
        }
        return legalMoves;
    }






    public int[] convertMovesto1d(ArrayList<Pair> legalmoves2d){
        int[] legalmoves1d = new int[legalmoves2d.size()];
        for (int i=0;i < legalmoves2d.size();i++){
            Pair coord = legalmoves2d.get(i);
            Object key = coord.getKey();
            Object value = coord.getValue();
            int row = (int) key;
            int col = (int) value;
            legalmoves1d[i] = (3*row+col);
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
        for (int i=0;i<9;i++){
            int row = i / 3;
            int col = i % 3;
            board[i] = board2d[row][col];
        }
        return board;
    }

    //zet de move op het bord en past de spelerbeurt aan zodat de volgende move van de andere speler is.
//    public void updateBoard(int move){
//        board[move] = piece;
//        if (piece == 1){
//            piece = 2;
//        }
//        else if (piece == 2){
//            piece = 1;
//        }
//    }


    // Win functie om de andere 3 win functies aan te roepen
    // Geeft true als er een winnaar is, false als er geen winnaar is
    public boolean controleerWinnaar() {
        return (rijenWinnaar() || kolomWinnaar() || diagonaalWinnaar());
    }

    // Rijen controleren op een winnaar (Verticaal)
    private boolean rijenWinnaar() {
        for (int rij = 0; rij < 3; rij++) {
            if (controleerRij(board[3 *rij], board[3 * rij + 1], board[3 * rij + 2])) {
                return true;
            }
        }
        return false;
    }

    //Kolommen cotroleren op een winnaar (Horizontaal)
    private boolean kolomWinnaar() {
        for (int kol = 0; kol < 3; kol++) {
            if (controleerRij(board[kol], board[kol * 2], board[kol * 3])) {
                return true;
            }
        }
        return false;
    }
    // de twee diagonale mogelijkheden controleren op een winnaar
    private boolean diagonaalWinnaar() {
        return ((controleerRij(board[0], board[4], board[8])) || (controleerRij(board[2], board[4], board[6])));
    }

    // Controleren of de 3 waardes hetzelfde zijn om te kijken of er een winnaar is
    private boolean controleerRij(int hok1, int hok2, int hok3) {
        return ((hok1 != 0) && (hok2 == hok3) && (hok1 == hok3));
    }


    public boolean isMoveLegal(int move){
        if(board[move] != 0){
            return false;
        }
        return board[move] == 0;
    }


    //--------------------------------------------------------------
    public void resetBoard(){
        piece = 1;
        b = new Board(9);
        board = b.getBoard();

    }

    public ArrayList<TicTacToe> getChildren(boolean isOtherPlayer){
        ArrayList<TicTacToe> children = new ArrayList<>();
        //System.out.println(isOtherPlayer);
        if (isOtherPlayer){changePiece();}
        int[] movelist = convertMovesto1d(getMoveList());
        for (int i : movelist){
            TicTacToe child = new TicTacToe();
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

    public void setBoard(int[] board){
        this.board = board;
        makeBoard2d();
    }

    public int[] getBoard(){
        return this.board;
    }

    public int getPiece(){
        return piece;
    }

    @Override
    public Board aiGetBoard(){
        return b;
    }
}