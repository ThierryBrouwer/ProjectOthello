import javafx.application.Platform;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Reversi klasse, Dit is de representatie van het spel. In deze klasse zitten methode om moves te verwerken,
 * legale moves te vinden en een winnaar te bepalen.
 *
 * @author Joost, Djordy, Thierry
 * @version 15/4/2021
 */
public class Reversi extends Game {

    private int[] board;
    private int[][] board2d;
    private int spelerBeurt;
    private int row;
    private int col;
    private int piece;
    private int lastmove;

    /**
     * Constructor voor Reversi.
     */
    public Reversi() {
        resetBoard();
    }

    /**
     * Deze methode convert het 1 dimensionale bord, naar een 2 dimensionaal bord.
     */
    public void makeBoard2d() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board2d[i][j] = this.board[(i * 8) + j];
            }
        }
    }

    /**
     * Methode om een zet te maken. Gebruikt de meegegeven integer 'move' en berekent daarmee het nieuwe bord.
     *
     * @param move      int, de plek waar een nieuw steentje moet worden neergelegd.
     * @param updateGui boolean, True als de GUI geupdate moet worden. False als het niet geupdate moet worden.
     */
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

            //De gui moet niet geupdate worden als er een zet 'gesimuleerd' moet worden bij het rekenen van het minimax algoritme.
            if (updateGui) {
                if (turn.equals(ourUsername)) {
                    Controller.con.makeMove(move);
                }
                try {
                    if (turn.equals(player1)) {
                        turn = player2;
                    } else {
                        turn = player1;
                    }
                    Platform.runLater(() -> {
                        Controller.reversiController.updateView();
                    });

                } catch (NullPointerException n) {
                    System.out.println(Controller.reversiController);
                    n.printStackTrace();
                }
            }
        }
    }

    /**
     * Methode om te checken of er vanaf een uitgangspunt in een bepaalde richting steentjes omgedraaid moeten worden.
     *
     * @param board2d       het bord
     * @param row           de x positie van het uitgangspunt.
     * @param col           de y positie van het uitgangspunt.
     * @param deltaRow      de x richting waarin gecheckt wordt of er stenen van de andere kleur liggen.
     * @param deltaCol      de y richting waarin gecheckt wordt of er stenen van de andere kleur liggen.
     * @param myPiece       de eigen kleur (1 voor zwart, 2 voor wit)
     * @param opponentPiece de kleur van de andere speler.
     * @return boolean, true als er geflipt moet worden, false als er niet geflipt moet worden.
     */
    public boolean checkFlip(int[][] board2d, int row, int col, int deltaRow, int deltaCol, int myPiece, int opponentPiece) {
        //check voor piece aan buitenste ring
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return false;
        }


        if (board2d[row][col] == opponentPiece) {
            while ((row >= 0) && (row < 8) && (col >= 0) && (col < 8)) {
                row += deltaRow;
                col += deltaCol;
                if (row < 0 || row > 7 || col < 0 || col > 7) {
                    return false;
                } //als de tile buiten het veld valt, kunnen we niet flippen


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

    /**
     * De methode die wordt gebruikt om de steentjes om te draaien.
     *
     * @param row de x positie van het uitgangspunt.
     * @param col de y positie van het uitgangspunt.
     * @param deltaRow de x richting waarin gecheckt wordt of er stenen van de andere kleur liggen.
     * @param deltaCol de y richting waarin gecheckt wordt of er stenen van de andere kleur liggen.
     * @param myPiece de eigen kleur (1 voor zwart, 2 voor wit)
     * @param opponentPiece de kleur van de tegenstander (1 voor zwart, 2 voor wit)
     */
    public void flipPieces(int row, int col, int deltaRow, int deltaCol, int myPiece, int opponentPiece) {
        while (board2d[row][col] == opponentPiece) {
            board2d[row][col] = myPiece;
            row += deltaRow;
            col += deltaCol;
        }
    }

    /**
     * @param row de x positie van het uitgangspunt.
     * @param col de y positie van het uitgangspunt.
     * @return boolean true als de beurt geldig is, false als de beurt ongeldig is.
     */
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
        if (checkFlip(board2d, row + 1, col - 1, 1, -1, piece, opponent))
            return true;
        // controleer noord west
        if (checkFlip(board2d, row - 1, col + 1, -1, 1, piece, opponent))
            return true;
        // controleer noord oost
        return checkFlip(board2d, row + 1, col + 1, 1, 1, piece, opponent);
    }

    /**
     * Methode die een lijst met alle valid moves in de huidige positie returned.
     *
     * @return ArrayList<Pair>, arraylist met valid moves, in (row, col) format.
     */
    public ArrayList<Pair> getMoveList() {

        ArrayList<Pair> legalMoves = new ArrayList<>();
        // Het bord controleren of we daarheen mogen bewegen en de coordinaten
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (validMove(row, col)) { // Coordinaten opslaan
                    Pair p = new Pair(row, col);
                    legalMoves.add(p);
                }
            }
        }
        return legalMoves;
    }


    /**
     * Telt het aantal zwarte stenen.
     *
     * @return int, aantal zwarte stenen
     */
    public int blackPoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 1) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Telt het aantal witte stenen.
     *
     * @return int, aantal witte stenen
     */
    public int whitePoints() {
        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 2) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Kijkt of zwart of wit de meeste punten heeft return x als zwart wint, return o als wit wint en return 0 bij gelijkspel.
     *
     * @return int, welke speler de winnaar is, of 0 als er niemand wint.
     */
    public int gameWinner() {
        if (blackPoints() > whitePoints()) {
            return 1;
        }
        if (blackPoints() < whitePoints()) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Convert de lijst met 2d moves naar een integer array met 1d moves.
     *
     * @param legalmoves2d, lijst met moves in (row, col) format
     * @return int[] array, lijst met moves in single integer format.
     */
    public int[] convertMovesto1d(ArrayList<Pair> legalmoves2d) {
        int[] legalmoves1d = new int[legalmoves2d.size()];
        for (int i = 0; i < legalmoves2d.size(); i++) {
            Pair coord = legalmoves2d.get(i);
            Object key = coord.getKey();
            Object value = coord.getValue();
            int row = (int) key;
            int col = (int) value;
            legalmoves1d[i] = (8 * row + col);
        }
        return legalmoves1d;
    }

    /**
     * print een bord.
     *
     * @param board het bord
     */
    public void print2dBoard(int[][] board) {

        for (int i = 0; i < board.length; i++) {
            String s = "";
            for (int j = 0; j < board.length; j++) {
                s = s + board[i][j];
            }
            System.out.println(s);
        }
        System.out.println();
    }

    /**
     * functie om de beurt om te draaien.
     */
    public void changePiece() {
        if (piece == 1) {
            piece = 2;
        } else {
            piece = 1;
        }
        //System.out.println("Nu ben ik piece " + piece);
    }

    /**
     * @return een array van 64 ints, die het bord representeren.
     */
    public int[] boardConvertto1d() {
        for (int i = 0; i < 64; i++) {
            int row = i / 8;
            int col = i % 8;
            board[i] = board2d[row][col];
        }
        return board;
    }

    /**
     * Functie om het bord te resetten naar de beginpositie.
     */
    public void resetBoard() {
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

    /**
     * Setter voor board.
     *
     * @param board nieuwe bord.
     */
    public void setBoard(int[] board) {
        this.board = board;
        makeBoard2d();
    }

    /**
     * Getter voor board.
     *
     * @return het bord van dit object.
     */
    public int[] getBoard() {
        return this.board;
    }

    /**
     * Methode die alle children van de huidige positie returned. Een child is een volgende positie die kan onstaan, door een legale zet te maken.
     *
     * @param isOtherPlayer boolean, die gebruikt wordt om de volgende zet met het juiste steentje te zetten.
     * @return ArrayList met Reversi objecten.
     */
    public ArrayList<Reversi> getChildren(boolean isOtherPlayer) {
        ArrayList<Reversi> children = new ArrayList<>();
        if (isOtherPlayer) {
            changePiece();
        }
        int[] movelist = convertMovesto1d(getMoveList());
        for (int i : movelist) {
            Reversi child = new Reversi();
            int[] oldboard = getBoard();
            int[] newboard = copyboard(oldboard);

            child.setBoard(newboard);

            if (isOtherPlayer) {
                child.changePiece();
            }
            child.makeMove(i, false);
            child.lastmove = i;
            children.add(child);
        }
        return children;
    }

    /**
     * Methode om een kopie te maken van het bord, zodat er geen referentie wordt gemaakt naar het oude bord,
     * en ze dus niet allebei geupdate worden.
     *
     * @param board Het bord om te kopiëren.
     * @return Kopie van het bord.
     */
    public int[] copyboard(int[] board) {
        int[] newboard = new int[64];
        int j = 0;
        for (int i : board) {
            newboard[j] = i;
            j++;
        }
        return newboard;
    }

    /**
     * Getter voor welk stuk er aan de beurt is.
     *
     * @return int, piece.
     */
    public int getPiece() {
        return piece;
    }

    @Override
    public Board aiGetBoard() {
        return null;
    }

}




