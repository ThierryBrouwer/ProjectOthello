import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Stinky {

    public Stinky(){


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/researchdata", "root", "");

            Statement statement = connection.createStatement();

            String query1 = "INSERT INTO `stinky`.`people`\n" +
                    "(`id`,\n" +
                    "`firstname`)\n" +
                    "VALUES\n" +
                    "(6,\n" +
                    "'kobus');\n";

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
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
