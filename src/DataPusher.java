import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataPusher {

    private int lastMove;
    private int turnCount;
    private int gameCount;
    private int[] lastBoard;
    private int validMovesCount;
    private String hasWon;
    private String info;
    //private Statement statement;

    private static DataPusher instance = null;
    //private DataStatements statements;

    private DataPusher(){
        try {
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/researchdata", "root", "");
            //Connection connection = new DataConnection().getConn();
            //statement = connection.createStatement();

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/researchdata", "root", "");

            Statement statement = connection.createStatement();

            String query2 = "SELECT `uitslagen`.`regelNummer`,\n" +
                    "    `uitslagen`.`gameCount`,\n" +
                    "    `uitslagen`.`turnCount`,\n" +
                    "    `uitslagen`.`hasWon`,\n" +
                    "    `uitslagen`.`lastMove`,\n" +
                    "    `uitslagen`.`validMovesCount`,\n" +
                    "    `uitslagen`.`lastBoard`\n" +
                    "FROM `researchdata`.`uitslagen`;";

            //statement.executeUpdate(query1);

            ResultSet resultSet = statement.executeQuery(query2);

            while (resultSet.next() ){
                System.out.println(resultSet.getString("lastBoard"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DataPusher getInstance(){
        if(instance == null){
            instance = new DataPusher();
        }
        return instance;
    }

    public void pushData(){
        /*try {
            System.out.println("kakkie050");

            ResultSet resultSet = statement.executeQuery("SELECT `uitslagen`.`regelNummer`,\n" +
                    "    `uitslagen`.`gameCount`,\n" +
                    "    `uitslagen`.`turnCount`,\n" +
                    "    `uitslagen`.`hasWon`,\n" +
                    "    `uitslagen`.`lastMove`,\n" +
                    "    `uitslagen`.`validMovesCount`,\n" +
                    "    `uitslagen`.`lastBoard`\n" +
                    "FROM `researchdata`.`uitslagen`;\n");

            while (resultSet.next() ){
                System.out.println(resultSet.getString("validMovesCount"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }

    public void setLastBoard(int[] lastBoard) {
        this.lastBoard = lastBoard;
    }

    public void setValidMovesCount(int validMovesCount) {
        this.validMovesCount = validMovesCount;
    }

    public void setHasWon(String hasWon) {
        this.hasWon = hasWon;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void gameCount(int gameCount) {
        this.gameCount = gameCount;
    }

}
