public class ResearchDataHolder {

    private int lastMove;
    private int[] lastBoard;
    private int validMovesCount;
    private boolean hasWon;
    private String info;

    public ResearchDataHolder() {

    }

    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }


    public int[] getLastBoard() {
        return lastBoard;
    }

    public void setLastBoard(int[] lastBoard) {
        this.lastBoard = lastBoard;
    }


    public int getValidMovesCount() {
        return validMovesCount;
    }

    public void setValidMovesCount(int validMovesCount) {
        this.validMovesCount = validMovesCount;
    }


    public boolean isHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
