import java.sql.*;
import java.sql.Connection;

public class DataStatements {

    private Connection connection;

    public DataStatements(Connection connection) {
        this.connection = connection;
    }

    public void insertData(ResearchDataHolder data) throws SQLException {
        String query = "INSERT INTO data () VALUES ()";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        //preparedStatement.setInt();
        preparedStatement.execute();
    }
}
